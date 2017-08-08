/*
 * 

 * 
 */
package com.jinchuangan.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.jinchuangan.Template;
import com.jinchuangan.dao.BrandDao;
import com.jinchuangan.dao.ProductDao;
import com.jinchuangan.dao.PromotionDao;
import com.jinchuangan.entity.Brand;
import com.jinchuangan.entity.Product;
import com.jinchuangan.entity.Promotion;
import com.jinchuangan.service.StaticService;
import com.jinchuangan.service.TemplateService;
import com.jinchuangan.util.FreemarkerUtils;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Service - 静态化
 * 
 * 
 * @version 1.0
 */
@Service("staticServiceImpl")
public class StaticServiceImpl implements StaticService, ServletContextAware {

	/** Sitemap最大地址数 */
	private static final Integer SITEMAP_MAX_SIZE = 40000;

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "brandDaoImpl")
	private BrandDao brandDao;
	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath, Map<String, Object> model) {
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		Writer writer = null;
		try {
			freemarker.template.Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
			File staticFile = new File(servletContext.getRealPath(staticPath));
			File staticDirectory = staticFile.getParentFile();
			if (!staticDirectory.exists()) {
				staticDirectory.mkdirs();
			}
			fileOutputStream = new FileOutputStream(staticFile);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			writer = new BufferedWriter(outputStreamWriter);
			template.process(model, writer);
			writer.flush();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStreamWriter);
			IOUtils.closeQuietly(fileOutputStream);
		}
		return 0;
	}

	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath) {
		return build(templatePath, staticPath, null);
	}

	@Transactional(readOnly = true)
	public int build(Product product) {
		Assert.notNull(product);

		delete(product);
		Template template = templateService.get("productContent");
		int buildCount = 0;
		if (product.getIsMarketable()) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("product", product);
			buildCount += build(template.getTemplatePath(), product.getPath(), model);
		}
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildIndex() {
		Template template = templateService.get("index");
		return build(template.getTemplatePath(), template.getStaticPath());
	}

	@Transactional(readOnly = true)
	public int buildSitemap() {
		int buildCount = 0;
		Template sitemapIndexTemplate = templateService.get("sitemapIndex");
		Template sitemapTemplate = templateService.get("sitemap");
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> staticPaths = new ArrayList<String>();
		for (int step = 0, index = 0, first = 0, count = SITEMAP_MAX_SIZE;;) {
			try {
				model.put("index", index);
				String templatePath = sitemapTemplate.getTemplatePath();
				String staticPath = FreemarkerUtils.process(sitemapTemplate.getStaticPath(), model);
				if (step == 1) {
					List<Product> products = productDao.findList(first, count, null, null);
					model.put("products", products);
					if (products.size() < count) {
						step++;
						first = 0;
						count -= products.size();
					} else {
						buildCount += build(templatePath, staticPath, model);
						productDao.clear();
						productDao.flush();
						staticPaths.add(staticPath);
						model.clear();
						index++;
						first += products.size();
						count = SITEMAP_MAX_SIZE;
					}
				} else if (step == 2) {
					List<Brand> brands = brandDao.findList(first, count, null, null);
					model.put("brands", brands);
					if (brands.size() < count) {
						step++;
						first = 0;
						count -= brands.size();
					} else {
						buildCount += build(templatePath, staticPath, model);
						brandDao.clear();
						brandDao.flush();
						staticPaths.add(staticPath);
						model.clear();
						index++;
						first += brands.size();
						count = SITEMAP_MAX_SIZE;
					}
				} else if (step == 3) {
					List<Promotion> promotions = promotionDao.findList(first, count, null, null);
					model.put("promotions", promotions);
					buildCount += build(templatePath, staticPath, model);
					promotionDao.clear();
					promotionDao.flush();
					staticPaths.add(staticPath);
					if (promotions.size() < count) {
						model.put("staticPaths", staticPaths);
						buildCount += build(sitemapIndexTemplate.getTemplatePath(), sitemapIndexTemplate.getStaticPath(), model);
						break;
					} else {
						model.clear();
						index++;
						first += promotions.size();
						count = SITEMAP_MAX_SIZE;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildOther() {
		int buildCount = 0;
		Template shopCommonJsTemplate = templateService.get("shopCommonJs");
		Template adminCommonJsTemplate = templateService.get("adminCommonJs");
		buildCount += build(shopCommonJsTemplate.getTemplatePath(), shopCommonJsTemplate.getStaticPath());
		buildCount += build(adminCommonJsTemplate.getTemplatePath(), adminCommonJsTemplate.getStaticPath());
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildAll() {
		int buildCount = 0;
		for (int i = 0; i < productDao.count(); i += 20) {
			List<Product> products = productDao.findList(i, 20, null, null);
			for (Product product : products) {
				buildCount += build(product);
			}
			productDao.clear();
		}
		buildIndex();
		buildSitemap();
		buildOther();
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int delete(String staticPath) {
		Assert.hasText(staticPath);

		File staticFile = new File(servletContext.getRealPath(staticPath));
		if (staticFile.exists()) {
			staticFile.delete();
			return 1;
		}
		return 0;
	}

	@Transactional(readOnly = true)
	public int delete(Product product) {
		Assert.notNull(product);

		return delete(product.getPath());
	}

	@Transactional(readOnly = true)
	public int deleteIndex() {
		Template template = templateService.get("index");
		return delete(template.getStaticPath());
	}

	@Transactional(readOnly = true)
	public int deleteOther() {
		int deleteCount = 0;
		Template shopCommonJsTemplate = templateService.get("shopCommonJs");
		Template adminCommonJsTemplate = templateService.get("adminCommonJs");
		deleteCount += delete(shopCommonJsTemplate.getStaticPath());
		deleteCount += delete(adminCommonJsTemplate.getStaticPath());
		return deleteCount;
	}

}
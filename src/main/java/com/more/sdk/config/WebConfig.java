package com.more.sdk.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebConfig implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfig.class);
		rootContext.setConfigLocation("com.more.sdk.config");
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

	
}

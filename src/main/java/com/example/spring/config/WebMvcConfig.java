package com.example.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.OrderedHiddenHttpMethodFilter;
import org.springframework.boot.context.web.OrderedHttpPutFormContentFilter;
import org.springframework.boot.context.web.OrderedRequestContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.example.spring.config.viewresolver.JsonViewResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@DependsOn(value = { "rootConfig" })
public class WebMvcConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

	@Autowired
	private ObjectMapper objectMapper;

	public WebMvcConfig() {
		LOGGER.debug("생성자 WebMvcConfig()");
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new CharacterEncodingFilter("UTF-8", true));
		registration.setName("characterEncodingFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new OrderedHiddenHttpMethodFilter());
		registration.setName("hiddenHttpMethodFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public FilterRegistrationBean httpPutFormContentFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new OrderedHttpPutFormContentFilter());
		registration.setName("httpPutFormContentFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public FilterRegistrationBean requestContextFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new OrderedRequestContextFilter());
		registration.setEnabled(true);
		registration.setName("requestContextFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setOrder(1);
		resolver.setContentNegotiationManager(manager);
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(jsonViewResolver());
		resolvers.add(jspViewResolver());
		resolvers.add(new BeanNameViewResolver());
		resolver.setViewResolvers(resolvers);
		return resolver;
	}

	@Bean
	public ViewResolver jsonViewResolver() {
		LOGGER.debug("{}", objectMapper);
		return new JsonViewResolver(objectMapper);
	}

	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "jsonView")
	public MappingJackson2JsonView mappingJackson2JsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setContentType("application/json; charset=UTF-8");
		return jsonView;
	}
}
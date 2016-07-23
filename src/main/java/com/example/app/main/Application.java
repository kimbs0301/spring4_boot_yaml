package com.example.app.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketMessagingAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import com.example.app.embedded.EmbeddedTomcatConfig;
import com.example.spring.config.WebAppContextConfig;

/**
 * @author gimbyeongsu
 * 
 */
@Configurable
@EnableAutoConfiguration(exclude = { EmbeddedServletContainerAutoConfiguration.class, //
		JmxAutoConfiguration.class, //
		SpringApplicationAdminJmxAutoConfiguration.class, //
		AopAutoConfiguration.class, //
		TransactionAutoConfiguration.class, //
		WebSocketAutoConfiguration.class, //
		WebSocketMessagingAutoConfiguration.class, //
		DispatcherServletAutoConfiguration.class, //
		ErrorMvcAutoConfiguration.class, //
		ServerPropertiesAutoConfiguration.class, //
		JacksonAutoConfiguration.class, //
		DataSourceAutoConfiguration.class, //
		HttpMessageConvertersAutoConfiguration.class, //
		DataSourceTransactionManagerAutoConfiguration.class, //
		ConfigurationPropertiesAutoConfiguration.class, //
		HttpEncodingAutoConfiguration.class, //
		MultipartAutoConfiguration.class //
})
@ComponentScan(basePackages = { "com.example.spring" }, basePackageClasses = { EmbeddedTomcatConfig.class }, excludeFilters = {
		@Filter(value = { WebAppContextConfig.class }, type = FilterType.ASSIGNABLE_TYPE),
		@Filter(pattern = { "com.example.spring.*.model.*" }, type = FilterType.REGEX) })
public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public Application() {
		LOGGER.debug("생성자 Application()");
	}

	public static void main(String[] args) throws Exception {
		LOGGER.debug("start");
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Application.class);
		SpringApplication springApplication = springApplicationBuilder.build();
		springApplication.run();
	}
}

package com.example.spring.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RootConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RootConfig.class);

	@Autowired
	private Environment environment;
	@Autowired
	private ObjectMapper objectMapper;

	public RootConfig() {
		LOGGER.debug("생성자 RootConfig()");
	}

	@Bean(name = "configProperties")
	public PropertiesFactoryBean configProperties() throws IOException {
		PropertiesFactoryBean properties = new PropertiesFactoryBean();
		String[] activeProfiles = environment.getActiveProfiles();
		List<String> profiles = Lists.newArrayList(activeProfiles);
		ClassPathResource[] classPathResources = new ClassPathResource[profiles.size()];
		for (int i = 0; i < profiles.size(); ++i) {
			String profile = profiles.get(i);
			if ("junit".equals(profile) || "local".equals(profile)) {
				classPathResources[i] = new ClassPathResource("application-" + profile + ".properties");
			} else {
				classPathResources[i] = new ClassPathResource("config/application-" + profile + ".properties");
			}
		}
		properties.setLocations(classPathResources);

		YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();
		yamlProperties.setResources(new ClassPathResource("application-" + getActiveProfile() + ".yml"));
		PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
		propertiesFactory.setPropertiesArray(yamlProperties.getObject(), properties.getObject());
		return propertiesFactory;
	}

	@Bean(name = "objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}

	private String getActiveProfile() {
		String[] activeProfiles = environment.getActiveProfiles();
		List<String> profiles = Lists.newArrayList(activeProfiles);
		return profiles.get(0);
	}
}
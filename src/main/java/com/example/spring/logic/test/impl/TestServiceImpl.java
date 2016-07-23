package com.example.spring.logic.test.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.spring.logic.test.TestService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class TestServiceImpl implements TestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	private Environment environment;

	@Value("#{configProperties['lang.ko']}")
	private String langKo;

	@Override
	public void test() {
		LOGGER.debug("KKK {}", langKo);
		LOGGER.debug("KKK {}", environment.getRequiredProperty("lang.ko"));
	}
}

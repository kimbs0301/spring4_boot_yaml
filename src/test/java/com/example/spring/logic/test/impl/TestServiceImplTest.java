package com.example.spring.logic.test.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.junit.JunitSpringAnnotation;
import com.example.spring.logic.test.TestService;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
public class TestServiceImplTest {

	@Autowired
	private TestService testService;

	@Test
	public void test() throws Exception {
		testService.test();
	}
}

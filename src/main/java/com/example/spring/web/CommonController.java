package com.example.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.logic.test.TestService;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
public class CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private TestService testService;

	public CommonController() {
		LOGGER.debug("생성자 CommonController()");
	}

	/**
	 * curl -v "http://localhost:8080/mvc/"
	 * 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		LOGGER.debug("");
		testService.test();
		return "index";
	}

	/**
	 * curl -v "http://localhost:8080/mvc/shutdown"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shutdown", method = RequestMethod.GET)
	public ResponseEntity<Void> shutdown(UriComponentsBuilder ucBuilder) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				LOGGER.info("shutdown start");
				System.exit(0);
			}
		}).start();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
	}
}

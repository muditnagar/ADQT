package com.adqt.springservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adqt.springservice.dto.RuleValueDTO;
import com.adqt.springservice.service.RuleValueService;

@org.springframework.web.bind.annotation.RestController
public class RuleValueController {

	@Autowired
	private RuleValueService ruleValueService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/ruleValue", produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveValue(@RequestBody List<RuleValueDTO> ruleValueDTOList) {
		ruleValueService.saveValue(ruleValueDTOList);
	}
	
	
}

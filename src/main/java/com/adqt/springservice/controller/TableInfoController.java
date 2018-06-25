package com.adqt.springservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adqt.springservice.dto.TableInfoDTO;
import com.adqt.springservice.service.TableInfoService;

@org.springframework.web.bind.annotation.RestController
public class TableInfoController {

	@Autowired
	TableInfoService tableInfoService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/table", produces = MediaType.APPLICATION_JSON_VALUE)
	public TableInfoDTO saveAndGetTable(@RequestBody TableInfoDTO tableInfoDTO) {
		
		return tableInfoService.saveAndGetTable(tableInfoDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/table", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TableInfoDTO> getAllTable() {
		
		return tableInfoService.getAllTable();
	}
	
}

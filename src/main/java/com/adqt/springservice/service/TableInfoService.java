package com.adqt.springservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.adqt.springservice.dto.ColumnInfoDTO;
import com.adqt.springservice.dto.TableInfoDTO;
import com.adqt.springservice.entity.ColumnInformation;
import com.adqt.springservice.entity.TableInformation;
import com.adqt.springservice.repo.TableRepository;

@org.springframework.stereotype.Service
public class TableInfoService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TableRepository tableRepository;

	public TableInfoDTO saveAndGetTable(TableInfoDTO tableInfoDTO) {
		log.info("recieved TableInfoDTO : {}",tableInfoDTO);
		TableInformation table = new TableInformation();
		table.setTableName(tableInfoDTO.getTableName());
		Set<ColumnInformation> columns = new HashSet<ColumnInformation>();
		int index = 0;
		for (ColumnInfoDTO columnDTO : tableInfoDTO.getColumnInfoList()) {
			log.info("recieved ColumnInfoDTO : {}",columnDTO);
			ColumnInformation column = new ColumnInformation();
			column.setColumnName(columnDTO.getColumnName());
			column.setColumnIndex(index);
			index++;
			column.setDataType(columnDTO.getDataType());
			column.setTableInformation(table);
			columns.add(column);
		}
		table.setColumnInformations(columns);
		tableRepository.save(table);
		return getTableInfoToSend(table);
	}
	
	public List<TableInfoDTO> getAllTable(){
		List<TableInfoDTO> tableList = new ArrayList<>();
		List<TableInformation> allTable = tableRepository.findAll();
		for (TableInformation tableInfo:allTable){
			TableInfoDTO tableDto = getTableInfoToSend(tableInfo);
			tableList.add(tableDto);
			
		}
		return tableList;
		
	}

	private TableInfoDTO getTableInfoToSend(TableInformation table) {
		TableInfoDTO tableInfoDTO = new TableInfoDTO();
		tableInfoDTO.setTableId(table.getId());
		tableInfoDTO.setTableName(table.getTableName());
		Set<ColumnInformation> columns = table.getColumnInformations();
		List<ColumnInfoDTO> columnInfoDTOList = new ArrayList<ColumnInfoDTO>();
		for (ColumnInformation column : columns) {
			ColumnInfoDTO columnInfoDTO = new ColumnInfoDTO();
			columnInfoDTO.setColumnId(column.getId());
			columnInfoDTO.setColumnIndex(column.getColumnIndex());
			columnInfoDTO.setColumnName(column.getColumnName());
			columnInfoDTO.setDataType(column.getDataType());
			columnInfoDTOList.add(columnInfoDTO);
		}
		tableInfoDTO.setColumnInfoList(columnInfoDTOList);
		return tableInfoDTO;
	}

}

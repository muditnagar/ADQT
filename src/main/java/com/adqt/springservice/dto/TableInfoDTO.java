package com.adqt.springservice.dto;

import java.io.Serializable;
import java.util.List;

public class TableInfoDTO implements Serializable {

	private static final long serialVersionUID = -7453572140604929789L;
	
	int tableId;
	String tableName;
	List<ColumnInfoDTO> columnInfoList;
	
	public int getTableId() {
		return tableId;
	}
	
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<ColumnInfoDTO> getColumnInfoList() {
		return columnInfoList;
	}
	
	public void setColumnInfoList(List<ColumnInfoDTO> columnInfoList) {
		this.columnInfoList = columnInfoList;
	}

	@Override
	public String toString() {
		return "TableInfoDTO [tableId=" + tableId + ", tableName=" + tableName + ", columnInfoList=" + columnInfoList
				+ "]";
	}
	
}

package com.adqt.springservice.dto;

import java.io.Serializable;

public class ColumnInfoDTO implements Serializable {

	private static final long serialVersionUID = -3230510770325812019L;

	int columnId;
	String columnName;
	String dataType;
	int columnIndex;
	
	public int getColumnId() {
		return columnId;
	}
	
	public void setColumnId(int id) {
		this.columnId = id;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public int getColumnIndex() {
		return columnIndex;
	}
	
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	@Override
	public String toString() {
		return "ColumnInfoDTO [columnId=" + columnId + ", columnName=" + columnName + ", dataType=" + dataType
				+ ", columnIndex=" + columnIndex + "]";
	}
	
	
	
}

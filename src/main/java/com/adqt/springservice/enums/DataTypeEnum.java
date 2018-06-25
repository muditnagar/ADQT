package com.adqt.springservice.enums;

public enum DataTypeEnum {
	
	STRING(1,"STRING"),
	BYTES(2,"BYTES"),
	INTEGER(3,"INTEGER"),
	FLOAT(4,"FLOAT"),
	BOOLEAN(5,"BOOLEAN"),
	RECORD(6,"RECORD"),
	TIMESTAMP(7,"TIMESTAMP"),
	DATE(8,"DATE"),
	TIME(9,"TIME"),
	DATETIME(10,"DATETIME");

	private int id;
	private String dataType;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	private DataTypeEnum(int id, String dataType) {
		this.id = id;
		this.dataType = dataType;
	}
	
	public DataTypeEnum getByDataType(String dataType) {
		switch(dataType) {
		case "BOOLEAN" : return  DataTypeEnum.BOOLEAN;
		case "BYTES" : return DataTypeEnum.BYTES;
		case "DATE" : return DataTypeEnum.DATE;
		case "DATETIME" : return DataTypeEnum.DATETIME;
		case "FLOAT" : return DataTypeEnum.FLOAT; 
		case "INTEGER" : return DataTypeEnum.INTEGER;
		case "RECORD" : return DataTypeEnum.RECORD;
		case "STRING" : return DataTypeEnum.STRING;
		case "TIME" : return DataTypeEnum.TIME;
		case "TIMESTAMP" : return DataTypeEnum.TIMESTAMP;
		default : return DataTypeEnum.STRING;
		}
	}
	
}

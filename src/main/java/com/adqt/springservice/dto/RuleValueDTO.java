package com.adqt.springservice.dto;

import java.io.Serializable;

public class RuleValueDTO implements Serializable {

	private static final long serialVersionUID = 2630693736584160670L;
	
	private int tableId;
	private int columnId;
	private String ruleType;
	private String ruleExpression;
	
	public int getTableId() {
		return tableId;
	}
	
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	public int getColumnId() {
		return columnId;
	}
	
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	
	public String getRuleType() {
		return ruleType;
	}
	
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	public String getRuleExpression() {
		return ruleExpression;
	}
	
	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}
	
}

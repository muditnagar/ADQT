package com.adqt.springservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@javax.persistence.Table(name="rule_value")
public class RuleValue implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@javax.persistence.Column(name="table_name")
	private String tableName;
	
	@javax.persistence.Column(name="column_name")
	private String columnName;
	
	@javax.persistence.Column(name="data_type")
	private String dataType;
	
	@javax.persistence.Column(name="column_index")
	private int columnIndex;
	
	@javax.persistence.Column(name="rule_type")
	private String ruleTypes;
	
	@javax.persistence.Column(name="rule_key")
	private String ruleKey;
	
	@javax.persistence.Column(name="rule_value")
	private String ruleValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRuleKey() {
		return ruleKey;
	}

	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public String getRuleTypes() {
		return ruleTypes;
	}

	public void setRuleTypes(String ruleTypes) {
		this.ruleTypes = ruleTypes;
	}

	@Override
	public String toString() {
		return "RuleValue [id=" + id + ", tableName=" + tableName + ", columnName=" + columnName + ", dataType="
				+ dataType + ", columnIndex=" + columnIndex + ", ruleTypes=" + ruleTypes + ", ruleKey=" + ruleKey
				+ ", ruleValue=" + ruleValue + "]";
	}

}

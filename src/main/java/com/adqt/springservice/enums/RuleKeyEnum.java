package com.adqt.springservice.enums;

public enum RuleKeyEnum {
	
	GREATER(1,"GREATER"),
	SMALLER(2,"SMALLER"),
	EQUAL(3,"EQUAL"),
	BETWEEN(4,"BETWEEN"),
	IN(5,"IN"),
	NOT_EQUAL(6,"NOT EQUAL"),
	NOT_NULL(7,"NOT NULL"),
	DATATYPE(8,"DATATYPE");
	
	private RuleKeyEnum(int id, String ruleKey) {
		this.id = id;
		this.ruleKey = ruleKey;
	}

	private int id;
	private String ruleKey;
	
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
	
	public RuleKeyEnum getByRuleKey(String ruleKey) {
		switch (ruleKey) {
		case "GREATER":
			return RuleKeyEnum.GREATER;
		case "SMALLER":
			return RuleKeyEnum.SMALLER;
		case "EQUALS":
			return RuleKeyEnum.EQUAL;
		case "BETWEEN":
			return RuleKeyEnum.BETWEEN;
		case "IN":
			return RuleKeyEnum.IN;
		case "NOT_EQUAL":
			return RuleKeyEnum.NOT_EQUAL;
		default:
			return RuleKeyEnum.GREATER;
		}
	}

}

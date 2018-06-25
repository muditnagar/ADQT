package com.adqt.springservice.enums;

public enum RuleTypeEnum {
	
	ACCURACY(1,"ACCURACY"),
	COMPLETNESS(2,"COMPLETNESS"),
	CONFORMITY(3,"CONFORMITY"),
	CONSISTENCY(4,"CONSISTENCY");

	private RuleTypeEnum(int id, String ruleType) {
		this.id = id;
		this.ruleType = ruleType;
	}

	private int id;
	private String ruleType;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRuleType() {
		return ruleType;
	}
	
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	public RuleTypeEnum getByRuleType(String ruleType) {
		switch (ruleType) {
		case "ACCURACY":
			return RuleTypeEnum.ACCURACY;
		case "COMPLETNESS":
			return RuleTypeEnum.COMPLETNESS;
		case "CONFORMITY":
			return RuleTypeEnum.CONFORMITY;
		case "CONSISTENCY":
			return RuleTypeEnum.CONSISTENCY;
		default:
			return RuleTypeEnum.ACCURACY;
		}
	}

}

package com.adqt.springservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.adqt.springservice.dto.RuleValueDTO;
import com.adqt.springservice.entity.ColumnInformation;
import com.adqt.springservice.entity.RuleValue;
import com.adqt.springservice.entity.TableInformation;
import com.adqt.springservice.enums.RuleKeyEnum;
import com.adqt.springservice.repo.ColumnRepository;
import com.adqt.springservice.repo.RuleValueRepository;
import com.adqt.springservice.repo.TableRepository;

@org.springframework.stereotype.Service
public class RuleValueService {

	@Autowired
	private RuleValueRepository ruleValueRepo;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Autowired
	private ColumnRepository columnRepository;

	public void saveValue(List<RuleValueDTO> ruleValueDTOList) {
		List<RuleValue> ruleValueList = new ArrayList<RuleValue>();
		for (RuleValueDTO ruleValueDTO : ruleValueDTOList) {
			RuleValue ruleValue = new RuleValue();
			TableInformation table = tableRepository.findOne(ruleValueDTO.getTableId());
			ColumnInformation column = columnRepository.findOne(ruleValueDTO.getColumnId());
			ruleValue.setTableName(table.getTableName());
			ruleValue.setColumnName(column.getColumnName());
			ruleValue.setColumnIndex(column.getColumnIndex());
			ruleValue.setDataType(column.getDataType());
			ruleValue.setRuleTypes(ruleValueDTO.getRuleType());
			Map<String, String> ruleKeyValueMap = getRuleKeyValue(ruleValueDTO.getRuleExpression());
			Set<String> keys = ruleKeyValueMap.keySet();
			for(String key : keys) {
				if(key.equals("ruleKey")) {
				ruleValue.setRuleKey(ruleKeyValueMap.get("ruleKey"));
				}else if(key.equals("ruleValue")) {
				ruleValue.setRuleValue(ruleKeyValueMap.get("ruleValue"));	
				}
			}
			ruleValueList.add(ruleValue);
		}
		ruleValueRepo.save(ruleValueList);
	}

	private Map<String, String> getRuleKeyValue(String ruleExpression) {
		Map<String, String> ruleKeyValueMap = new HashMap<String, String>();
		String ruleValue = null;
		int index = 0;
		if (ruleExpression.toUpperCase().startsWith(RuleKeyEnum.GREATER.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.GREATER.getRuleKey());
			index = ruleExpression.toUpperCase().indexOf("THAN") + 4;
		} else if (ruleExpression.toUpperCase().startsWith(RuleKeyEnum.BETWEEN.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.BETWEEN.getRuleKey());
			index = 9;
			int endIndex = ruleExpression.length()-1;
			ruleValue = ruleExpression.substring(index,endIndex).trim();
			ruleKeyValueMap.put("ruleValue", ruleValue);
			return ruleKeyValueMap;
		} else if (ruleExpression.toUpperCase().startsWith(RuleKeyEnum.EQUAL.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.EQUAL.getRuleKey());
			index = ruleExpression.toUpperCase().indexOf("TO") + 2;
		} else if (ruleExpression.toUpperCase().startsWith(RuleKeyEnum.IN.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.IN.getRuleKey());
			index = ruleExpression.toUpperCase().indexOf("(") + 1;
			int endIndex = ruleExpression.length()-1;
			ruleValue = ruleExpression.substring(index,endIndex).trim();
			ruleKeyValueMap.put("ruleValue", ruleValue);
			return ruleKeyValueMap;
		} else if (ruleExpression.toUpperCase().startsWith(RuleKeyEnum.NOT_EQUAL.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.NOT_EQUAL.getRuleKey());
			index = ruleExpression.toUpperCase().indexOf("TO") + 2;
		} else if (ruleExpression.toUpperCase().startsWith(RuleKeyEnum.SMALLER.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.SMALLER.getRuleKey());
			index = ruleExpression.toUpperCase().indexOf("THAN") + 4;
		}else if(ruleExpression.toUpperCase().startsWith(RuleKeyEnum.NOT_NULL.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.NOT_NULL.getRuleKey());
			ruleKeyValueMap.put("ruleValue", null);
			return ruleKeyValueMap;
		}else if(ruleExpression.toUpperCase().startsWith(RuleKeyEnum.DATATYPE.getRuleKey())) {
			ruleKeyValueMap.put("ruleKey", RuleKeyEnum.DATATYPE.getRuleKey());
			index = ruleExpression.toUpperCase().indexOf("IS") + 2;
		}
		ruleValue = ruleExpression.substring(index).trim();
		ruleKeyValueMap.put("ruleValue", ruleValue);
		return ruleKeyValueMap;
	}

}

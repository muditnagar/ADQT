package com.adqt.springservice.repo;


import com.adqt.springservice.entity.TableInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import com.adqt.springservice.entity.RuleValue;

import java.util.List;

public interface RuleValueRepository extends JpaRepository<RuleValue, Integer> {

    List<RuleValue> findByTableName(String tableName);
	
	
}

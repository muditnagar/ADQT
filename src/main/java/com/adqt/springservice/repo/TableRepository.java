package com.adqt.springservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adqt.springservice.entity.TableInformation;


public interface TableRepository extends JpaRepository<TableInformation, Integer> {

    TableInformation findByTableName(String tableName);

	
}

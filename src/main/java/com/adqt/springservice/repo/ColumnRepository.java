package com.adqt.springservice.repo;

import com.adqt.springservice.entity.TableInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.adqt.springservice.entity.ColumnInformation;

import java.util.List;

public interface ColumnRepository extends JpaRepository<ColumnInformation, Integer> {

    List<ColumnInformation> findByTableInformation(TableInformation tableName);
}

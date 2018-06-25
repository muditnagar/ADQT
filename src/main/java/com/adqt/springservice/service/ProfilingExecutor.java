package com.adqt.springservice.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adqt.springservice.entity.RuleValue;
import com.adqt.springservice.entity.TableInformation;
import com.adqt.springservice.repo.RuleValueRepository;
import com.adqt.springservice.repo.TableRepository;

@Service
public class ProfilingExecutor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RuleValueRepository ruleValueRepository;

    @Autowired
    PipeLineCreator pipeLineCreator;

    @Autowired
    TableRepository tableRepository;
    
    @Autowired
    DataQualityStatService dataQualityStatService;


    public void executeProfiling(String tableName) throws IOException {
        log.info("________________TABLE________________{}",tableName);
        TableInformation table = tableRepository.findByTableName(tableName);
        List<RuleValue> rules = ruleValueRepository.findByTableName(tableName);
        Schema schema = new Schema(table.getColumnInformations());
        dataQualityStatService.lauchDataQuality(tableName);
        pipeLineCreator.preProcess(tableName,schema,rules);
    }
}

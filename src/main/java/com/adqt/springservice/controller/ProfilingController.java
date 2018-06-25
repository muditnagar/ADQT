package com.adqt.springservice.controller;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adqt.springservice.service.ProfilingExecutor;

@RestController
public class ProfilingController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProfilingExecutor profilingExecutor ;

    @RequestMapping(method = RequestMethod.GET, value = "/api/profiling/{tableName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void startProfiling(@PathVariable("tableName") String tableName) {
        log.info("In Table : {}",tableName);
        try {
            profilingExecutor.executeProfiling(tableName);
        } catch (Exception e) {
            log.error("error in profiling {}",e);
        }
    }

}


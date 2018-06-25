package com.adqt.springservice.service;

import com.adqt.springservice.entity.RuleValue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ProfilingContext implements Serializable {
    String tableName;
    Schema schema;
    List<RuleValue> rules;

    public ProfilingContext(String tableName, Schema schema, List<RuleValue> rules) {
        this.tableName = tableName;
        this.schema = schema;
        this.rules = rules;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<RuleValue> getRules() {
        return rules;
    }

    public void setRules(List<RuleValue> rules) {
        this.rules = rules;
    }

}

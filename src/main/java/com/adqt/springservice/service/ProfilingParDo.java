package com.adqt.springservice.service;

import com.adqt.springservice.entity.ColumnInformation;
import com.adqt.springservice.entity.RuleValue;
import com.adqt.springservice.enums.RuleTypeEnum;
import com.google.api.services.bigquery.model.TableRow;
import com.google.cloud.dataflow.sdk.values.TupleTag;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ProfilingParDo extends DoFn<String, TableRow> {

    private final PCollectionView<ProfilingContext> profilingContext;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ProfilingParDo(PCollectionView<ProfilingContext> profilingContext) {
        this.profilingContext = profilingContext;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        ProfilingContext localProfilingContext = c.sideInput(profilingContext);
        String row = c.element();
        List<RuleValue> rules = localProfilingContext.getRules();
        String[] columns = row.split(",");
        TableRow tableRow = new TableRow();
        tableRow.set("tablename", localProfilingContext.getTableName());
        tableRow.set("row", row);
        log.info("Row processing started {} with rule : {}",row,rules);
        Boolean accStatus = true, conformStatus = true, completenessStatus=true, consistencyStatus=true;
        for (RuleValue rule : rules) {
            log.info("Row processing started {} with rule {}",row,rule);
            ColumnInformation column = localProfilingContext.getSchema().getColumn(rule.getColumnIndex());
            String data = columns[rule.getColumnIndex()];
            if (RuleTypeEnum.ACCURACY.toString().equalsIgnoreCase(rule.getRuleTypes())) {
                accStatus = interpretAccuracyRule(data, rule.getRuleValue(), rule.getDataType(), rule.getRuleKey());
            } else if (RuleTypeEnum.COMPLETNESS.toString().equalsIgnoreCase(rule.getRuleTypes())) {
                completenessStatus = interpretCompletenessRule(data, rule.getRuleKey());
            } else if (RuleTypeEnum.CONFORMITY.toString().equalsIgnoreCase(rule.getRuleTypes())) {
                conformStatus = interpretConformityRule(data, rule.getRuleValue(), rule.getRuleKey());
            } else if (RuleTypeEnum.CONSISTENCY.toString().equalsIgnoreCase(rule.getRuleTypes())) {
                consistencyStatus = interpretConsistencyRule(data, rule.getRuleValue(), rule.getRuleKey());
            }
        }
        tableRow.set("accuracy", accStatus);
        tableRow.set("completeness", completenessStatus);
        tableRow.set("conformity", conformStatus);
        tableRow.set("consistency", consistencyStatus);
        log.info("@@@@ TABLE ROW @@@@"+tableRow);
        c.output(tableRow);
    }

    private Comparable castObject(String value, String dataType) {
        try {
            switch (dataType.toLowerCase()) {
                case "string":
                    return value;
                case "integer":
                    log.info("value : {}     datatype : {}",value,dataType);
                    return Integer.parseInt(value);
                case "float":
                    return Float.parseFloat(value);
                case "date":
                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                    Date d = f.parse(value);
                    return d.getTime();
                case "boolean":
                    return Boolean.parseBoolean(value);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private boolean interpretConsistencyRule(String data, String ruleValue, String rule) {
        if (rule.toUpperCase().equals("IN")) {
            String[] in = ruleValue.split(",");
            return Arrays.binarySearch(in, data) >= 0;
        }
        return false;

    }

    private boolean interpretCompletenessRule(String data, String rule) {
        if (rule.toUpperCase().equals("NOT NULL")) {
            return data != null && !data.equals("") && !data.equals("-");
        }
        return false;
    }

    private boolean interpretConformityRule(String data, String ruleValue, String rule) {
        if (rule.toUpperCase().equals("DATATYPE")) {
            Comparable com = castObject(data, ruleValue);
            return com!=null;
        }
        return false;
    }

    private boolean interpretAccuracyRule(String data, String ruleValue, String dataType, String rule) {
        //TODO: handle null pointer from castObject function
        Comparable dataCom = castObject(data,dataType);
        if(dataCom==null) return false;
        if(rule.toUpperCase().trim().equals("GREATER")) {
            Comparable ruleValCom = castObject(ruleValue, dataType);
            return dataCom.compareTo(ruleValCom) > 0;
        }else if(rule.toUpperCase().trim().equals("SMALLER")) {
            Comparable ruleValCom = castObject(ruleValue, dataType);
            return dataCom.compareTo(ruleValCom) < 0;
        }else if(rule.toUpperCase().trim().equals("EQUAL")) {
            Comparable ruleValCom = castObject(ruleValue, dataType);
            return dataCom.compareTo(ruleValCom) == 0;
        }else if(rule.toUpperCase().trim().equals("BETWEEN")) {
            String[] range = ruleValue.split(",");
            Comparable ruleValCom1 = castObject(range[0], dataType);
            Comparable ruleValCom2 = castObject(range[1], dataType);
            return dataCom.compareTo(ruleValCom1) > 0 && dataCom.compareTo(ruleValCom2) < 0;
        } else if(rule.toUpperCase().trim().equals("NOT EQUAL")) {
                return data.compareTo(ruleValue) != 0;
        }else{
                return true;
        }
    }
}

package com.adqt.springservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.adqt.bigquery.connection.BigQueryClientServiceFactory;
import com.adqt.springservice.dto.DataQualityStatDTO;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryResult;

@Service
public class DataQualityStatService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	  
	private final static double percentageThreshold = 66.66d;
	
	@Autowired
	private ThreadPoolProviderService threadPoolProvider;

	@Autowired
	private SimpMessagingTemplate broker;

	@Autowired
	BigQueryClientServiceFactory bigQueryClientServiceFactory;

	@Autowired
	public DataQualityStatService(final SimpMessagingTemplate broker) {
		this.broker = broker;
	}

	public void lauchDataQuality(final String tableName) {
		final BigQuery bigQueryClient;
		try {
			bigQueryClient = bigQueryClientServiceFactory.getClientService();
		}catch(Exception exception) {
			log.error("Exception occured while trying to make connection");
			return;
		}
			threadPoolProvider.execute(new Runnable() {
			@Override
			public void run() {
				try {		
			launchDataAccuracy(tableName,bigQueryClient);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		threadPoolProvider.execute(new Runnable() {
			@Override
			public void run() {
				try {
					launchDataCompletenss(tableName,bigQueryClient);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		threadPoolProvider.execute(new Runnable() {
			@Override
			public void run() {
				try {
					launchDataConformity(tableName,bigQueryClient);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		threadPoolProvider.execute(new Runnable() {
			@Override
			public void run() {
				try {
					launchDataConsistency(tableName,bigQueryClient);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void launchDataAccuracy(final String tableName,final BigQuery bigQueryClient) throws Exception {
		log.info("fetch Accuracy count ");
		String query = "select count(1) as row_count,accuracy from `let_us_see.ProfilingResult` group by tablename,accuracy";
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false).build();
		while (true) {
			Thread.sleep(10000);
			log.info("new thread fetching count of accuracy");
			DataQualityStatDTO dataQualityDTO = new DataQualityStatDTO();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = null;
			try {
			queryJob = bigQueryClient.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
			}catch(RuntimeException r) {	
				log.error("error : {}",r);
			}
			queryJob = queryJob.waitFor();
			if(queryJob == null) {
				throw new Exception("QueryJob is null");
			}else if(queryJob.getStatus().getError() != null) {
				throw new Exception(queryJob.getStatus().getError().toString());
			}
			log.info("connection made");
			QueryResult queryResult = queryJob.getQueryResults().getResult();
			Map<Boolean,Long> countMap = new HashMap<Boolean,Long>();
			for(FieldValueList fieldValueList : queryResult.iterateAll()) {
				FieldValue fieldValueRowCount = fieldValueList.get("row_count");
				long rowCount = fieldValueRowCount.getLongValue();
				FieldValue fieldValueAccuracy = fieldValueList.get("accuracy");
				if(null == fieldValueAccuracy) {
					countMap.put(true,0l);
				}else {
					try {
						Boolean status = Boolean.valueOf(fieldValueAccuracy.getBooleanValue());
						countMap.put(status, rowCount);
					}catch (Exception e){
						dataQualityDTO.setTableName("accuracy");
						dataQualityDTO.setQualifiedRowCount(0);
						dataQualityDTO.setTotalRowCount(0);
						dataQualityDTO.setPercentage(0);
						dataQualityDTO.setBelowThreshold(false);
						broker.convertAndSend("/dataquality/accuracy", dataQualityDTO);
						continue continueLoop;
					}
				}
			}
			long totalFinalCount = 0l;
			long totalQualifiedCount = 0l;
			if(countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(true) + totalQualifiedCount;
			}else if(!countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = 0l;
				totalFinalCount = countMap.get(true);
			}else if(countMap.containsKey(false) && !countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(false);
			}else {
				totalQualifiedCount = 0l;
				totalFinalCount = 0l;
			}			
			log.info("total of accuracy : {}",totalFinalCount);
			log.info("qualified of accuracy : {} ",totalQualifiedCount);
			dataQualityDTO.setTableName("accuracy");
			dataQualityDTO.setTotalRowCount(totalFinalCount);
			dataQualityDTO.setQualifiedRowCount(totalQualifiedCount);
			dataQualityDTO.setPercentage(totalFinalCount == 0l ? 0d : (totalQualifiedCount*100/totalFinalCount));
			dataQualityDTO.setBelowThreshold(dataQualityDTO.getPercentage() > percentageThreshold ? false : true);
			broker.convertAndSend("/dataquality/accuracy", dataQualityDTO);
		}
	}

	public void launchDataCompletenss(String tableName, BigQuery bigQueryClient) throws Exception {
		String query = "select count(1) as row_count,completeness from `let_us_see.ProfilingResult` group by tablename,completeness";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false).build();
		while (true) {
			Thread.sleep(10000);
			
			DataQualityStatDTO dataQualityDTO = new DataQualityStatDTO();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigQueryClient.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
			
			queryJob = queryJob.waitFor();
			if(queryJob == null) {
				throw new Exception("QueryJob is null");
			}else if(queryJob.getStatus().getError() != null) {
				throw new Exception(queryJob.getStatus().getError().toString());
			}
			QueryResult queryResult = queryJob.getQueryResults().getResult();
			Map<Boolean,Long> countMap = new HashMap<Boolean,Long>();
			for(FieldValueList fieldValueList : queryResult.iterateAll()) {
				FieldValue fieldValueRowCount = fieldValueList.get("row_count");
				long rowCount = fieldValueRowCount.getLongValue();
				FieldValue fieldValueAccuracy = fieldValueList.get("completeness");
				if(null == fieldValueAccuracy) {
					countMap.put(true,0l);
				}else {
					try {
						Boolean status = Boolean.valueOf(fieldValueAccuracy.getBooleanValue());
						countMap.put(status, rowCount);
					}catch (Exception e){
						dataQualityDTO.setTableName("completness");
						dataQualityDTO.setQualifiedRowCount(0);
						dataQualityDTO.setTotalRowCount(0);
						dataQualityDTO.setPercentage(0);
						dataQualityDTO.setBelowThreshold(false);
						broker.convertAndSend("/dataquality/completness", dataQualityDTO);
						continue continueLoop;
					}
				}
			}
			long totalFinalCount = 0l;
			long totalQualifiedCount = 0l;
			if(countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(true) + totalQualifiedCount;
			}else if(!countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = 0l;
				totalFinalCount = countMap.get(true);
			}else if(countMap.containsKey(false) && !countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(false);
			}else {
				totalQualifiedCount = 0l;
				totalFinalCount = 0l;
			}
			
			dataQualityDTO.setTableName("completness");
			dataQualityDTO.setQualifiedRowCount(totalQualifiedCount);
			dataQualityDTO.setTotalRowCount(totalFinalCount);
			dataQualityDTO.setPercentage(totalFinalCount == 0l ? 0d : (totalQualifiedCount*100/totalFinalCount));
			dataQualityDTO.setBelowThreshold(dataQualityDTO.getPercentage() > percentageThreshold ? false : true);
			broker.convertAndSend("/dataquality/completness", dataQualityDTO);
		}
	}

	public void launchDataConformity(String tableName, BigQuery bigQueryClient) throws Exception {
		String query = "select count(1) as row_count,conformity from `let_us_see.ProfilingResult` group by tablename,conformity";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false).build();
		while (true) {
			Thread.sleep(10000);
			
			DataQualityStatDTO dataQualityDTO = new DataQualityStatDTO();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigQueryClient.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
			
			queryJob = queryJob.waitFor();
			if(queryJob == null) {
				throw new Exception("QueryJob is null");
			}else if(queryJob.getStatus().getError() != null) {
				throw new Exception(queryJob.getStatus().getError().toString());
			}
			QueryResult queryResult = queryJob.getQueryResults().getResult();
			Map<Boolean,Long> countMap = new HashMap<Boolean,Long>();
			for(FieldValueList fieldValueList : queryResult.iterateAll()) {
				FieldValue fieldValueRowCount = fieldValueList.get("row_count");
				long rowCount = fieldValueRowCount.getLongValue();
				FieldValue fieldValueAccuracy = fieldValueList.get("conformity");
				if(null == fieldValueAccuracy) {
					countMap.put(true,0l);
				}else {
					try {
						Boolean status = Boolean.valueOf(fieldValueAccuracy.getBooleanValue());
						countMap.put(status, rowCount);
					}catch (Exception e){
						dataQualityDTO.setTableName("conformity");
						dataQualityDTO.setQualifiedRowCount(0);
						dataQualityDTO.setTotalRowCount(0);
						dataQualityDTO.setPercentage(0);
						dataQualityDTO.setBelowThreshold(false);
						broker.convertAndSend("/dataquality/conformity", dataQualityDTO);
						continue continueLoop;
					}
				}
			}
			long totalFinalCount = 0l;
			long totalQualifiedCount = 0l;
			if(countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(true) + totalQualifiedCount;
			}else if(!countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = 0l;
				totalFinalCount = countMap.get(true);
			}else if(countMap.containsKey(false) && !countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(false);
			}else {
				totalQualifiedCount = 0l;
				totalFinalCount = 0l;
			}
		
			dataQualityDTO.setTableName("conformity");
			dataQualityDTO.setQualifiedRowCount(totalQualifiedCount);
			dataQualityDTO.setTotalRowCount(totalFinalCount);
			dataQualityDTO.setPercentage(totalFinalCount == 0l ? 0d : (totalQualifiedCount*100/totalFinalCount));
			dataQualityDTO.setBelowThreshold(dataQualityDTO.getPercentage() > percentageThreshold ? false : true);
			broker.convertAndSend("/dataquality/conformity", dataQualityDTO);
		}
	}

	public void launchDataConsistency(String tableName, BigQuery bigQueryClient) throws Exception {
		String query = "select count(1) as row_count,consistency from `let_us_see.ProfilingResult` group by tablename,consistency";
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false).build();
		while (true) {
			Thread.sleep(10000);
			
			DataQualityStatDTO dataQualityDTO = new DataQualityStatDTO();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigQueryClient.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
			
			queryJob = queryJob.waitFor();
			if(queryJob == null) {
				throw new Exception("QueryJob is null");
			}else if(queryJob.getStatus().getError() != null) {
				throw new Exception(queryJob.getStatus().getError().toString());
			}
			QueryResult queryResult = queryJob.getQueryResults().getResult();
			Map<Boolean,Long> countMap = new HashMap<Boolean,Long>();
			for(FieldValueList fieldValueList : queryResult.iterateAll()) {
				FieldValue fieldValueRowCount = fieldValueList.get("row_count");
				long rowCount = fieldValueRowCount.getLongValue();
				FieldValue fieldValueAccuracy = fieldValueList.get("consistency");
				if(null == fieldValueAccuracy) {
					countMap.put(true,0l);
				}else {
					try {
						Boolean status = Boolean.valueOf(fieldValueAccuracy.getBooleanValue());
						countMap.put(status, rowCount);
					}catch (Exception e){
						dataQualityDTO.setTableName("consistency");
						dataQualityDTO.setQualifiedRowCount(0);
						dataQualityDTO.setTotalRowCount(0);
						dataQualityDTO.setPercentage(0);
						dataQualityDTO.setBelowThreshold(false);
						broker.convertAndSend("/dataquality/consistency", dataQualityDTO);
						continue continueLoop;
					}
				}
			}
			long totalFinalCount = 0l;
			long totalQualifiedCount = 0l;
			if(countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(true) + totalQualifiedCount;
			}else if(!countMap.containsKey(false) && countMap.containsKey(true)) {
				totalQualifiedCount = 0l;
				totalFinalCount = countMap.get(true);
			}else if(countMap.containsKey(false) && !countMap.containsKey(true)) {
				totalQualifiedCount = countMap.get(false);
				totalFinalCount = countMap.get(false);
			}else {
				totalQualifiedCount = 0l;
				totalFinalCount = 0l;
			}
			dataQualityDTO.setTableName("consistency");
			dataQualityDTO.setQualifiedRowCount(totalQualifiedCount);
			dataQualityDTO.setTotalRowCount(totalFinalCount);
			dataQualityDTO.setPercentage(totalFinalCount == 0l ? 0d : (totalQualifiedCount*100/totalFinalCount));
			dataQualityDTO.setBelowThreshold(dataQualityDTO.getPercentage() > percentageThreshold ? false : true);
			broker.convertAndSend("/dataquality/consistency", dataQualityDTO);
		}
	}

}
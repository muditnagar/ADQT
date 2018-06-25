package com.adqt.bigquery.connection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adqt.PropertyFileReader;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

@Component
public class BigQueryClientServiceFactory {

	private static final Logger LOG = LoggerFactory.getLogger(BigQueryClientServiceFactory.class);

	@Autowired private PropertyFileReader propertyFileReader;
	public BigQuery getClientService() throws IOException, GeneralSecurityException {

		return createAuthorizedClient();
	}

	private BigQuery createAuthorizedClient() throws IOException, GeneralSecurityException {
		
		String jsonFilePath = propertyFileReader.getOAuthPvtKeyPath();
		GoogleCredentials credentials = createGoogleCredential(jsonFilePath);
		String projectId = propertyFileReader.getProjectId();

		return BigQueryOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build().getService();
	}

	private GoogleCredentials createGoogleCredential(String jsonFilePath) throws IOException, GeneralSecurityException {
		File credentialsPath = new File(jsonFilePath);
		FileInputStream serviceAccountStream = new FileInputStream(credentialsPath);
		return GoogleCredentials.fromStream(serviceAccountStream).createScoped(BigqueryScopes.all());
	}

}

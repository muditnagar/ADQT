package com.adqt.springservice.service;

import org.apache.beam.sdk.extensions.gcp.options.GcsOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

public interface PelicanPipelineOptions extends GcsOptions {

    @Description("GCP private key json path")
    @Validation.Required
    String getCredentialPath();
    void setCredentialPath(String value);

    @Description("Approximate size of chunks to read")
    @Default.Integer(1000000)
    Integer getChunkSize();
    void setChunkSize(Integer value);
}

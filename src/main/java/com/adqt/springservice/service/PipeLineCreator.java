package com.adqt.springservice.service;

import com.adqt.PropertyFileReader;
import com.adqt.springservice.entity.RuleValue;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.dataflow.sdk.values.TupleTag;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.SlidingWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollectionView;
import org.apache.beam.sdk.values.TupleTagList;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PipeLineCreator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PropertyFileReader propertyFileReader;

    public void preProcess(String tableName, Schema schema, List<RuleValue> rules) throws IOException {
        //PelicanCluster targetCluster = getTargetCluster();
        String[] args = new String[7];
        args[0] = prepareArgument("tempLocation", propertyFileReader.getTempLocation());
        args[1] = prepareArgument("runner", propertyFileReader.getRunner());
        args[2] = prepareArgument("project", propertyFileReader.getProjectId());
        args[3] = prepareArgument("stagingLocation", propertyFileReader.getStagingLocation());
        args[4] = prepareArgument("credentialPath", propertyFileReader.getOAuthPvtKeyPath());
        args[5] = prepareArgument("streaming", "true");
        args[6] = prepareArgument("zone", propertyFileReader.getZone());
        //args[7] = prepareArgument("autoscalingAlgorithm", "NONE");
   /* args[8] = prepareArgument("numWorkers", ""+(Math.min(maxPipelineWorkers, Math.max(infoObjects.size()/8, minPipelineWorkers))));
    args[9] = prepareArgument("maxNumWorkers", ""+maxPipelineWorkers);*/



        for(int i=0;i<7;i++)
        log.info("@@@ ARGS @@ {}",args[i]);

        PipelineOptionsFactory.register(PelicanPipelineOptions.class);
        final PelicanPipelineOptions pipelineOptions = PipelineOptionsFactory.fromArgs(args).as(PelicanPipelineOptions.class);
        File credentialsPath = new File(propertyFileReader.getOAuthPvtKeyPath());
        HashSet<String> scopeSet = new HashSet<>();
        scopeSet.add("https://www.googleapis.com/auth/cloud-platform");
        scopeSet.add("https://www.googleapis.com/auth/compute");
        scopeSet.add("https://www.googleapis.com/auth/compute.readonly");
        scopeSet.add("https://www.googleapis.com/auth/userinfo.email");
        FileInputStream serviceAccountStream = new FileInputStream(credentialsPath);
        Credentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream).createScoped(scopeSet);
        pipelineOptions.setGcpCredential(credentials);

        Pipeline p = Pipeline.create(pipelineOptions);

        // Hardcoded Topic value
        String topic = propertyFileReader.getTopic();
        String output = propertyFileReader.getOutputBQTable();

        // INitialise the context

        // Build the table bQSchema for the output table.
        List<TableFieldSchema> fields = new ArrayList<>();
        fields.add(new TableFieldSchema().setName("tablename").setType("STRING"));
        fields.add(new TableFieldSchema().setName("accuracy").setType("BOOL"));
        fields.add(new TableFieldSchema().setName("completeness").setType("BOOL"));
        fields.add(new TableFieldSchema().setName("conformity").setType("BOOL"));
        fields.add(new TableFieldSchema().setName("consistency").setType("BOOL"));
        fields.add(new TableFieldSchema().setName("row").setType("STRING"));
        TableSchema bQSchema = new TableSchema().setFields(fields);

        ProfilingContext profilingContext = new ProfilingContext(tableName,schema,rules);
        log.info("Pipeline started");

        final PCollectionView<ProfilingContext> transferContextView = p
                .apply("DB Context SideInput", Create.of(profilingContext).withCoder(SerializableCoder.of(ProfilingContext.class)))
                .apply(View.<ProfilingContext>asSingleton());
        p.apply("GetMessages", PubsubIO.readStrings().fromTopic(topic))
        .apply("window", Window.into(SlidingWindows.of(Duration.standardMinutes(2)).every(Duration.standardSeconds(30))))
        .apply("ProfilingParDo", ParDo.of(new ProfilingParDo(transferContextView))
        .withSideInputs(transferContextView))
        .apply(BigQueryIO.writeTableRows()
          .to(output)
          .withSchema(bQSchema)
          .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
          .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED));

        log.info("Pipeline Stopped");
        PipelineResult pipelineResult = p.run();
        }

    private String prepareArgument(String fieldName, String fieldValue) {
        return String.format("--%s=%s", fieldName, fieldValue);
    }
}



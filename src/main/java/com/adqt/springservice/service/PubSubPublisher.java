package com.adqt.springservice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


import javax.annotation.PostConstruct;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.grpc.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

@Service
public class PubSubPublisher {
    /*public static void publishMessages(String dataPath) throws Exception {

        TopicName topicName = TopicName.of("gcp-data-engineer-188205", "adqt");
        Publisher publisher = null;
        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build();
            BufferedReader br = new BufferedReader(new FileReader(new File(dataPath)));

            String message;
            for (; (message = br.readLine()) != null; ) {
                //final String message = br.readLine();
                ByteString data = ByteString.copyFromUtf8(message);
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
                publisher.publish(pubsubMessage);
                // add an asynchronous callback to handle success / failure
                ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        if (throwable instanceof ApiException) {
                            ApiException apiException = ((ApiException) throwable);
                            // details on the API exception
                            System.out.println(apiException.getMessage());
                        }
                        System.out.println("Error publishing message : " + message);
                    }

                    @Override
                    public void onSuccess(String messageId) {
                        // Once published, returns server-assigned message ids (unique within the topic)
                        System.out.println(message + " :" + messageId);
                    }
                });
            }
        } finally {
            if (publisher != null) {
                publisher.shutdown();
            }
        }
    }

    public static void main(String... args) throws Exception {
        // "/home/amitashukla0906/LetUsSee/src/main/resources/EcommerceData.csv" 
        String filePath = args[0];
        publishMessages(filePath);
    }*/
}

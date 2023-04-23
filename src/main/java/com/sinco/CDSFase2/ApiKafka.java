package com.sinco.CDSFase2;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONArray;
import org.json.JSONObject;


public class ApiKafka {
    public void getKafkaAlgo() {
        final String bootstapServers = "localhost:9093";
        final String consumerGroupID = "test-consumer-group";

        Properties p = new Properties();
        p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstapServers);
        p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //p.setProperty("auto.offset.reset", "earliest");
        p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupID);
        p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(p);
        consumer.subscribe(Arrays.asList("climatology","demand","events","hydrodataWAT001","hydrodataWAT002","hydrodataWAT003", "weather"));

        JSONArray climatology = new JSONArray();
        JSONArray demand = new JSONArray();
        JSONArray events = new JSONArray();
        JSONArray hydrodata1 = new JSONArray();
        JSONArray hydrodata2 = new JSONArray();
        JSONArray hydrodata3 = new JSONArray();
        JSONArray weather = new JSONArray();

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord record : records) {
                    switch(record.topic()){
                        case "climatology":
                            JSONObject jo1 = new JSONObject(record.value()+"");
                            climatology.put(jo1);
                            break;
                        case "demand":
                            JSONObject jo2 = new JSONObject(record.value()+"");
                            demand.put(jo2);
                            break;
                        case "events":
                            JSONObject jo3 = new JSONObject(record.value()+"");
                            events.put(jo3);
                            break;
                        case "hydrodataWAT001":
                            JSONObject jo4 = new JSONObject(record.value()+"");
                            hydrodata1.put(jo4);
                            break;
                        case "hydrodataWAT002":
                            JSONObject jo5 = new JSONObject(record.value()+"");
                            hydrodata2.put(jo5);
                            break;
                        case "hydrodataWAT003":
                            JSONObject jo6 = new JSONObject(record.value()+"");
                            hydrodata3.put(jo6);
                            break;
                        case "weather":
                            JSONObject jo7 = new JSONObject(record.value()+"");
                            weather.put(jo7);
                            break;
                    }
                }
            }

    }
}

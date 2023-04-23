package com.sinco.CDSFase2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONArray;
import org.json.JSONObject;


public class ApiKafka implements Runnable{
    private OptimizationAlgorithm oa;
    JSONObject climatology = new JSONObject();
    boolean hayClima = false;
    JSONObject demand = new JSONObject();
    boolean hayDemanda = false;
    JSONObject events = new JSONObject();
    boolean hayEvents = false;
    JSONObject hydrodata1 = new JSONObject();
    boolean hayHidro1 = false;
    JSONObject hydrodata2 = new JSONObject();
    boolean hayHidro2 = false;
    JSONObject weather = new JSONObject();
    boolean hayWeather = false;

    public void iniciarHilo(OptimizationAlgorithm oa) {
        this.oa = oa;
        this.run();
    }

    public void run() {
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
        String fecha = "01/01/2030";
        JSONObject joAux;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord record : records) {

                switch(record.topic()){
                    case "climatology":
                        climatology = new JSONObject(record.value()+"");
                        hayClima = true;
                    break;
                    case "demand":
                        demand = new JSONObject(record.value()+"");
                        hayDemanda = true;
                        break;
                    case "events":
                        events = new JSONObject(record.value()+"");
                        hayEvents = true;
                        break;
                    case "hydrodataWAT001":
                        hydrodata1 = new JSONObject(record.value()+"");
                        hayHidro1 = true;
                        break;
                    case "hydrodataWAT002":
                        hydrodata2 = new JSONObject(record.value()+"");
                        hayHidro2 = true;
                        break;
                    case "weather":
                        weather = new JSONObject(record.value()+"");
                        hayWeather = true;
                        break;
                }
                if(hayClima && hayDemanda && hayHidro1 && hayHidro2) {
                    oa.iniciar(climatology, demand);
                    hayClima = false;
                    hayDemanda = false;
                    hayHidro1 = false;
                    hayHidro2 = false;
                }
            }
        }
    }
}

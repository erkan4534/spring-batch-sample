package com.sample.batch.config;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() {

        // Retry ayarları
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000) // 2 saniye bekle
                .retryAttemptedLogLevel(LoggingLevel.WARN)
        );

        from("direct:sendToKafka")
                .routeId("kafkaProducerRoute")
                .log("Kafka'ya gönderiliyor: ${body}")
                .process(exchange -> {
                    throw new RuntimeException("Kafka gönderim hatası");
                })
                .to("kafka:personal-topic?brokers=localhost:9092");
    }
}


package com.sample.batch.config;

import com.sample.batch.model.Personal;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("ETL-Load", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     ItemReader<Personal> itemReader,
                     ItemProcessor<Personal, Personal> itemProcessor,
                     ItemWriter<Personal> itemWriter) {

        return new StepBuilder("ETL-file-load", jobRepository)
                .<Personal, Personal>chunk(100, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                .build();
    }
}

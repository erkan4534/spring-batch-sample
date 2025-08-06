package com.sample.batch.batch;

import com.sample.batch.model.Personal;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("itemReader")
@RequiredArgsConstructor
public class UserFileItemReader implements ItemReader<Personal> , ItemStream {
    private FlatFileItemReader<Personal> flatFileItemReader;
    private final KafkaTemplate<String, Personal> kafkaTemplate;
    @PostConstruct
    public void init() {
        this.flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
    }
    @Override
    public Personal read() throws Exception {
        return flatFileItemReader.read();
    }

    @Override
    public void open(ExecutionContext executionContext) {
        flatFileItemReader.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) {
        flatFileItemReader.update(executionContext);
    }

    @Override
    public void close() {
        flatFileItemReader.close();
    }

    public LineMapper<Personal> lineMapper() {
        DefaultLineMapper<Personal> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "dept", "salary");

        BeanWrapperFieldSetMapper<Personal> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Personal.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}

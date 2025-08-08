package com.sample.batch.batch;

import com.sample.batch.model.Personal;
import com.sample.batch.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBWriter implements ItemWriter<Personal> {

    private final PersonalRepository personalRepository;
    private final ProducerTemplate producerTemplate;

    @Override
    public void write(Chunk<? extends Personal> personalChunk) {
        System.out.println("Data Saved for Users: " + personalChunk);
        for (Personal personal : personalChunk) {
            personalRepository.save(personal);
            producerTemplate.sendBody("direct:sendToKafka", personal);
        }
    }
}

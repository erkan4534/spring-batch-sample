package com.sample.batch.batch;

import com.sample.batch.model.Personal;
import com.sample.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBWriter implements ItemWriter<Personal> {

    private final UserRepository userRepository;
    private final ProducerTemplate producerTemplate;

    @Override
    public void write(Chunk<? extends Personal> personalChunk) {
        System.out.println("Data Saved for Users: " + personalChunk);
        for (Personal personal : personalChunk) {
            userRepository.save(personal);
            producerTemplate.sendBody("direct:sendToKafka", personal);
        }
    }
}

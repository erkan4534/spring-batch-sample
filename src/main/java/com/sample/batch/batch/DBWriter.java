package com.sample.batch.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.batch.model.OutboxMessage;
import com.sample.batch.model.Personal;
import com.sample.batch.repository.OutboxMessageRepository;
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
    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void write(Chunk<? extends Personal> personalChunk) throws Exception {
        System.out.println("Data Saved for Users: " + personalChunk);
        for (Personal personal : personalChunk) {
            try{
                personalRepository.save(personal);
                producerTemplate.sendBody("direct:sendToKafka", personal);
            }catch (Exception ex){
                String json = objectMapper.writeValueAsString(personal);
                outboxMessageRepository.save(OutboxMessage.builder().
                        payload(json).
                        topic("personal-topic").
                        attempts(0).
                        build());
            }
        }
    }
}

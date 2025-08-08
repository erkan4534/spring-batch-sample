package com.sample.batch.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.batch.model.OutboxMessage;
import com.sample.batch.model.Personal;
import com.sample.batch.repository.OutboxMessageRepository;
import com.sample.batch.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxFlusher {

    private static final int MAX_ATTEMPTS = 3;
    private final OutboxMessageRepository outboxMessageRepository;
    private final PersonalRepository personalRepository;
    private final ProducerTemplate producerTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    // 5 sn’de bir çalışsın
    @Scheduled(fixedDelay = 5000)
    public void flush() {
        Instant now = Instant.now();
        List<OutboxMessage> batch = outboxMessageRepository
                .findTop200ByAttemptsLessThan(MAX_ATTEMPTS, Sort.by(Sort.Direction.ASC, "createDate"));

        for (OutboxMessage m : batch) {
            try {
                Personal p = mapper.readValue(m.getPayload(), Personal.class);
                // Kafka’ya tekrar dene
                producerTemplate.sendBody("direct:sendToKafka", p);

                // Başarılıysa: DB’ye kaydet ve outbox’tan sil
                personalRepository.save(p);
                outboxMessageRepository.deleteById(m.getId());

            } catch (Exception ex) {
                int attempts = m.getAttempts() + 1;
                m.setAttempts(attempts);
                outboxMessageRepository.save(m);
            }
        }
    }
}


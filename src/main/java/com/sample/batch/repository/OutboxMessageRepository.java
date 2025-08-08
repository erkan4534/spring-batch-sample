package com.sample.batch.repository;

import com.sample.batch.model.OutboxMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, Long> {
    List<OutboxMessage> findTop200ByAttemptsLessThan(Integer maxAttempts, Sort sort);
}


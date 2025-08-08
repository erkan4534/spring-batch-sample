package com.sample.batch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxMessage {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Lob
    @Column(name = "PAYLOAD")
    private String payload;

    @Column(name="TOPIC")
    private String topic;

    @Column(name = "ATTEMPTS")
    private Integer attempts = 0;

    @Column(name = "CREATE_DATE")
    private LocalDate createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @PrePersist
    public void onPrePersist() {
        this.createDate = LocalDate.now();
        this.updateDate = LocalDate.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updateDate = LocalDate.now();
    }
}

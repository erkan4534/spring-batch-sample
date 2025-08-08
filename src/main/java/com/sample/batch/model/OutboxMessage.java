package com.sample.batch.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

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

    @CreatedDate
    @Column(updatable = false,name = "CREATE_DATE")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}

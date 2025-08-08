package com.sample.batch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Personal {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEPT")
    private String dept;

    @Column(name = "SALARY")
    private Integer salary;

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

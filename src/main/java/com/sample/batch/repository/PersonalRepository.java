package com.sample.batch.repository;

import com.sample.batch.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Integer> {
}

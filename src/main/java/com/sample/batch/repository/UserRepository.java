package com.sample.batch.repository;

import com.sample.batch.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Personal, Integer> {
}

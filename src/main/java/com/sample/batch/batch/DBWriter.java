package com.sample.batch.batch;

import com.sample.batch.model.Users;
import com.sample.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBWriter implements ItemWriter<Users> {

    private final UserRepository userRepository;

    @Override
    public void write(Chunk<? extends Users> users) throws Exception {
        System.out.println("Data Saved for Users: " + users);
        userRepository.saveAll(users);
    }
}

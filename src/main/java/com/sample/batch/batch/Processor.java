package com.sample.batch.batch;

import com.sample.batch.model.Personal;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<Personal, Personal> {

    private static final Map<String, String> DEPT_NAMES = new HashMap<>();

    public Processor() {
        DEPT_NAMES.put("001", "Technology");
        DEPT_NAMES.put("002", "Operations");
        DEPT_NAMES.put("003", "Accounts");
    }

    @Override
    public Personal process(Personal personal) {
        String deptCode = personal.getDept();
        String dept = DEPT_NAMES.get(deptCode);
        personal.setDept(dept);
        return personal;
    }
}

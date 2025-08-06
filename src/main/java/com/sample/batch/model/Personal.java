package com.sample.batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personal {

    @Id
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;
    private Date time;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Personal {");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", dept='").append(dept).append('\'');
        sb.append(", salary=").append(salary);
        sb.append('}');
        return sb.toString();
    }
}

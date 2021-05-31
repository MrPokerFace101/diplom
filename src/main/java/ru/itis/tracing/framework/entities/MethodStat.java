package ru.itis.tracing.framework.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class MethodStat {

    @Id
    @GeneratedValue
    private Long id;
    private String methodName;
    private Long executionTime;
    private Long timestamp;
}

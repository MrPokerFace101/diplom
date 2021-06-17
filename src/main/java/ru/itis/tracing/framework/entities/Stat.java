package ru.itis.tracing.framework.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Stat {

    @Id
    @GeneratedValue
    private Long id;
    private Long executionTime;
    private Long startTime;
}

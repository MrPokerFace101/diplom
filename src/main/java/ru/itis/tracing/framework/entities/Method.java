package ru.itis.tracing.framework.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Method {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double averageExecutionTime;

    @OneToMany
    private List<Stat> stats;

    public void addStatAndRecalculateAverageTime(Stat stat) {
        this.averageExecutionTime = (this.averageExecutionTime * this.stats.size() + stat.getExecutionTime()) / (this.stats.size() + 1);
        addStat(stat);
    }

    public void addStat(Stat stat) {
        this.stats.add(stat);
    }
}

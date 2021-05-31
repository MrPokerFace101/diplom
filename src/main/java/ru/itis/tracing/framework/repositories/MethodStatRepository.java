package ru.itis.tracing.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracing.framework.entities.MethodStat;

import java.util.List;

public interface MethodStatRepository extends JpaRepository<MethodStat, Long> {

    List<MethodStat> findAllByMethodName(String methodName);
}

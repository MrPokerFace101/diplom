package ru.itis.tracing.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracing.framework.entities.Stat;

public interface StatRepository extends JpaRepository<Stat, Long> {
}

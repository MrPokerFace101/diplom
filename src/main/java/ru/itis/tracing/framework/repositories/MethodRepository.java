package ru.itis.tracing.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import ru.itis.tracing.framework.entities.Method;

@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {

    Optional<Method> findByName(String name);
}

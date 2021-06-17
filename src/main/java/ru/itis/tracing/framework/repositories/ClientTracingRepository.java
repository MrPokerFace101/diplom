package ru.itis.tracing.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.tracing.framework.entities.ClientService;

@Repository
public interface ClientTracingRepository extends JpaRepository<ClientService, Long> {
}

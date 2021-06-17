package ru.itis.tracing.framework.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClientService {

    @Id
    @GeneratedValue
    private Long id;
    private String url;

    public ClientService(String url) {
        this.url = url;
    }
}

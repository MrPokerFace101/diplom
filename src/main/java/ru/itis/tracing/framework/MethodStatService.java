package ru.itis.tracing.framework;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.itis.tracing.framework.entities.MethodStat;
import ru.itis.tracing.framework.repositories.MethodStatRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "tracing", value = "true")
public class MethodStatService {

    private final MethodStatRepository methodStatRepository;

    public MethodStatService(MethodStatRepository methodStatRepository) {
        this.methodStatRepository = methodStatRepository;
    }

    public Map<String, Pair<Long, Long>> loadAllStats() {
        List<MethodStat> list = methodStatRepository.findAll();
        return list.isEmpty() ? null : list.stream().collect(
                Collectors.toMap(
                        MethodStat::getMethodName,
                        (item) -> Pair.of(item.getTimestamp(), item.getExecutionTime())
                )
        );
    }

    public List<MethodStat> loadMethodStats(String methodName) {
        return methodStatRepository.findAllByMethodName(methodName);
    }

    public void saveStat(MethodStat methodStat) {
        methodStatRepository.save(methodStat);
    }
}

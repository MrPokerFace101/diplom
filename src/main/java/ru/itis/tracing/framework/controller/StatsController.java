package ru.itis.tracing.framework.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.tracing.framework.entities.Method;
import ru.itis.tracing.framework.services.MethodStatService;

import java.util.List;
import java.util.Optional;

@Controller
@ConditionalOnBean(MethodStatService.class)
public class StatsController {

    private final MethodStatService methodStatService;

    public StatsController(MethodStatService methodStatService) {
        this.methodStatService = methodStatService;
    }

    @GetMapping("/method-stats")
    public String allStatsPage(Model model) {
        List<Method> methodList = methodStatService.loadAllStats();
        if(methodList == null || methodList.isEmpty()) {
            model.addAttribute("statsEmpty", true);
        } else {
            model.addAttribute("statsEmpty", false);
            model.addAttribute("stats", methodList);
        }
        return "all-method-stats";
    }

    @GetMapping("/method-stats/{methodName}")
    public String loadMethodStats(Model model, @PathVariable String methodName) {
        Optional<Method> methodOptional = methodStatService.findMethodByName(methodName);
        if(!methodOptional.isPresent()) {
            model.addAttribute("method-stats-empty", true);
        } else {
            model.addAttribute("method-stats-empty", false);
            model.addAttribute("stats", methodOptional.get());
        }
        return "method-stats";
    }

    @GetMapping("/method-stats/request")
    public List<Method> sendData() {
        List<Method> stats = methodStatService.findAll();
        methodStatService.deleteAll();
        return stats;
    }
}

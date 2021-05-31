package ru.itis.tracing.framework.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.tracing.framework.MethodStatService;
import ru.itis.tracing.framework.entities.MethodStat;

import java.util.List;
import java.util.Map;

@Controller
@ConditionalOnProperty(name = "tracing", value = "true")
public class StatsController {

    private final MethodStatService methodStatService;

    public StatsController(MethodStatService methodStatService) {
        this.methodStatService = methodStatService;
    }

    @GetMapping("/method-stats")
    public String allStatsPage(Model model) {
        Map<String, Pair<Long, Long>> stats = methodStatService.loadAllStats();
        if(stats == null) {
            model.addAttribute("stats-empty", true);
        } else {
            model.addAttribute("stats-empty", false);
            model.addAttribute("stats", stats);
        }
        return "all-method-stats";
    }

    @GetMapping("/method-stats/{methodName}")
    public String loadMethodStats(Model model, @PathVariable String methodName) {
        List<MethodStat> list = methodStatService.loadMethodStats(methodName);
        if(list.isEmpty()) {
            model.addAttribute("method-stats-empty", true);
        } else {
            model.addAttribute("method-stats-empty", false);
            model.addAttribute("method-stats", list);
        }
        return "method-stats";
    }
}

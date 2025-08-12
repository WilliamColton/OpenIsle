package com.openisle.controller;

import com.openisle.service.UserVisitService;
import com.openisle.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatController {
    private final UserVisitService userVisitService;
    private final StatService statService;

    @GetMapping("/dau")
    public Map<String, Long> dau(@RequestParam(value = "date", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        long count = userVisitService.countDau(date);
        return Map.of("dau", count);
    }

    @GetMapping("/dau-range")
    public List<Map<String, Object>> dauRange(@RequestParam(value = "days", defaultValue = "30") int days) {
        if (days < 1) days = 1;
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1L);
        var data = userVisitService.countDauRange(start, end);
        return data.entrySet().stream()
                .map(e -> Map.<String,Object>of(
                        "date",  e.getKey().toString(),
                        "value", e.getValue()
                ))
                .toList();
    }

    @GetMapping("/new-users-range")
    public List<Map<String, Object>> newUsersRange(@RequestParam(value = "days", defaultValue = "30") int days) {
        if (days < 1) days = 1;
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1L);
        var data = statService.countNewUsersRange(start, end);
        return data.entrySet().stream()
                .map(e -> Map.<String,Object>of(
                        "date", e.getKey().toString(),
                        "value", e.getValue()
                ))
                .toList();
    }

    @GetMapping("/posts-range")
    public List<Map<String, Object>> postsRange(@RequestParam(value = "days", defaultValue = "30") int days) {
        if (days < 1) days = 1;
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1L);
        var data = statService.countPostsRange(start, end);
        return data.entrySet().stream()
                .map(e -> Map.<String,Object>of(
                        "date", e.getKey().toString(),
                        "value", e.getValue()
                ))
                .toList();
    }

    @GetMapping("/comments-range")
    public List<Map<String, Object>> commentsRange(@RequestParam(value = "days", defaultValue = "30") int days) {
        if (days < 1) days = 1;
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1L);
        var data = statService.countCommentsRange(start, end);
        return data.entrySet().stream()
                .map(e -> Map.<String,Object>of(
                        "date", e.getKey().toString(),
                        "value", e.getValue()
                ))
                .toList();
    }
}

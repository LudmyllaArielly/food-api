package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.filter.DailySaleFilter;
import com.github.ludmylla.foodapi.domain.model.filter.DailySale;
import com.github.ludmylla.foodapi.domain.service.DailySaleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticController {

    @Autowired
    private DailySaleQueryService dailySaleQueryService;

    @GetMapping("/daily-sales")
    public List<DailySale> consultDailySales(DailySaleFilter filter) {
        return dailySaleQueryService.consultDailySales(filter);
    }
}

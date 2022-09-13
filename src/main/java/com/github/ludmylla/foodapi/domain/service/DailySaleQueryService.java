package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.filter.DailySaleFilter;
import com.github.ludmylla.foodapi.domain.model.filter.DailySale;

import java.util.List;

public interface DailySaleQueryService {

    List<DailySale> consultDailySales(DailySaleFilter filter);
}

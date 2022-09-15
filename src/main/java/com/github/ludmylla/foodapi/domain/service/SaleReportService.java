package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.filter.DailySaleFilter;

public interface SaleReportService {

    byte[] issueDailySales(DailySaleFilter filter, String timeOffset);
}

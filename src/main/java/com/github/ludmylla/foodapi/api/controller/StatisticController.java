package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.filter.DailySaleFilter;
import com.github.ludmylla.foodapi.domain.model.filter.DailySale;
import com.github.ludmylla.foodapi.domain.service.DailySaleQueryService;
import com.github.ludmylla.foodapi.domain.service.SaleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticController {

    @Autowired
    private DailySaleQueryService dailySaleQueryService;

    @Autowired
    private SaleReportService saleReportService;

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> consultDailySales(DailySaleFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return dailySaleQueryService.consultDailySales(filter, timeOffSet);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultDailySalesPdf(DailySaleFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
       byte[] bytesPdf = saleReportService.issueDailySales(filter, timeOffSet);

       var headers = new HttpHeaders();
       headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

       return ResponseEntity.ok()
               .contentType(MediaType.APPLICATION_PDF)
               .headers(headers)
               .body(bytesPdf);
    }
}

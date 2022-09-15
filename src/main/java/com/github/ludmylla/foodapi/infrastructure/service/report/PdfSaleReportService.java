package com.github.ludmylla.foodapi.infrastructure.service.report;

import com.github.ludmylla.foodapi.domain.filter.DailySaleFilter;
import com.github.ludmylla.foodapi.domain.service.DailySaleQueryService;
import com.github.ludmylla.foodapi.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfSaleReportService implements SaleReportService {

    @Autowired
    private DailySaleQueryService dailySaleQueryService;

    @Override
    public byte[] issueDailySales(DailySaleFilter filter, String timeOffset) {
        try {

            var inputStream = this.getClass()
                    .getResourceAsStream("/reports/daily-sales.jasper");

            var params = new HashMap<String, Object>();
            params.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var dailySales = dailySaleQueryService.consultDailySales(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(dailySales);

            var jasperPrint = JasperFillManager
                    .fillReport(inputStream, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new ReportException("Unable to issue daily sales report.", e);
        }
    }
}

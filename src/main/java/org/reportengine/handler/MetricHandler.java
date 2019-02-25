package org.reportengine.handler;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.reportengine.model.DetailedRespone;
import org.reportengine.model.Metric;
import org.reportengine.model.entity.ReportConfig;
import org.reportengine.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricHandler {

    @Autowired
    private MetricsService metricsService;

    @PostMapping("/single")
    public void addSingle(@Valid @RequestBody Metric metric) {
        metricsService.writeSingle(metric);
    }

    @PostMapping("/multiple")
    public void addMultiple(@Valid @RequestBody List<Metric> metrics) {
        metricsService.writeMultiple(metrics);
    }

    @GetMapping("/report/{reportId}")
    public ReportConfig reportData(
            @PathVariable(required = true) String reportId,
            @RequestParam Map<String, String> customLabels) {
        return metricsService.getMetricForReport(reportId, customLabels);
    }

    @GetMapping("/report/{reportId}/detailed/{suiteId}")
    public DetailedRespone reportDataDetailed(
            @PathVariable(required = true) String reportId,
            @PathVariable(required = true) String suiteId,
            @RequestParam Map<String, String> customLabels) {
        return metricsService.getMetricForReportDetailed(reportId, suiteId);
    }

}

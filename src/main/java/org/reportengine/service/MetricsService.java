package org.reportengine.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.reportengine.model.Chart;
import org.reportengine.model.Detailed;
import org.reportengine.model.DetailedRespone;
import org.reportengine.model.KeyExpression;
import org.reportengine.model.Metric;
import org.reportengine.model.MetricSeries;
import org.reportengine.model.ReportTable;
import org.reportengine.model.entity.ReportConfig;
import org.reportengine.model.entity.Suite;
import org.reportengine.properties.InfluxDbProps;
import org.reportengine.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricsService {

    private InfluxDB client;
    private String databaseName;

    private static final String DELETE_METRIC = "DELETE FROM \"$measurement\" WHERE \"suiteId\" = '$suiteId'";

    @Autowired
    private SuiteService suiteService;

    @Autowired
    private ReportService reportService;

    public MetricsService(InfluxDB client, InfluxDbProps props) {
        this.client = client;
        this.databaseName = props.getDatabase();
        this.client.setDatabase(this.databaseName);
    }

    public void writeSingle(Metric metric) {
        client.write(metric.getPoint());
    }

    public void writeMultiple(List<Metric> metrics) {
        for (Metric metric : metrics) {
            writeSingle(metric);
        }
    }

    public void delete(Suite suite) {
        Query command = new Query(
                DELETE_METRIC.replaceAll("\\$measurement", suite.getType())
                        .replaceAll("\\$suiteId", suite.getId()), databaseName);
        QueryResult result = client.query(command, TimeUnit.MILLISECONDS);
        _logger.debug("{}", result);
    }

    private KeyExpression getExpression(String raw) {
        if (raw.contains(",")) {
            String[] rawList = raw.split(",", 2);
            return KeyExpression.builder()
                    .key(rawList[0].trim())
                    .expression(rawList[1].trim())
                    .build();
        }
        return KeyExpression.builder()
                .key(raw.trim())
                .build();

    }

    private Object getSuiteValue(Suite suite, String field) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field, Suite.class);
            Method getter = pd.getReadMethod();
            return getter.invoke(suite);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IntrospectionException ex) {
            _logger.error("Exception when accessing the filed[{}] in the instance {}", field, suite, ex);
            return ex.getMessage();
        }
    }

    private void updateRows(Map<String, String> columns, List<Suite> suites, List<Map<String, Object>> rows) {
        for (Suite suite : suites) {
            if (suite.getReady()) {
                Map<String, Object> row = new HashMap<>();
                // add row in to rows list
                rows.add(row);
                updateMap(columns, row, suite);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void updateMap(Map<String, String> columns, Map<String, Object> map, Suite suite) {
        // add id into map
        map.put("suiteId", getSuiteValue(suite, "id"));
        for (String header : columns.keySet()) {
            String rawKey = columns.get(header);
            KeyExpression expression = getExpression(rawKey);
            if (expression.getKey().contains("[*]")) {
                String[] keysLevel1 = expression.getKey().split("\\.", 2);
                Map<String, Object> _data = (Map<String, Object>) getSuiteValue(suite, keysLevel1[0]);
                String[] keysLevel2 = keysLevel1[1].split("\\[\\*]\\.", 2);
                List<Object> listData = (List<Object>) CommonUtils.getValue(_data, keysLevel2[0],
                        new ArrayList<Object>());
                int index = 1;
                for (Object dataObj : listData) {
                    Map<String, Object> finalData = (Map<String, Object>) dataObj;
                    Object value = CommonUtils.getValue(finalData, keysLevel2[1], "");
                    // evaluate expression
                    if (expression.isValid()) {
                        expression.add("self", value);
                        value = expression.evaluate();
                    }
                    map.put(header + " " + index, value);
                    index++;
                }
            } else {
                Object value = null;
                if (expression.getKey().contains(".")) {
                    String[] keys = expression.getKey().split("\\.", 2);
                    Object mapObject = getSuiteValue(suite, keys[0]);
                    if (mapObject instanceof Map) {
                        Map<String, Object> _map = (Map<String, Object>) mapObject;
                        value = CommonUtils.getValue(_map, keys[1], "");
                    } else {
                        value = mapObject;
                    }
                } else {
                    value = getSuiteValue(suite, expression.getKey());
                }
                // evaluate expression
                if (expression.isValid()) {
                    expression.add("self", value);
                    value = expression.evaluate();
                }
                map.put(header, value);
            }
        }

    }

    public ReportConfig getMetricForReport(String reportId, Map<String, String> customLabels) {
        _logger.debug("Custom labels:{}", customLabels);
        ReportConfig reportConfig = reportService.get(reportId);

        // update labels with custom mapping
        if (customLabels != null) {
            reportConfig.getLabels().putAll(customLabels);
        }

        List<Suite> suites = suiteService.getAll(
                reportConfig.getType(), reportConfig.getLabels(), true);

        // update tables data
        List<ReportTable> tablesFinal = new ArrayList<>();
        for (ReportTable table : reportConfig.getTables()) {
            // update table data
            List<Map<String, Object>> tableRows = new ArrayList<>();
            ReportTable tableFinal = ReportTable.builder()
                    .enabled(table.getEnabled())
                    .name(table.getName())
                    .order(table.getOrder())
                    .build();
            // add table rows
            tableFinal.setRows(tableRows);
            updateRows(table.getColumns(), suites, tableRows);
            tablesFinal.add(tableFinal);
        }

        // update table data
        reportConfig.setTables(tablesFinal);

        // update chart data
        List<Chart> chartsFinal = new ArrayList<>();
        for (Chart chart : reportConfig.getCharts()) {
            List<Map<String, Object>> data = new ArrayList<>();
            Chart chartFinal = Chart.builder()
                    .enabled(chart.getEnabled())
                    .name(chart.getName())
                    .type(chart.getType())
                    .metric(chart.isMetric())
                    .xaxis(chart.getXaxis())
                    .yaxis(chart.getYaxis())
                    .yaxis2(chart.getYaxis2())
                    .xaxisFormat(chart.getXaxisFormat())
                    .yaxisFormat(chart.getYaxisFormat())
                    .data(data)
                    .build();
            Map<String, String> columns = new HashMap<>();
            for (String query : chart.getQueries()) {
                String[] keyValue = query.split(",", 2);
                columns.put(keyValue[0], keyValue[1].trim());
            }
            updateRows(columns, suites, data);
            chartsFinal.add(chartFinal);
        }
        // update chart data
        reportConfig.setCharts(chartsFinal);

        // remove detailed chart data
        reportConfig.setDetailed(null);

        return reportConfig;
    }

    public DetailedRespone getMetricForReportDetailed(String reportId, String suiteId) {
        Optional<Suite> _suite = suiteService.get(suiteId);
        // if no suite found, return empty data
        if (!_suite.isPresent()) {
            return DetailedRespone.builder().build();
        }
        ReportConfig reportConfig = reportService.get(reportId);
        Detailed detailed = reportConfig.getDetailed();
        Suite suite = _suite.get();

        // update description
        Map<String, Object> description = new HashMap<>();
        if (detailed.getDescription() != null) {
            updateMap(detailed.getDescription(), description, suite);
        }

        // update charts
        List<Object> finalCharts = new ArrayList<>();
        if (detailed.getCharts() != null) {
            for (Chart chart : detailed.getCharts()) {
                if (chart.isMetric()) {
                    List<List<Map<String, ?>>> metricList = new ArrayList<>();
                    MetricSeries metricSeries = MetricSeries.builder()
                            .name(chart.getName()).id(suite.getId()).type(chart.getType())
                            .xAxis(chart.getXaxis())
                            .xAxisFormat(chart.getXaxisFormat())
                            .yAxisFormat(chart.getYaxisFormat())
                            .data(metricList).build();

                    for (String query : chart.getQueries()) {
                        List<Map<String, ?>> data = new ArrayList<>();
                        String command = query.replaceAll("\\$measurement",
                                suite.getType()).replaceAll("\\$suiteId", suite.getId());
                        _logger.debug("Command:{}", command);

                        QueryResult result = client.query(new Query(command, databaseName), TimeUnit.MILLISECONDS);
                        if (!result.getResults().isEmpty()
                                && result.getResults().get(0).getSeries() != null
                                && !result.getResults().get(0).getSeries().isEmpty()) {

                            Series series = result.getResults().get(0).getSeries().get(0);
                            for (List<Object> value : series.getValues()) {
                                Map<String, Object> values = new HashMap<String, Object>();
                                data.add(values);
                                for (int index = 0; index < series.getColumns().size(); index++) {
                                    String column = series.getColumns().get(index);
                                    if (!column.equals("suiteId")) {
                                        values.put(column, value.get(index));
                                    }
                                }
                            }
                            metricList.add(data);
                        }
                    }
                    finalCharts.add(metricSeries);
                } else {
                    Map<String, Object> data = new HashMap<>();
                    chart.setData(data);
                    Map<String, String> columns = new HashMap<>();
                    for (String query : chart.getQueries()) {
                        String[] keyValue = query.split(",", 2);
                        columns.put(keyValue[0], keyValue[1].trim());
                    }
                    updateMap(columns, data, suite);
                    finalCharts.add(chart);
                }
            }
        }

        return DetailedRespone.builder()
                .description(description)
                .charts(finalCharts)
                .build();
    }

}

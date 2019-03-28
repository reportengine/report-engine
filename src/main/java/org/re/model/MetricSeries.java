package org.re.model;

import java.util.List;
import java.util.Map;

import org.re.Enums.CHART_TYPE;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class MetricSeries {
    private String id;
    private String name;
    private CHART_TYPE type;
    @Default
    private boolean metric = true;
    private String xAxis;
    private String xAxisFormat;
    private String yAxisFormat;
    private List<List<Map<String, ?>>> data;
}

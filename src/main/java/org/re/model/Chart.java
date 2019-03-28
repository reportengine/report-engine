package org.re.model;

import java.util.ArrayList;
import java.util.List;

import org.re.Enums.CHART_TYPE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chart {
    private String name;
    @Default
    private Boolean enabled = true;
    private CHART_TYPE type;
    @Default
    private boolean metric = false;
    private String xaxis;
    @Default
    private List<String> yaxis = new ArrayList<>();
    @Default
    private List<String> yaxis2 = new ArrayList<>();
    private List<String> queries;
    private Object data;
    private String xaxisFormat;
    private String yaxisFormat;

    public String getXaxis() {
        if (xaxis == null) {
            return "time";
        }
        return xaxis;
    }
}

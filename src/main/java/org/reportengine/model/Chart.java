package org.reportengine.model;

import java.util.ArrayList;
import java.util.List;

import org.reportengine.Enums.CHART_TYPE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chart {
    private String name;
    private Boolean enabled = true;
    private CHART_TYPE type;
    private boolean metric = false;
    private String xaxis;
    private List<String> yaxis = new ArrayList<>();
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

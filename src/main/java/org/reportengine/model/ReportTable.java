package org.reportengine.model;

import java.util.List;
import java.util.Map;

import lombok.Builder.Default;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportTable {
    @Default
    private Boolean enabled = true;
    private List<String> order;
    private Map<String, String> columns;
    private List<Map<String, Object>> rows;
}

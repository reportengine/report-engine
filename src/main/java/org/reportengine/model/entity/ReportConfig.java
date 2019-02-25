package org.reportengine.model.entity;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.reportengine.model.Chart;
import org.reportengine.model.Detailed;
import org.reportengine.model.ReportTable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "report_config")
public class ReportConfig {

    @Id
    private String id;

    @NotEmpty
    @Indexed(unique = true)
    private String name;

    @NotEmpty
    private String type;

    @NotNull
    private Map<String, String> labels;

    @NotNull
    private ReportTable table;

    @NotNull
    private List<Chart> charts;

    @NotNull
    private Detailed detailed;

    public String getType() {
        return type.replaceAll("[^A-Za-z0-9-_]", "");
    }
}

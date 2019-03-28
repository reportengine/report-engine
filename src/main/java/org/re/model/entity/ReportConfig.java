package org.re.model.entity;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.re.model.Chart;
import org.re.model.Detailed;
import org.re.model.ReportTable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private List<ReportTable> tables;

    @NotNull
    private List<Chart> charts;

    @NotNull
    private Detailed detailed;

    public String getType() {
        return type.replaceAll("[^A-Za-z0-9-_]", "");
    }
}

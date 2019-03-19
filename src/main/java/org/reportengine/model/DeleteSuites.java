package org.reportengine.model;

import java.util.HashMap;
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
public class DeleteSuites {
    private String name;
    private String type;
    @Default
    private Map<String, String> labels = new HashMap<>();
    private Boolean ready;

    public boolean isValid() {
        if (name != null) {
            return true;
        }
        if (type != null) {
            return true;
        }
        if (ready != null) {
            return true;
        }
        if (labels != null && labels.size() > 0) {
            return true;
        }
        return false;
    }
}

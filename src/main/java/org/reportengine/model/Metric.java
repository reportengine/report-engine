package org.reportengine.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotEmpty;

import org.influxdb.dto.Point;
import org.reportengine.BeanUtil;
import org.reportengine.exception.ResourceNotFoundException;
import org.reportengine.model.entity.Suite;
import org.reportengine.service.SuiteService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Metric {

    @NotEmpty
    private String suiteId;

    @NotEmpty
    private String name;

    private Long timestamp;

    private Map<String, String> labels;

    @NotEmpty
    private Map<String, Object> data;

    public Point getPoint() {
        SuiteService suiteService = BeanUtil.getBean(SuiteService.class);
        Optional<Suite> suite = suiteService.get(getSuiteId());
        if (!suite.isPresent()) {
            throw new ResourceNotFoundException("Suite", "suiteId", getSuiteId());
        }

        Map<String, String> _tags = new HashMap<>();
        _tags.put("name", getName());
        _tags.put("suiteId", getSuiteId());

        if (labels != null) {
            for (String key : labels.keySet()) {
                if (!(key.equalsIgnoreCase("suiteId") || key.equalsIgnoreCase("name"))) {
                    _tags.put(key.toLowerCase(), labels.get(key));
                }
            }
        }

        return Point
                .measurement(suite.get().getType())
                .time(getTimestamp() == null ? System.currentTimeMillis()
                        : getTimestamp(), TimeUnit.MILLISECONDS).tag(_tags)
                .fields(getData()).build();
    }
}

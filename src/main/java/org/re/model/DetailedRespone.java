package org.re.model;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DetailedRespone {

    @NotNull
    private Map<String, Object> description;

    @NotNull
    private List<Object> charts;

    @NotNull
    private List<String> files;
}

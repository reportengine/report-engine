package org.re.model;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Detailed {

    @NotNull
    private Map<String, String> description;

    @NotNull
    private List<Chart> charts;

    @NotNull
    private List<String> files;
}

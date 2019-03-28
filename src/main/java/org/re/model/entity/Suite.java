package org.re.model.entity;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@Document(collection = "suite")
public class Suite {

    @Id
    private String id;

    @NotEmpty
    @Indexed(unique = false)
    private String type;

    @NotEmpty
    private String name;

    private Long createdAt;

    @NotNull
    private Boolean ready;

    @NotNull
    private Map<String, String> labels;

    @NotNull
    private Map<String, ?> data;

    public String getType() {
        return type.replaceAll("[^A-Za-z0-9-_]", "");
    }
}

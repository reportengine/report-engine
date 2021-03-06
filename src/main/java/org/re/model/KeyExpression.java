package org.re.model;

import java.util.HashMap;
import java.util.Map;

import org.re.utils.ExpressionEvaluator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyExpression {
    private String key;
    private String expression;

    @Default
    private Map<String, Object> data = new HashMap<>();

    public boolean isValid() {
        return expression != null;
    }

    public void add(String key, Object value) {
        data.put(key, value);
    }

    public Object evaluate() {
        if (expression != null) {
            ExpressionEvaluator evaluator = new ExpressionEvaluator();
            return evaluator.execute(expression, data);
        }
        return null;
    }
}

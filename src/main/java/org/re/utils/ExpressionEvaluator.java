package org.re.utils;

import java.util.HashMap;
import java.util.Map;

import org.scijava.parse.eval.DefaultEvaluator;
import org.scijava.parse.eval.Evaluator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpressionEvaluator {

    public Object execute(String expression, Map<String, Object> data) {
        _logger.trace("Expression:{}, data:{}", expression, data);
        try {
            Evaluator evaluator = new DefaultEvaluator();
            if (data != null) {
                evaluator.setAll(data);
            }
            return evaluator.evaluate(expression);
        } catch (Exception ex) {
            _logger.error("Expression:{}, data:{}", expression, data, ex);
            return "";
        }

    }

    public static void main(String[] args) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("self", 234211134);
        _logger.debug("Final data: " + evaluator.execute("(self/600/100.0)", data));
    }
}

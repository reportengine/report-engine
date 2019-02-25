package org.reportengine.utils;

import java.util.Map;

import org.scijava.parse.eval.DefaultEvaluator;
import org.scijava.parse.eval.Evaluator;

public class ExpressionEvaluator {

    public Object execute(String expression, Map<String, Object> data) {
        Evaluator evaluator = new DefaultEvaluator();
        if (data != null) {
            evaluator.setAll(data);
        }
        return evaluator.evaluate(expression);
    }
}

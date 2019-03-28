package org.re.utils;

import java.util.Map;

public class CommonUtils {

    @SuppressWarnings("unchecked")
    public static Object getValue(Map<String, Object> map, String key, Object defaultValue) {
        String[] keys = key.split("\\.");
        Object value = null;
        Map<String, Object> _source = map;
        int keysSize = keys.length;
        while (keysSize > 0) {
            if (keysSize == 1) {
                value = _source.get(keys[keys.length - keysSize]);
                break;
            } else {
                _source = (Map<String, Object>) _source.get(keys[keys.length - keysSize]);
                keysSize--;
            }
            if (_source == null) {
                break;
            }
        }
        if (value != null) {
            return value;
        }
        return defaultValue;
    }
}

package io.github.gdpl2112.database.e1;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class QueryWrapper {
    public Map<String, Object> k2v = new HashMap<>();

    public QueryWrapper add(String key, Object v) {
        k2v.put(key,v);
        return this;
    }
}

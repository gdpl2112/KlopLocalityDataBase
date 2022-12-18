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
public class UpdateWrapper extends QueryWrapper{
    public Map<String, Object> gk2v = new HashMap<>();

    public UpdateWrapper set(String key, Object v) {
        gk2v.put(key, v);
        return this;
    }
}

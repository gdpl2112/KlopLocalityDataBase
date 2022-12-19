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
    private Map<String, Object> eqK2v = new HashMap<>();
    private Map<String, Number> lessK2v = new HashMap<>();
    private Map<String, Number> graK2v = new HashMap<>();
    private Map<String, Object> ueqK2v = new HashMap<>();

    public int getSize() {
        return eqK2v.size() + lessK2v.size() + graK2v.size() + ueqK2v.size();
    }

    public QueryWrapper add(String key, Object v) {
        eqK2v.put(key, v);
        return this;
    }

    /**
     * 添加小于条件
     *
     * @param key
     * @param v
     * @return
     */
    public QueryWrapper less(String key, Number v) {
        lessK2v.put(key, v);
        return this;
    }

    /**
     * 添加大于条件
     *
     * @param key
     * @param v
     * @return
     */
    public QueryWrapper gra(String key, Number v) {
        graK2v.put(key, v);
        return this;
    }


    /**
     * 添加不等于条件
     *
     * @param key
     * @param v
     * @return
     */
    public QueryWrapper ueq(String key, Object v) {
        ueqK2v.put(key, v);
        return this;
    }
}

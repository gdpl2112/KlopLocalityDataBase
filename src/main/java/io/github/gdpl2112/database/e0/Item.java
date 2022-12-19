package io.github.gdpl2112.database.e0;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.gdpl2112.database.Table.EMPTY_ITEM;

/**
 * @author github.kloping
 */
@Data
@EqualsAndHashCode
@ToString
public class Item {
    private Map<String, FieldValue> line = new LinkedHashMap<>();

    public void setLine(Map<String, FieldValue> line) {
        this.line = line;
    }

    public <T> T toJavaClass(Class<T> type) {
        if (this.equals(EMPTY_ITEM)) return null;
        JSONObject jo = new JSONObject();
        line.forEach((k, v) -> {
            jo.put(k, v.toObj());
        });
        return jo.toJavaObject(type);
    }
}

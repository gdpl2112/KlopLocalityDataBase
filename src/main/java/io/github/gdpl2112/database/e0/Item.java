package io.github.gdpl2112.database.e0;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

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
}

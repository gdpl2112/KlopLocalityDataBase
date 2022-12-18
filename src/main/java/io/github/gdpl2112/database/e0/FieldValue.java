package io.github.gdpl2112.database.e0;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.object.ObjectUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Type;

/**
 * @author github.kloping
 */
@Data
@EqualsAndHashCode
@ToString
public class FieldValue {
    private Field field;
    private Object object;

    public <T> T toObj() {
        Class cla = null;
        try {
            cla = Class.forName(field.getClassName());
            if (ObjectUtils.isBaseOrPack(cla)) {
                return (T) ObjectUtils.maybeType(object.toString());
            } else if (cla == String.class) {
                return (T) object.toString();
            } else if (object instanceof JSONObject) {
                JSONObject jo = (JSONObject) object;
                return jo.toJavaObject((Type) cla);
            } else {
                return (T) object;
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}

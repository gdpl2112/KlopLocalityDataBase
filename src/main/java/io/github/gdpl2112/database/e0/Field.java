package io.github.gdpl2112.database.e0;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author github.kloping
 */
@Data
@EqualsAndHashCode
@ToString
public class Field implements Comparable<Field> {
    private String name;
    private Boolean unique = false;
    private String className;
    /**
     * 如果为 true 则 className 必须为 java.lang.Integer
     */
    private Boolean increment = false;
    private Integer st = 1;

    @Override
    public int compareTo(Field o) {
        return st - o.st;
    }
}

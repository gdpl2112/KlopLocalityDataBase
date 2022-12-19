package test;

import io.github.gdpl2112.database.anno.TableId;
import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class PerSon {
    @TableId(increment = true, unique = true)
    private Integer id;
    private String name;
    private Integer age;

    public PerSon() {
    }

    public PerSon(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

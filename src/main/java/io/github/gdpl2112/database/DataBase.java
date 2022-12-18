package io.github.gdpl2112.database;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.gdpl2112.database.builder.TableBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author github.kloping
 */
@Data
@EqualsAndHashCode
@ToString
public class DataBase {
    private String name;
    protected Map<String, Table> tableMap = new HashMap<>(1);

    public Boolean delete() {
        try {
            KlopLocalityDataBase.INSTANCE.dataBaseMap.remove(name);
            KlopLocalityDataBase.INSTANCE.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public DataBase use() {
        KlopLocalityDataBase.INSTANCE.now = this;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public List<String> getTableNames() {
        return new LinkedList<>(tableMap.keySet());
    }

    public Table getTable(String name) {
        return tableMap.get(name);
    }

    public TableBuilder createTable(String name) {
        TableBuilder builder = new TableBuilder(name, this);
        return builder;
    }

    public void flush() {
        KlopLocalityDataBase.INSTANCE.apply();
    }
}

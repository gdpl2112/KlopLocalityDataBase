package test;

import io.github.gdpl2112.database.KlopLocalityDataBase;
import io.github.gdpl2112.database.Table;
import io.github.gdpl2112.database.e0.Item;
import io.github.gdpl2112.database.e1.QueryWrapper;

import java.util.List;

/**
 * @author github.kloping
 */
public class InsertItem {
    public static void main(String[] args) {
        Table table = KlopLocalityDataBase.createDefault()
                .getAndUse().getTable("table0");
        table.insert(null, "张三", 21);
        table.insert(null, "李四", 22);
        table.insert(null, "王五", 20);
        System.out.println(table);

    }
}

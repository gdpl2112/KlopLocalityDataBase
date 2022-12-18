package test;

import io.github.gdpl2112.database.KlopLocalityDataBase;
import io.github.gdpl2112.database.Table;
import io.github.gdpl2112.database.e0.Item;
import io.github.gdpl2112.database.e1.QueryWrapper;

import java.util.List;

/**
 * @author github.kloping
 */
public class SelectItem {
    public static void main(String[] args) {
        Table table = KlopLocalityDataBase.createDefault()
                .getAndUse().getTable("table0");
        List<Item> items = table.selectListBy(new QueryWrapper());
        System.out.println(items);
        Item item= table.selectOneById(1);
        System.out.println(item);
    }
}

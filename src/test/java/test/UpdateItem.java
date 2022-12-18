package test;

import io.github.gdpl2112.database.KlopLocalityDataBase;
import io.github.gdpl2112.database.Table;
import io.github.gdpl2112.database.e0.Item;
import io.github.gdpl2112.database.e1.UpdateWrapper;

import java.util.List;

/**
 * @author github.kloping
 */
public class UpdateItem {
    public static void main(String[] args) {
        Table table = KlopLocalityDataBase.createDefault()
                .getAndUse().getTable("table0");
        Item item = table.selectOneById(1);
        UpdateWrapper updateWrapper = (UpdateWrapper) new UpdateWrapper()
                .set("name", "李狗蛋")
                .add("id", 1);
        List<Item> items = table.update(updateWrapper);
        System.out.println(items);
    }
}

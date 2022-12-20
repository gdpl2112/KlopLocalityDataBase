package test;

import io.github.gdpl2112.database.KlopLocalityDataBase;
import io.github.gdpl2112.database.Table;
import io.github.gdpl2112.database.e0.Item;

/**
 * @author github.kloping
 */
public class DeleteItem {
    public static void main(String[] args) {
        Table table = KlopLocalityDataBase.createDefault()
                .getAndUse().getTable("table0");
        Item item = table.deleteById(1);
        System.out.println(item);
    }
}

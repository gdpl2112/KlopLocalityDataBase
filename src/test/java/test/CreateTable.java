package test;

import io.github.gdpl2112.database.DataBase;
import io.github.gdpl2112.database.KlopLocalityDataBase;
import io.github.gdpl2112.database.Table;

/**
 * @author github.kloping
 */
public class CreateTable {
    public static void main(String[] args) {
        DataBase dataBase = KlopLocalityDataBase.createDefault()
                //若未创建过数据库 则 为null
                .getAndUse();
        Table table = dataBase.createTable("table0")
                .addField(Integer.class)
                .isUnique(true)
                .isIncrement(true)
                .setName("id")
                .addField(String.class)
                .setName("name")
                .addField(Integer.class)
                .setName("age").build();
        System.out.println(table);
    }
}

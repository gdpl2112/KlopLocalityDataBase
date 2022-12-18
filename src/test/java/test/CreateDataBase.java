package test;

import io.github.gdpl2112.database.DataBase;
import io.github.gdpl2112.database.KlopLocalityDataBase;

/**
 * @author github.kloping
 */
public class CreateDataBase {
    public static void main(String[] args) {
        DataBase dataBase = createDataBase("db0");
        System.out.println(dataBase);
    }

    public static DataBase createDataBase(String name) {
        DataBase dataBase = KlopLocalityDataBase.createDefault()
//                .createDataBase(name);
                .createAndUseDataBase(name);
        return dataBase;
    }
}

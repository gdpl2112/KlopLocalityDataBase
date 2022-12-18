import io.github.gdpl2112.database.KlopLocalityDataBase;
import io.github.gdpl2112.database.KlopLocalityDataBaseProxy;
import io.github.gdpl2112.database.e1.QueryWrapper;
import test.PerSon;
import test.TestTable;

import java.util.List;

/**
 * @author github.kloping
 */
public class Main {
    public static void main(String[] args) {
        KlopLocalityDataBase.createDefault().createAndUseDataBase("db0");
        TestTable table = KlopLocalityDataBaseProxy.INSTANCE.generate(TestTable.class);
//        table.insert(new PerSon("李六", 10));
//        table.insert(new PerSon("张三", 15));
//        son = table.selectOneById(1);
        List<PerSon> list = table.selectByWrapper(new QueryWrapper());

        System.out.println();
    }
}

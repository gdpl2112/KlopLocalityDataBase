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
        PerSon son = new PerSon("李六A", 20);
        son.setId(1);
        table.insert(son);
//        table.insert(new PerSon("张三", 15));
//        son = table.selectOneById(1);
        List<PerSon> list = table.selectByWrapper(new QueryWrapper().ueq("name", "王五"));

        System.out.println();
    }
}

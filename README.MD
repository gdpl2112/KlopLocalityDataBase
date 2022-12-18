### KlopLocalityDataBase

内嵌至文件的简易数据库

使用方式

```xml

<dependency>
    <groupId>io.github.gdpl2112</groupId>
    <artifactId>ForbiddenWordsPlugin</artifactId>
    <version>${version}</version>
</dependency>
```

## 使用

```java
package test;

import io.github.gdpl2112.database.anno.DataTable;
import io.github.gdpl2112.database.e0.BaseTable;
import test.PerSon;

/**
 * @author github.kloping
 */
@DataTable
public interface TestTable extends BaseTable<PerSon> {

}
```

```java
package test;

import io.github.gdpl2112.database.anno.TableId;
import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class PerSon {
    public PerSon() {
    }

    public PerSon(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @TableId(increment = true, unique = true)
    private Integer id;

    private String name;

    private Integer age;
}

```


```java
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
```
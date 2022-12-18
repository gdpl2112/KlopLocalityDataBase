package io.github.gdpl2112.database;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.initialize.FileInitializeValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
public class KlopLocalityDataBase {
    public static KlopLocalityDataBase INSTANCE;

    public static KlopLocalityDataBase createDefault() {
        INSTANCE = new KlopLocalityDataBase();
        return INSTANCE;
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            INSTANCE.apply();
        }));
    }

    public KlopLocalityDataBase() {
        reload();
    }

    private String dataFile = "./database.kdb";

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    protected Map<String, DataBase> dataBaseMap = new HashMap<>();

    /**
     * 适用于更改数据文件后的重载
     */
    public void reload() {
        dataBaseMap = FileInitializeValue.getValue(dataFile, dataBaseMap);
        for (String s : dataBaseMap.keySet()) {
            Object o = (Object) dataBaseMap.get(s);
            if (o != null && o instanceof JSONObject) {
                JSONObject jo = (JSONObject) o;
                DataBase db = jo.toJavaObject(DataBase.class);
                dataBaseMap.put(s, db);
            } else continue;
        }
    }

    protected DataBase now = null;

    /**
     * 创建一个数据库 创建失败返回null 成功返回实例
     *
     * @param name
     * @return
     */
    public synchronized DataBase createDataBase(String name) {
        if (dataBaseMap.containsKey(name)) return null;
        DataBase dataBase = new DataBase();
        dataBase.setName(name);
        dataBaseMap.put(name, dataBase);
        dataBase.flush();
        return dataBase;
    }

    /**
     * {@link KlopLocalityDataBase#createDataBase(String)}
     * 创建并使用
     *
     * @param name
     * @return
     */
    public synchronized DataBase createAndUseDataBase(String name) {
        DataBase dataBase = createDataBase(name);
        now = dataBase;
        return dataBase;
    }

    /**
     * 保存数据
     */
    protected void apply() {
        FileInitializeValue.putValues(
                dataFile, dataBaseMap
        );
    }

    /**
     * 获取一个已将存在的数据库并置于当前
     *
     * @return
     */
    public DataBase getAndUse() {
        DataBase dataBase = dataBaseMap.values().iterator().next();
        now = dataBase.use();
        return dataBase;
    }

    /**
     * 获取当前数据库
     *
     * @return
     */
    public DataBase get() {
        return now;
    }
}

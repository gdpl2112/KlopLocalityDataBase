package io.github.gdpl2112.database;

import io.github.gdpl2112.database.e0.Field;
import io.github.gdpl2112.database.e0.FieldValue;
import io.github.gdpl2112.database.e0.Item;
import io.github.gdpl2112.database.e1.QueryWrapper;
import io.github.gdpl2112.database.e1.UpdateWrapper;
import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author github.kloping
 */
@Data
public class Table {
    private String name;
    private Map<String, Field> fieldMap = new LinkedHashMap<>();
    private List<Item> items = new LinkedList<>();

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setFieldMap(Map<String, Field> fieldMap) {
        List<Map.Entry<String, Field>> entryList1 = new ArrayList<>(fieldMap.entrySet());
        Collections.sort(entryList1, new Comparator<Map.Entry<String, Field>>() {
            @Override
            public int compare(Map.Entry<String, Field> me1, Map.Entry<String, Field> me2) {
                return me1.getValue().compareTo(me2.getValue());
            }
        });
        this.fieldMap.clear();
        for (Map.Entry<String, Field> s2f : entryList1) {
            this.fieldMap.put(s2f.getKey(), s2f.getValue());
        }
    }

    public DataBase delete() {
        KlopLocalityDataBase.INSTANCE.now.getTableMap().remove(name);
        KlopLocalityDataBase.INSTANCE.apply();
        return KlopLocalityDataBase.INSTANCE.now;
    }

    public Boolean insert(Object... objects) {
        try {
            Item item = new Item();
            int n = fieldMap.keySet().size();
            Iterator<String> iterator0 = fieldMap.keySet().iterator();
            for (int i = 0; i < n; i++) {
                Object o = objects[i];
                String name = iterator0.next();
                Field field = fieldMap.get(name);
                if (field.getIncrement()) {
                    if (o == null) {
                        int id = getMax(items, field);
                        o = id + 1;
                    }
                }
                FieldValue value = new FieldValue();
                value.setField(field);
                value.setObject(o);
                item.getLine().put(field.getName(), value);
            }
            items.add(item);
            KlopLocalityDataBase.INSTANCE.now.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getMax(List<Item> items, Field field) {
        int id = 0;
        for (Item item : items) {
            Integer i0 = item.getLine().get(field.getName()).toObj();
            id = i0 > id ? i0 : id;
        }
        return id;
    }

    public List<Item> selectListBy(QueryWrapper wrapper) {
        List<Item> is = new LinkedList<>();
        AtomicInteger r = new AtomicInteger();
        for (Item item : items) {
            selectPre(r, item, wrapper.getK2v(), wrapper);
            if (r.get() == wrapper.k2v.size()) {
                is.add(item);
            }
        }
        return is;
    }

    /**
     * Field Has Unique AND Increment IS Integer
     *
     * @param id
     * @return
     */
    public Item selectOneById(Integer id) {
        AtomicReference<Item> i = new AtomicReference<>();
        for (Item item : items) {
            for (String k : item.getLine().keySet()) {
                FieldValue v = item.getLine().get(k);
                Field field = getFieldMap().get(k);
                if (field.getUnique() && field.getIncrement() && field.getClassName().equals(Integer.class.getName())) {
                    if (v.toObj().equals(id)) {
                        i.set(item);
                        break;
                    }
                }
            }
        }
        return i.get();
    }

    /**
     * Field Has Unique AND String
     *
     * @param key
     * @return
     */
    public Item selectOneByKey(String key) {
        AtomicReference<Item> i = null;
        for (Item item : items) {
            item.getLine().forEach((k, v) -> {
                Field field = getFieldMap().get(k);
                if (field.getUnique() && field.getClassName().equals(String.class.getName())) {
                    if (v.equals(key)) {
                        i.set(item);
                        return;
                    }
                }
            });
        }
        return i.get();
    }

    public List<Item> update(UpdateWrapper wrapper) {
        List<Item> is = new LinkedList<>();
        AtomicInteger r = new AtomicInteger();
        for (Item item : items) {
            selectPre(r, item, wrapper.getK2v(), wrapper);
            if (r.get() == wrapper.k2v.size()) {
                wrapper.getGk2v().forEach((k, v) -> {
                    Field field = fieldMap.get(k);
                    item.getLine().get(k).setObject(v);
                });
                is.add(item);
            }
        }
        if (!is.isEmpty()) {
            KlopLocalityDataBase.INSTANCE.now.flush();
        }
        return is;
    }

    public List<Item> deleteBy(QueryWrapper wrapper) {
        List<Item> is = selectListBy(wrapper);
        for (Item i : is) {
            items.remove(i);
        }
        if (!is.isEmpty()) {
            KlopLocalityDataBase.INSTANCE.now.flush();
        }
        return is;
    }

    /**
     * Field Has Unique AND Increment IS Integer
     *
     * @param id
     * @return
     */
    public Item deleteOneById(Integer id) {
        Item item = selectOneById(id);
        if (item != null) {
            items.remove(item);
            KlopLocalityDataBase.INSTANCE.now.flush();
        }
        return item;
    }

    /**
     * Field Has Unique AND String
     *
     * @param key
     * @return
     */
    public Item deleteOneByKey(String key) {
        Item item = selectOneByKey(key);
        if (item != null) {
            items.remove(item);
            KlopLocalityDataBase.INSTANCE.now.flush();
        }
        return item;
    }

    private void selectPre(AtomicInteger r, Item item, Map<String, Object> k2v, QueryWrapper wrapper) {
        r.set(0);
        item.getLine().forEach((k, v) -> {
            Object o = k2v.get(k);
            if (o == null) {
                return;
            } else {
                if (o.equals(v.toObj())) {
                    r.getAndIncrement();
                }
            }
        });
    }
}

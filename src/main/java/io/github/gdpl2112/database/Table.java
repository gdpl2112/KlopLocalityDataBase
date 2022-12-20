package io.github.gdpl2112.database;

import io.github.gdpl2112.database.anno.TableId;
import io.github.gdpl2112.database.e0.Field;
import io.github.gdpl2112.database.e0.FieldValue;
import io.github.gdpl2112.database.e0.Item;
import io.github.gdpl2112.database.e1.QueryWrapper;
import io.github.gdpl2112.database.e1.UpdateWrapper;
import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author github.kloping
 */
@Data
public class Table {
    public static final Item EMPTY_ITEM = new Item();

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
                if (field.getUnique()) {
                    if (!selectOneByKey(o).equals(EMPTY_ITEM))
                        return false;
                }
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
            selectPre(r, item, wrapper);
            if (r.get() == wrapper.getSize()) {
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
        for (Item item : items) {
            for (String k : item.getLine().keySet()) {
                FieldValue v = item.getLine().get(k);
                Field field = getFieldMap().get(k);
                if (field.getUnique() && field.getIncrement()) {
                    if (v.equals(id)) {
                        return item;
                    }
                }
            }
        }
        return EMPTY_ITEM;
    }

    /**
     * Field Has Unique AND String
     *
     * @param key
     * @return
     */
    public Item selectOneByKey(Object key) {
        for (Item item : items) {
            for (String k : item.getLine().keySet()) {
                FieldValue v = item.getLine().get(k);
                Field field = getFieldMap().get(k);
                if (field.getUnique()) {
                    if (v.equals(key)) {
                        return item;
                    }
                }
            }
        }
        return EMPTY_ITEM;
    }

    public List<Item> update(UpdateWrapper wrapper) {
        List<Item> is = new LinkedList<>();
        AtomicInteger r = new AtomicInteger();
        for (Item item : items) {
            selectPre(r, item, wrapper);
            if (r.get() == wrapper.getSize()) {
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

    public List<Item> updateById(Object o) {
        try {
            UpdateWrapper wrapper = new UpdateWrapper();
            for (java.lang.reflect.Field declaredField : o.getClass().getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(TableId.class)) {
                    declaredField.setAccessible(true);
                    Object o1 = declaredField.get(o);
                    wrapper.add(KlopLocalityDataBaseProxy.toLowName(declaredField.getName()), o1);
                } else {
                    declaredField.setAccessible(true);
                    Object o1 = declaredField.get(o);
                    wrapper.set(KlopLocalityDataBaseProxy.toLowName(declaredField.getName()), o1);
                }
            }
            return update(wrapper);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
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
    public Item deleteById(Integer id) {
        Item item = selectOneById(id);
        if (!item.equals(EMPTY_ITEM)) {
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
    public Item deleteByKey(String key) {
        Item item = selectOneByKey(key);
        if (!item.equals(EMPTY_ITEM)) {
            items.remove(item);
            KlopLocalityDataBase.INSTANCE.now.flush();
        }
        return item;
    }

    private void selectPre(AtomicInteger r, Item item, QueryWrapper wrapper) {
        r.set(0);
        item.getLine().forEach((k, v) -> {
            Object o;
            o = wrapper.getEqK2v().get(k);
            if (o != null)
                if (v.equals(o)) {
                    r.getAndIncrement();
                }
            o = wrapper.getUeqK2v().get(k);
            if (o != null)
                if (!v.equals(o)) {
                    r.getAndIncrement();
                }
            Number l;
            l = wrapper.getLessK2v().get(k);
            if (l != null)
                if (Long.valueOf(v.toObj().toString()) < l.longValue()) {
                    r.getAndIncrement();
                }
            l = wrapper.getGraK2v().get(k);
            if (l != null)
                if (Long.valueOf(v.toObj().toString()) > l.longValue()) {
                    r.getAndIncrement();
                }
        });
    }
}

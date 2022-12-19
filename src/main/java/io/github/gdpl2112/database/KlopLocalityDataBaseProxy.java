package io.github.gdpl2112.database;

import io.github.gdpl2112.database.anno.TableId;
import io.github.gdpl2112.database.builder.FieldBuilder;
import io.github.gdpl2112.database.builder.TableBuilder;
import io.github.gdpl2112.database.e0.BaseTable;
import io.github.gdpl2112.database.e0.Item;
import io.github.gdpl2112.database.e1.QueryWrapper;
import io.github.gdpl2112.database.e1.UpdateWrapper;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author github.kloping
 */
public class KlopLocalityDataBaseProxy implements InvocationHandler {
    public static final KlopLocalityDataBaseProxy INSTANCE = new KlopLocalityDataBaseProxy();
    private static final Map<Class, Object> O2C = new LinkedHashMap<>();
    private static final Random SECURE_RANDOM = new SecureRandom();

    public <T extends BaseTable> T generate(Class<T> cla) {
        Object t = Proxy.newProxyInstance(cla.getClassLoader(), new Class[]{cla}, this);
        O2C.put(cla, t);
        return (T) t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("hashCode".equals(method.getName())) return Objects.hashCode(proxy);
        AtomicReference<Class> interfaceClass = new AtomicReference<>();
        O2C.forEach((k, v) -> {
            if (v == proxy) {
                interfaceClass.set(k);
            }
        });
        Class type = getType(interfaceClass.get());
        if (type == null) return null;
        String tableName = toLowName(type.getSimpleName());
        Table table = KlopLocalityDataBase.INSTANCE.get().getTable(tableName);
        if (table == null) {
            table = createTable(tableName, type);
        }
        switch (method.getName()) {
            case "insert":
                return table.insert(type2objs(table, args[0])) ? 1 : 0;
            case "selectOneById":
                return table.selectOneById(Integer.valueOf(args[0].toString())).toJavaClass(type);
            case "selectOneByKey":
                return table.selectOneByKey(args[0].toString()).toJavaClass(type);
            case "selectByWrapper":
                QueryWrapper wrapper1 = (QueryWrapper) args[0];
                List<Item> items1 = table.selectListBy(wrapper1);
                List list1 = new ArrayList<>();
                for (Item item : items1)
                    list1.add(item.toJavaClass(type));
                return list1;
            case "deleteOneById":
                return table.deleteOneById(Integer.valueOf(args[0].toString())).toJavaClass(type);
            case "deleteOneByKey":
                return table.deleteOneByKey(args[0].toString()).toJavaClass(type);
            case "deleteByWrapper":
                QueryWrapper wrapper = (QueryWrapper) args[0];
                List<Item> items = table.deleteBy(wrapper);
                List list = new ArrayList<>();
                for (Item item : items)
                    list.add(item.toJavaClass(type));
                return list;
            case "updateByWrapper":
                UpdateWrapper wrapper2 = (UpdateWrapper) args[0];
                List<Item> items2 = table.update(wrapper2);
                List list2 = new ArrayList<>();
                for (Item item : items2)
                    list2.add(item.toJavaClass(type));
                return list2;
            case "updateById":
                List<Item> items3 = table.updateById(args[0]);
                List list3 = new ArrayList<>();
                for (Item item : items3)
                    list3.add(item.toJavaClass(type));
                return list3;
            default:
                return null;
        }
    }

    private Object[] type2objs(Table table, Object arg) {
        Object[] objects = new Object[table.getFieldMap().size()];
        Set<String> set = table.getFieldMap().keySet();
        for (Field declaredField : arg.getClass().getDeclaredFields()) {
            String name = declaredField.getName();
            name = toLowName(name);
            if (set.contains(name)) {
                int i = index(set, name);
                if (i >= 0) {
                    try {
                        declaredField.setAccessible(true);
                        Object o1 = declaredField.get(arg);
                        objects[i] = o1;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return objects;
    }

    private int index(Set<String> set, String name) {
        Iterator<String> itr = set.iterator();
        int i = 0;
        while (itr.hasNext()) {
            String n = itr.next();
            if (n.equals(name)) return i;
            else i++;
        }
        return -1;
    }


    private Table createTable(String tableName, Class type) {
        TableBuilder builder = KlopLocalityDataBase.INSTANCE.get().createTable(tableName);
        for (Field declaredField : type.getDeclaredFields()) {
            Class cla = declaredField.getType();
            FieldBuilder fb = builder.addField(cla);
            TableId tableId = declaredField.getAnnotation(TableId.class);
            if (tableId != null) {
                if (tableId.increment())
                    fb.isIncrement(true);
                if (tableId.unique())
                    fb.isUnique(true);
                String name = tableId.name();
                if (name.isEmpty())
                    builder = fb.setName(toLowName(declaredField.getName()));
            } else {
                builder = fb.setName(toLowName(declaredField.getName()));
            }
        }
        return builder.build();
    }

    public static final Pattern PATTERN = Pattern.compile("[A-Z]");

    public static String toLowName(String name) {
        String firstS = name.substring(0, 1).toLowerCase();
        name = firstS + name.substring(1);
        Matcher matcher = PATTERN.matcher(name);
        while (matcher.find()) {
            String g0 = matcher.group();
            String g1 = toLow(g0);
            name = name.replace(g0, g1);
        }
        return name;
    }

    private Class getType(Class cla) {
        try {
            Type[] types = cla.getGenericInterfaces();
            ParameterizedTypeImpl type0 = (ParameterizedTypeImpl) types[0];
            return (Class) type0.getActualTypeArguments()[0];
        } catch (Exception e) {
            return null;
        }
    }

    public static String toLow(String s1) {
        switch (s1.charAt(0)) {
            case 'A':
                return "_a";
            case 'B':
                return "_b";
            case 'C':
                return "_c";
            case 'D':
                return "_d";
            case 'E':
                return "_e";
            case 'F':
                return "_f";
            case 'G':
                return "_g";
            case 'H':
                return "_h";
            case 'I':
                return "_i";
            case 'J':
                return "_j";
            case 'K':
                return "_k";
            case 'L':
                return "_l";
            case 'M':
                return "_m";
            case 'N':
                return "_n";
            case 'O':
                return "_o";
            case 'P':
                return "_p";
            case 'Q':
                return "_q";
            case 'R':
                return "_r";
            case 'S':
                return "_s";
            case 'T':
                return "_t";
            case 'U':
                return "_u";
            case 'V':
                return "_v";
            case 'W':
                return "_w";
            case 'X':
                return "_x";
            case 'Y':
                return "_y";
            case 'Z':
                return "_z";
            default:
                return s1;
        }
    }
}

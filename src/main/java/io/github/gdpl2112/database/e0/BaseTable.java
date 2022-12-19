package io.github.gdpl2112.database.e0;

import io.github.gdpl2112.database.e1.QueryWrapper;
import io.github.gdpl2112.database.e1.UpdateWrapper;

import java.util.List;

/**
 * @author github.kloping
 */
public interface BaseTable<T> {
    /**
     * 插入一条
     *
     * @param e
     * @return
     */
    int insert(T e);

    /**
     * 删除一条
     *
     * @param id
     * @return
     */
    T deleteById(Integer id);

    /**
     * 删除一条
     *
     * @param id
     * @return
     */
    T deleteByKey(String id);

    /**
     * 删除多条
     *
     * @param wrapper
     * @return
     */
    List<T> deleteByWrapper(QueryWrapper wrapper);

    /**
     * 更新操作
     *
     * @param wrapper
     * @return
     */
    List<T> updateByWrapper(UpdateWrapper wrapper);

    /**
     * 更新操作
     *
     * @param t
     * @return
     */
    T updateById(T t);

    /**
     * 查询
     *
     * @param wrapper
     * @return
     */
    List<T> selectByWrapper(QueryWrapper wrapper);

    /**
     * 查询 {@link io.github.gdpl2112.database.Table#selectOneById(Integer)}
     *
     * @param id
     * @return
     */
    T selectOneById(Integer id);

    /**
     * 查询 {@link io.github.gdpl2112.database.Table#selectOneByKey(String)}
     *
     * @param key
     * @return
     */
    T selectOneByKey(String key);
}

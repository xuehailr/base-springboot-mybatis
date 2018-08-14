package com.airblock.utils;

/**
 *
 * @author yitianlin
 * @date 2018/6/4
 * T id 类型
 * E DO 类型
 */
public interface DataGateway<T, E> {
    String keyName();

    String getCache(String key);

    String getCacheKey(T id);

    E queryDBById(T id);

    void setCache(String cacheKey, E dbVal);

    T insertDB(E data);

    /**
     * 插入数据时是否缓存
     * @return
     */
    boolean cacheWhileInsert();

    int deleteDBById(T id);

    void deleteCache(String cacheKey);

    /**
     * 更新时，为了避免DB写入成功和更新cache之间发生错误导致数据不一致的情况
     * 为缓存提供过期时间，控制
     * @return
     */
    int softCacheTime();

    void softCache(T id);

    int updateDBById(T id, E data);
}
package com.airblock.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.ObjectUtils;

/**
 * 多层查询
 * Created by yitianlin on 2018/6/4.
 */
public class HierarchyAccessor {
    public static <T,E> E queryById(DataGateway<T, E> dataGateway, T id, Class<E> vclazz) {
        String cacheVal = dataGateway.getCache(dataGateway.getCacheKey(id));
        if (!ObjectUtils.isEmpty(cacheVal)) {
            return JSON.parseObject(cacheVal, vclazz);
        }
        E dbVal = dataGateway.queryDBById(id);
        dataGateway.setCache(dataGateway.getCacheKey(id), dbVal);
        return dbVal;
    }

    public static <T,E> T insert(DataGateway<T, E> dataGateway, E data, Class<T> clazz) {
        T id = dataGateway.insertDB(data);
        return id;
    }

    public static <T> int deleteById(DataGateway dataGateway, T id) {
        if (dataGateway.softCacheTime()>0) {
            dataGateway.softCache(id);
        }
        if (dataGateway.deleteDBById(id)<=0) {
            return 0;
        }
        dataGateway.deleteCache(dataGateway.getCacheKey(id));
        return 1;
    }

    public static <T, E> int updateById(DataGateway<T, E> dataGateway, E data, T id) {
        if (dataGateway.softCacheTime()>0) {
            dataGateway.softCache(id);
        }
        int result = dataGateway.updateDBById(id, data);

        dataGateway.setCache(dataGateway.getCacheKey(id),data);

        return result;

    }
}
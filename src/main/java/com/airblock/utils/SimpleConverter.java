package com.airblock.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.ObjectUtils;

/**
 * Created by yitianlin on 2018/6/4.
 */
public class SimpleConverter {
    public static <T> T convert(Object obj, Class<T> clz) {
        return ObjectUtils.isEmpty(obj) ? null : JSON.parseObject(JSON.toJSONString(obj), clz);
    }
}

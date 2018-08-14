package com.airblock.dataGateway;

import com.alibaba.fastjson.JSON;
import com.sohu.sns.rcmd.redis.JedisCluster;
import com.sohu.sns.rcmd.redis.JedisClusterPool;
import com.sohu.sns.user.mapping.common.UserService;
import com.sohu.sns.zhuanye.dao.ZYInfoDao;
import com.sohu.sns.zhuanye.domain.ZYInfoDO;
import com.sohu.sns.zhuanye.utils.DataGateway;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yitianlin on 2018/6/5.
 */
@Log
@Component
public class ZYDataGateway implements DataGateway<Long,ZYInfoDO> {

    @Autowired
    private ZYInfoDao zyInfoDao;
    @Autowired
    private JedisClusterPool jedisClusterPool;
    @Autowired
    private UserService userService;

    @Override
    public String keyName() {
        return "id";
    }

    @Override
    public String getCache(String key) {
        JedisCluster cluster = this.jedisClusterPool.getResource();

        String cache = cluster.get(key);

        cluster.close();

        return cache;
    }

    @Override
    public String getCacheKey(Long id) {
        return String.format("6001:%s", id);
    }

    @Override
    public ZYInfoDO queryDBById(Long id) {
        return zyInfoDao.queryById(id);
    }

    @Override
    public void setCache(String cacheKey, ZYInfoDO dbVal) {
        JedisCluster cluster = this.jedisClusterPool.getResource();
        cluster.set(cacheKey, JSON.toJSONString(dbVal));
        cluster.close();
    }

    @Override
    public Long insertDB(ZYInfoDO data) {

        data.setPpid("zy_"+data.getType()+"_"+data.getInfoId()+"@sohu.com");
        /** 调用usermapping接口获取suid */
        data.setId(Long.parseLong(userService.saveUserMappingInfoWithPId(data.getPpid(), "sns_zhuanye")));
        this.zyInfoDao.save(data);
        return data.getId();
    }

    @Override
    public boolean cacheWhileInsert() {
        return true;
    }

    @Override
    public int deleteDBById(Long id) {
        return this.zyInfoDao.delete(id);
    }

    @Override
    public void deleteCache(String cacheKey) {
        JedisCluster cluster = this.jedisClusterPool.getResource();
        cluster.del(cacheKey);
        cluster.close();
    }

    @Override
    public int softCacheTime() {
        return 10;
    }

    @Override
    public void softCache(Long id) {
        JedisCluster cluster = this.jedisClusterPool.getResource();
        cluster.expire(getCacheKey(id), softCacheTime());
        cluster.close();
    }

    @Override
    public int updateDBById(Long id, ZYInfoDO data) {
        return this.zyInfoDao.update(data, id)? 1: 0;
    }
}

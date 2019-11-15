package com.dhm.redis;


import org.apache.commons.lang3.StringUtils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
@Service
public class RedisManagerImpl implements RedisManager {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

   private final static StringRedisSerializer stringSerializer = new StringRedisSerializer();
    private final static GenericJackson2JsonRedisSerializer valueeSerializer = new GenericJackson2JsonRedisSerializer();

    /**
     * 往队列尾部添加
     */
    @Override
    public void rpushList(String key, String json) {
        redisTemplate.opsForList().rightPush(key, json);
    }

    /**
     * 往队列头部添加
     */
    @Override
    public void lpushList(String key, String json) {

        redisTemplate.opsForList().leftPush(key, json);
    }

    /**
     * 从队列尾部取出
     */
    @Override
    public Object rpopList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 从队列头部取出
     */
    @Override
    public Object lpopList(String key) {

        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 获取队列中所有元素
     */
    @Override
    public List<Object> getAllList(String key) {
        List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
        return list;
    }

    /**
     * 清空队列
     */
    @Override
    public void clearList(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 尾部放入集合元素
     */
    @Override
    public void lpushAllList(String key, Collection<String> collection) {

        redisTemplate.opsForList().leftPushAll(key, collection);
    }

    /**
     * 集合元素大小
     */
    @Override
    public int getListSize(String key) {

        return redisTemplate.opsForList().range(key, 0, -1).size();
    }

    /**
     * 头部放入集合元素
     */
    @Override
    public void rpushAllList(String key, Collection<String> collection) {

        redisTemplate.opsForList().rightPushAll(key, collection);
    }

    /**
     * 添加HashMap
     */
    @Override
    public void putHashObject(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 查询HashMap某个value
     */
    @Override
    public Object getHashObject(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 批量添加HashMap
     */
    @Override
    public void putAll(String key, Map<String, Object> objectMap) {
        if (objectMap == null || StringUtils.isBlank(key) || objectMap.size() == 0) {
            return;
        }
        redisTemplate.opsForHash().putAll(key, objectMap);
    }

    /**
     * 删除某个hashKey
     */
    @Override
    public void delHashkey(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断key是否存在
     */
    @Override
    public boolean haskey(String key) {
        return redisTemplate.hasKey(key);
    }



    /**
     * 根据key获取所有的hashkey
     */
    @Override
    public Set<Object> getHashKeySetBykey(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 根据key和hashkey的集合获取值
     */
    @Override
    public List<Object> getValueListBykey(String key, List<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * 设置Key的过期时间
     */
    @Override
    public void setkeyExpire(String key, long expire, TimeUnit unit) {
        redisTemplate.expire(key, expire, unit);
    }


    @Override
    public void test(String key, Object value) {

    }
    public static void main(String[] args) {
        RedisManager redisManager=new RedisManagerImpl();
        System.out.println(redisManager);
        HashMap hash=new HashMap();
        hash.put("qq","kk");
        redisManager.putAll("dhmd",hash);
    }

}

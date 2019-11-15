package com.dhm.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisManager {
    /**
     * 往队列尾部添加
     *
     * @param key
     * @param json
     */
    void rpushList(String key, String json);

    /**
     * 往队列头部添加
     *
     * @param key
     * @param json
     */
    void lpushList(String key, String json);

    /**
     * 从队列尾部取出
     */
    Object rpopList(String key);

    /**
     * 从队列头部取出
     */
    Object lpopList(String key);

    /**
     * 往获取队列中所有元素
     */
    List<Object> getAllList(String key);

    /**
     * 清空队列
     */
    void clearList(String key);

    /**
     * 尾部放入集合元素
     */
    void lpushAllList(String key, Collection<String> collection);

    /**
     * 头部放入集合元素
     */
    void rpushAllList(String key, Collection<String> collection);

    /**
     * 集合元素大小
     */
    int getListSize(String key);

    /**
     * 添加HashMap
     */
    void putHashObject(String key, String hashKey, Object hashValue);

    /**
     * 查询HashMap某个value
     */
    Object getHashObject(String key, String hashKey);

    /**
     * 批量添加HashMap
     */
    void putAll(String key, Map<String, Object> objectMap);

    /**
     * 删除某个hashKey
     */
    void delHashkey(String key, String hashKey);

    /**
     * 根据key获取所有的hashkey
     *
     * @param key
     * @return
     */
    Set<Object> getHashKeySetBykey(String key);

    /**
     * 根据key和hashkey的集合获取值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    List<Object> getValueListBykey(String key, List<Object> hashKeys);

    /**
     * 设置Key的过期时间
     *
     * @param key
     * @param expire
     * @param unit
     */
    void setkeyExpire(String key, long expire, TimeUnit unit);

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    boolean haskey(String key);

    void test(String key,Object value);


}


package com.lovesimly.mcache.core;

/**
 * 是不是需要考虑多线程的问题
 * 
 *
 * @param <V>
 */
public interface MCache<V> {
	
	/**
     * 更新多级缓存。
     *
     * @param key 主键
     * @throws Exception
     */
    void set(String key, V value) throws Exception;
    
    /**
     * 多级缓存获取数据
     *
     * @param key 主键
     * @return 结果
     * @throws Exception
     */
    V get(String key) throws Exception;

    
    /**
     * 从多级缓存中删除指定对象
     *
     * @param key 主键
     */
    void del(String key) throws Exception;

}

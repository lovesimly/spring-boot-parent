package com.lovesimly.mcache.remote;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.lovesimly.mcache.common.Constants;
import com.lovesimly.mcache.common.PubMessageDTO;

/**
 * 使用redisTemplate操作哨兵集群
 *
 * @param <V>
 */
public class RemoteCach<V> {

	@Resource  
	private RedisTemplate<String, Object> redisTemplate;

	public void set(String key, Object value,int expireTime) throws Exception {
		
		ValueOperations<String, Object> valueOperations= redisTemplate.opsForValue();
		valueOperations.set(key, value);
		 if(expireTime > 0){  
			 redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		 }
	}

	@SuppressWarnings("unchecked")
	public V get(String key) throws Exception {
		ValueOperations<String, Object> valueOperations= redisTemplate.opsForValue();
		return (V) valueOperations.get(key);
	}

	/**
	 * 发布响应的key value存入事情
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void pub(String cacheName,String key, Object value) {
		if(value==null)
		{
			String jonStr = JSON.toJSONString(new PubMessageDTO(cacheName,key,""));
			redisTemplate.convertAndSend(Constants.LOCAL_CACHE_CLUSTER, jonStr);
			redisTemplate.delete(key);
		}else {
			String jonStr = JSON.toJSONString(new PubMessageDTO(cacheName,key,value));
			redisTemplate.convertAndSend(Constants.LOCAL_CACHE_CLUSTER, jonStr);
		}
		
	}

}

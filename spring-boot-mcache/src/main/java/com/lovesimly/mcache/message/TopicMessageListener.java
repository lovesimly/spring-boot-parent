package com.lovesimly.mcache.message;


import javax.annotation.Resource;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.lovesimly.mcache.common.Constants;
import com.lovesimly.mcache.local.LocalCache;
import com.lovesimly.mcache.local.LocalCacheHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于Pub/sub的redis消息接收类，当收到消息后放入本地缓存
 *
 */
@Slf4j
public class TopicMessageListener implements MessageListener {

	@Resource  
	private RedisTemplate<String, String> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onMessage(Message message, byte[] pattern) {

		log.info( "message:" + message);
		  
		byte[] body = message.getBody();
		byte[] channel = message.getChannel();
		
		String message_str = (String) redisTemplate.getValueSerializer().deserialize(body);
		String channel_str = (String) redisTemplate.getStringSerializer().deserialize(channel);
		
		 if (channel_str.endsWith(Constants.LOCAL_CACHE_CLUSTER)) {
			 JSONObject jsonObject = JSON.parseObject(message_str);
             String name = (String)jsonObject.get("cacheName");
             String key = (String)jsonObject.get("key");
             String value = jsonObject.get("value").toString();
             
             LocalCache localCache = LocalCacheHolder.getLocalCacheHolder().get(name);
             if(localCache==null)
            	 return;
        	 if (Strings.isNullOrEmpty(value)) {
        		 localCache.getCache().invalidate(key);
    		 }else {
    			 if(localCache.getTypeReference().getType().toString().equals(Constants.strType))
    			  localCache.getCache().put(key, value);
    			 else
    			  localCache.getCache().put(key, JSON.parseObject(value, localCache.getTypeReference()));
             }
		 }
		
	}

}

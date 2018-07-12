package com.lovesimly.mcache.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.StringUtils;

import com.lovesimly.mcache.common.Constants;
import com.lovesimly.mcache.common.SpringContextHolder;
import com.lovesimly.mcache.message.TopicMessageListener;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 初始化哨兵模式的相关配置
 *
 */
@Configuration  
@EnableConfigurationProperties(McacheRedisProperties.class)  
@ConditionalOnProperty(name = "mcache.redis.sentinel")  
public class RedisSentinelConfig {  
    private Logger LOG = LoggerFactory.getLogger(RedisSentinelConfig.class);  
  
    @Resource  
    private McacheRedisProperties redisProperties;  
  
    public JedisPoolConfig jedisPoolConfig() {  
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());  
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());  
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());  
        return jedisPoolConfig;  
  
    }  
  
    /**
     * 哨兵模式的配置
     * @return
     */
    public RedisSentinelConfiguration jedisSentinelConfig() {  
        String[] hosts = redisProperties.getHostName().split(",");  
        HashSet<String> sentinelHostAndPorts = new HashSet<>();  
        for (String hn : hosts) {  
            sentinelHostAndPorts.add(hn);  
        }  
        return new RedisSentinelConfiguration(redisProperties.getMastername(), sentinelHostAndPorts);  
  
    }  
  
    @Bean  
    public JedisConnectionFactory jedisConnectionFactory() {  
  
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisSentinelConfig(),  
                jedisPoolConfig());  
        
        if (!StringUtils.isEmpty(redisProperties.getPassword()))  
            jedisConnectionFactory.setPassword(redisProperties.getPassword());  
        return jedisConnectionFactory;  
    }  
  
    
    @Bean  
    public RedisTemplate<String, Object> redisTemplate() {  
  
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();  
        redisTemplate.setConnectionFactory(jedisConnectionFactory());  
        
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        LOG.info("create RedisTemplate success");  
        return redisTemplate;  
    }  
    
    
	/**
	 * Container providing asynchronous behaviour for Redis message listeners.
	 * Handles the low level details of listening, converting and message dispatching
	 * 
	 * @return
	 */
    @Bean  
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public RedisMessageListenerContainer startMessageLisenter() {  
        
    	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    	container.setConnectionFactory(SpringContextHolder.getBean(JedisConnectionFactory.class));
    	ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
    	tpts.setPoolSize(3);
    	tpts.initialize();
    	
    	container.setTaskExecutor(tpts);
    
		Map listeners_Map = new HashMap<>();
    	
    	MessageListener ml = SpringContextHolder.getBean(TopicMessageListener.class);
    	
    	ChannelTopic ctp = new ChannelTopic(Constants.LOCAL_CACHE_CLUSTER);
    	Collection<ChannelTopic> topics = new ArrayList<>();
    	topics.add(ctp);
    	
    	listeners_Map.put(ml, topics);
    	
    	container.setMessageListeners(listeners_Map);
    	
    	return container;
    	
    }  
    
}  
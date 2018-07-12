package com.lovesimly.mcache;  
  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Import;

import com.lovesimly.mcache.common.SpringContextHolder;
import com.lovesimly.mcache.config.RedisClusterConfig;
import com.lovesimly.mcache.config.RedisSentinelConfig;
import com.lovesimly.mcache.message.TopicMessageListener;
import com.lovesimly.mcache.remote.RemoteCach;  
  
  
@Configuration  
@Import({ SpringContextHolder.class,RedisClusterConfig.class, RedisSentinelConfig.class, JedisClusterUtils.class,TopicMessageListener.class,RemoteCach.class })  
public class MacheAutoConfiguration {  
  
} 
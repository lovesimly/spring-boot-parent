package com.lovesimly.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.lovesimly.mcache.JedisClusterUtils;
import com.lovesimly.mcache.common.SpringContextHolder;
import com.lovesimly.mcache.config.RedisClusterConfig;
import com.lovesimly.mcache.config.RedisSentinelConfig;
import com.lovesimly.mcache.message.TopicMessageListener;
import com.lovesimly.mcache.remote.RemoteCach;


@SpringBootApplication
@EnableAsync
//@Import({ SpringContextHolder.class,RedisClusterConfig.class, RedisSentinelConfig.class, JedisClusterUtils.class,TopicMessageListener.class,RemoteCach.class })
public class MacacheAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacacheAppApplication.class, args);
	}
}

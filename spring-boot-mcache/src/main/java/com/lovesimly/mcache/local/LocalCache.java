package com.lovesimly.mcache.local;

import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 本地缓存，用来持有guava cache对象
 *
 * @param <V>
 */
@Setter
@Getter
@AllArgsConstructor
public class LocalCache<V> {

	  //guava cache
	  private Cache<String, V> cache;
	  
	  /**
	   * 本地缓存名
	   */
	  private String name;
	   
	  /**
	   * 缓存的过期时间
	   */
	  private int expireAfterWrite;
	  
	  //TODO是否可以查找Hibernate编译时候绑定 typeReference = new TypeReference<V>(){}
	  private TypeReference<V> typeReference;

}

package com.lovesimly.mcache.core;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lovesimly.mcache.local.LocalCache;
import com.lovesimly.mcache.local.LocalCacheHolder;

public class McacheBuilder<V> {
	
	 /** 本地缓存大小 */
    private int maximumSize;
    
    /** 是否使用远程缓存 */
    private boolean useRemote;
    
    /** 过期时间(距上次写入N秒) */
    private int expireAfterWrite;
    
    /** 远程缓存过期时间 */
    private int remoteExpireTime;
    
    /** 本地缓存名 */
    private String cacheName;
    
    /** 扩散到集群时的泛型类型 */
    private TypeReference<V> typeReference;
    
    
    private McacheBuilder(String cacheName, TypeReference<V> typeReference) {
        this.cacheName = cacheName;
        this.typeReference = typeReference;
    }
    
    /**
     * 新建缓存实例
     *
     * @param name 缓存名，不要重复
     * @return 缓存模板生成器
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static McacheBuilder<Object> newBuilder(String cacheName, TypeReference typeReference) {
        return new McacheBuilder<Object>(cacheName, typeReference);
    }
    
    
    /**
     * 设置本地缓存最大数量
     *
     * @param maximumSize 数量
     * @return 缓存模板生成器
     */
    public McacheBuilder<V> maximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
        return this;
    }

    /**
     * 本地缓存过期策略
     *
     * @param expireAfterWrite 过期时间
     * @return 缓存模板生成器
     */
    public McacheBuilder<V> expireAfterWrite(int expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
        this.remoteExpireTime = 2*expireAfterWrite;// set default remote expire time
        return this;
    }
    
    /**
     * 根据配置生成多级缓存实例
     *
     * @return 多级缓存
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <V1 extends V> MCache<V1> build() throws Exception {
    	
       Cache<String, V1> caches = CacheBuilder.newBuilder()
                 .maximumSize(maximumSize)
                 .expireAfterWrite(expireAfterWrite, TimeUnit.SECONDS).build();
    	 
    	LocalCache localCache = new LocalCache(caches,cacheName,expireAfterWrite,typeReference);
    	
    	LocalCacheHolder.getLocalCacheHolder().put(cacheName, localCache);
    	
    	return new MCacheTemplate(localCache,useRemote,remoteExpireTime);
    	
    }

	protected V generateValueByKey(String key) {
		return null;
	}
	
    /**
     * 增加远程缓存
     *
     * @param remoteExpireTime 过期时间
     * @return 缓存模板生成器
     */
    public McacheBuilder<V> useRemote(int remoteExpireTime) {
        this.useRemote = true;
        this.remoteExpireTime = remoteExpireTime;
        return this;
    }

    /**
     * 增加远程缓存(过期时间=本地缓存过期时间*2)
     *
     * @return 缓存模板生成器
     */
    public McacheBuilder<V> useRemote() {
        this.useRemote = true;
        return this;
    }
}

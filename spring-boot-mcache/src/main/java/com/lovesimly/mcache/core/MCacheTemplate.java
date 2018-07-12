package com.lovesimly.mcache.core;


import com.lovesimly.mcache.common.SpringContextHolder;
import com.lovesimly.mcache.local.LocalCache;
import com.lovesimly.mcache.remote.RemoteCach;

public class MCacheTemplate<V> implements MCache<V> {

	/** 本地缓存 */
	private LocalCache<V> localCache;

	@SuppressWarnings("rawtypes")
	private volatile static RemoteCach remoteCache;

	/** 是否使用远程缓存 */
	private boolean useRemote;

	/** 远程缓存过期时间 */
	private int expireTime;

	@Override
	public void set(String key, V value) throws Exception {
		if (key == null || value == null) {
			throw new Exception("Parameter can't be null.");
		}
		
		if (useRemote) {
			remoteCache.set(key, value,expireTime);
			remoteCache.pub(localCache.getName(), key, value);
		} else {
			localCache.getCache().put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(String key) throws Exception {
		if (key == null)
			throw new Exception("Parameter can't be null.");
		
		V value = localCache.getCache().getIfPresent(key);

		if (value == null && useRemote) {//本地过期了，因为本地可能过期时间比远端短
			V remoteValue = (V) remoteCache.get(key);
			return remoteValue;
		}
		
		return value;

	}

	@Override
	public void del(String key) throws Exception {
		remoteCache.pub(localCache.getName(), key, null);
	}

	/**
	 * 使用多级缓存
	 *
	 * @param localCache
	 *            本地缓存
	 * @param useRemote
	 *            是否使用远程缓存
	 */
	protected <V1 extends V> MCacheTemplate(LocalCache<V> localCache, boolean useRemote) throws Exception {
		this(localCache, useRemote, localCache.getExpireAfterWrite() * 2);
	}

	/**
	 * 使用多级缓存
	 *
	 * @param localCache
	 *            本地缓存
	 * @param useRemote
	 *            是否使用远程缓存
	 * @param remoteExpireTime
	 *            远程缓存过期时间
	 */
	protected <V1 extends V> MCacheTemplate(LocalCache<V> localCache, boolean useRemote, int remoteExpireTime)
			throws Exception {
		this.localCache = localCache;
		this.expireTime = remoteExpireTime;
		this.useRemote = useRemote;
		if (useRemote)
			initRemoteCache();
	}

	private static synchronized void initRemoteCache() {
		if (remoteCache == null) {
			remoteCache = SpringContextHolder.getBean(RemoteCach.class);
		}
	}

}

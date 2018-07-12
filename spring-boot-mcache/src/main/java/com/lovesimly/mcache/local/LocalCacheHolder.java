package com.lovesimly.mcache.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 此类用来持有所有的本地缓存引用
 * 
 *
 */
public class LocalCacheHolder<V> {

	/** 本地缓存集合 */
	private Map<String, LocalCache<V>> cacheMap;

	private LocalCacheHolder() {
		cacheMap = new ConcurrentHashMap<String, LocalCache<V>>();
	}

	@SuppressWarnings("rawtypes")
	private static LocalCacheHolder<?> localCacheHolder = new LocalCacheHolder();

	public static LocalCacheHolder<?> getLocalCacheHolder() {
		return localCacheHolder;
	}

	public void put(String name, LocalCache<V> cache) {
		cacheMap.put(name, cache);
	}

	public LocalCache<V> get(String name) {
		return cacheMap.get(name);
	}

	public void remove(String key) {
		cacheMap.remove(key);
	}

	/**
	 * 返回所有的本地缓存列表
	 * 
	 * @return
	 */
	public List<LocalCache<V>> list() {
		List<LocalCache<V>> list = new ArrayList<LocalCache<V>>();
		for (Map.Entry<String, LocalCache<V>> entry : cacheMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}

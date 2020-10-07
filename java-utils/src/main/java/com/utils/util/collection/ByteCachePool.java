
package com.utils.util.collection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ByteCachePool {

	private static Queue<ByteCache> queue = new ConcurrentLinkedQueue<>();

	/**
	 * 获取一个cache
	 * @return
	 */
	public static ByteCache get() {
		ByteCache cache = queue.poll();
		if (cache == null) {
			cache = new ByteCache();
		}
		return cache;
	}

	/**
	 * 归还使用的cache
	 * @param cache
	 */
	public static void returnCache(ByteCache cache) {
		cache.clear();
		queue.offer(cache);
	}

	/**
	 * 缩小当前的缓存池
	 */
	public static void shink() {
		queue.clear();
	}
}

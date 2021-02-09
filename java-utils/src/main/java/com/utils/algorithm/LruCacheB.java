package com.utils.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCacheB extends LinkedHashMap {

    private static final Logger log = LoggerFactory.getLogger(LruCacheB.class);

    // 缓存大小
    private int capacity;


    public LruCacheB(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LruCacheB cache = new LruCacheB(10);

        for (int i = 0; i < 10; i++) {
            cache.put("k" + i, i);
        }
        log.info("all cache :'{}'",cache);
        cache.get("k3");
        log.info("get k3 :'{}'", cache);
        cache.get("k4");
        log.info("get k4 :'{}'", cache);
        cache.get("k4");
        log.info("get k4 :'{}'", cache);
        cache.put("k" + 10, 10);
        log.info("After running the LRU algorithm cache :'{}'", cache);
    }


}

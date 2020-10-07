package com.utils.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class RedisScriptRunner {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private Map<String, DefaultRedisScript<?>> scripts = new HashMap<>();
    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    /**
     * rpush 后，将总长度 ltrim 至 maxLen
     *
     * @param key       redis list key
     * @param pushValue rpush 的 value
     * @param maxLen    redis list 总长度
     * @return 执行完成后 redis list 的长度
     */
    public Long rpushWithMaxLen(String key, Object pushValue, int maxLen) {
        RedisScript<Long> script = (RedisScript<Long>) scripts.get(RedisScriptNames.RPUSH_WITH_MAX_LEN);
        return redisTemplate.execute(script, Arrays.asList(key, String.valueOf(maxLen)), pushValue);
    }

    public Long zaddWithMaxLen(String key, Object pushValue, int maxLen) {

        DefaultRedisScript<Long> script = (DefaultRedisScript<Long>) scripts.get(RedisScriptNames.ZADD_WITH_MAX_LEN);
        return redisTemplate.execute(script, Arrays.asList(key), pushValue, System.currentTimeMillis(), maxLen);
    }

    public Long deleteMatchKey(String preKey) {
        DefaultRedisScript<Long> script = (DefaultRedisScript<Long>) scripts.get(RedisScriptNames.DELETE_MATCH_KEY);
        return redisTemplate.execute(script, Arrays.asList(preKey), redisDatabase);
    }

    @PostConstruct
    private void initScript() {
        loadScript(RedisScriptNames.RPUSH_WITH_MAX_LEN, Long.class);
        loadScript(RedisScriptNames.ZADD_WITH_MAX_LEN, Long.class);
        loadScript(RedisScriptNames.DELETE_MATCH_KEY, Long.class);
    }

    private <T> void loadScript(String name, Class<T> type) {
        ClassPathResource resource = new ClassPathResource("lua" + File.separator + name + ".lua");
        DefaultRedisScript<T> script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(resource));
        script.setResultType(type);

        scripts.put(name, script);
    }
}
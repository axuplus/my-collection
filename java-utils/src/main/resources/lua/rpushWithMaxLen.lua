redis.call("rpush", KEYS[1], ARGV[1])
redis.call("ltrim", KEYS[1], -KEYS[2], -1)
return redis.call("llen", KEYS[1])
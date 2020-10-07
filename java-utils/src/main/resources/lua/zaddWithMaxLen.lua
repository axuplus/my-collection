local size = redis.call("zcard",KEYS[1])

if( size > tonumber(ARGV[3]) )
    then
        redis.call("Zremrangebyrank",KEYS[1],0,1)
    end
return redis.call("ZADD",KEYS[1], ARGV[2] ,ARGV[1])
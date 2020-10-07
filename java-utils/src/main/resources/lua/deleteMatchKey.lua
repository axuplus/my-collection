redis.call("select",ARGV[1])

local cursor = 0
local keyNum = 0
repeat
    local res = redis.call("scan", cursor, "MATCH", KEYS[1])
    if (res ~= nil and #res >= 0) then
        cursor = tonumber(res[1])
        local ks = res[2]
        if (ks ~= nil and #ks > 0) then
            redis.replicate_commands()
            for i = 1, #ks, 1 do
                local key = tostring(ks[i])
                redis.call("del", key)
            end
            keyNum = keyNum + #ks
        end
    end
until (cursor <= 0)

return keyNum
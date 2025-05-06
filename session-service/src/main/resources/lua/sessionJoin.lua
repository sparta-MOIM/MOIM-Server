-- KEYS[1]: 잔여 좌석 키 (예: session:{sessionId}:remain)
-- KEYS[2]: 참가자 목록 SET 키 (예: session:{sessionId}:members)
-- KEYS[3]: 스트림 키 (전역 키: stream:session_join)
-- ARGV[1]: userId
-- ARGV[2]: sessionId
-- ARGV[3]: timestamp

-- 1. 중복 체크
local isMember = redis.call("SISMEMBER", KEYS[2], ARGV[1])
if isMember == 1 then
    return -2
end

-- 2. 잔여 좌석 확인
local count = redis.call("GET", KEYS[1])
if not count then
    return -1
end

count = tonumber(count)
if count <= 0 then
    return 0
end

-- 3. 좌석 차감 및 참가 등록
redis.call("DECR", KEYS[1])
redis.call("SADD", KEYS[2], ARGV[1])

-- 4. 전역 스트림에 참가 이벤트 기록
redis.call("XADD", KEYS[3], "*",
        "session_id", ARGV[2],
        "member_id", ARGV[1],
        "type", "GENERAL",
        "joined_at", ARGV[3]
)

return 1

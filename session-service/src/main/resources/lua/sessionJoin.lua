-- KEYS[1]: 잔여 좌석 키 (예: session:{sessionId}:remain)
-- KEYS[2]: 참가자 목록 SET 키 (예: session:{sessionId}:members)
-- ARGV[1]: userId (예: "user-123")

-- 1. 중복 참가 확인
local isMember = redis.call("SISMEMBER", KEYS[2], ARGV[1])
if isMember == 1 then
    return -2  -- 이미 참가한 사용자
end

-- 2. 잔여 좌석 확인
local count = redis.call("GET", KEYS[1])
if not count then
    return -1  -- 잔여 좌석 키 없음
end

count = tonumber(count)
if count <= 0 then
    return 0  -- 좌석 없음
end

-- 3. 좌석 차감 및 참가자 등록
redis.call("DECR", KEYS[1])
redis.call("SADD", KEYS[2], ARGV[1])

return 1  -- 성공

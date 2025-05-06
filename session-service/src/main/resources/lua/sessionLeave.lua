-- KEYS[1]: 잔여 좌석 키 (예: session:{sessionId}:remain)
-- KEYS[2]: 참가자 목록 SET 키 (예: session:{sessionId}:members)
-- KEYS[3]: 스트림 키 (전역 키: stream:session_leave)
-- ARGV[1]: userId
-- ARGV[2]: sessionId
-- ARGV[3]: timestamp

-- 1. 참가 여부 확인
local isMember = redis.call("SISMEMBER", KEYS[2], ARGV[1])
if isMember == 0 then
    return -2  -- 참가하지 않은 사용자
end

-- 2. 참가자 목록에서 제거
redis.call("SREM", KEYS[2], ARGV[1])

-- 3. 좌석 수 증가
redis.call("INCR", KEYS[1])

-- 4. 스트림에 퇴장 이벤트 기록
redis.call("XADD", KEYS[3], "*",
        "session_id", ARGV[2],
        "member_id", ARGV[1],
        "type", "GENERAL",
        "left_at", ARGV[3]
)

return 1

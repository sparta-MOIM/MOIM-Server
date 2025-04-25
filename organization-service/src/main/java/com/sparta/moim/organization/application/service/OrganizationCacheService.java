package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.infrastruct.dto.OrganizationMemberCache;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class OrganizationCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;
    private final SetOperations<String, String> setOps;

    // TTL 설정
    private final long TTL = 3 * 60 * 60; // 3시간

    @Autowired
    public OrganizationCacheService(
            RedisTemplate<String, Object> redisTemplate,
            RedisTemplate<String, String> redisStringTemplate
            ) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.setOps = redisStringTemplate.opsForSet();
    }

    // 개별 멤버 정보 캐싱
    public void cacheMemberInfo(OrganizationMemberCache member) {
        String memberKey = generateMemberKey(member.getOrganizationTrackingId(), member.getUserTrackingId());
        valueOps.set(memberKey, member, Duration.ofSeconds(TTL));
    }

    // 여러 멤버 정보 한 번에 캐싱 (벌크 작업)
    public void cacheMembersInfo(List<OrganizationMemberCache> members) {
        if (members == null || members.isEmpty()) {
            return;
        }

        Map<String, OrganizationMemberCache> memberMap = new HashMap<>();
        Set<String> memberIds = new HashSet<>();

        // 모든 멤버 정보를 맵에 저장하고 ID Set 구성
        for (OrganizationMemberCache member : members) {
            String memberKey = generateMemberKey(member.getOrganizationTrackingId(), member.getUserTrackingId());
            memberMap.put(memberKey, member);
            memberIds.add(member.getUserTrackingId());
        }

        // 멤버 정보 일괄 저장
        valueOps.multiSet(memberMap);

        // 모든 키에 TTL 설정
        memberMap.keySet().forEach(key -> redisTemplate.expire(key, Duration.ofSeconds(TTL)));

        // 모임 멤버 Set 저장
        String organizationTrackingId = members.get(0).getOrganizationTrackingId();
        String orgMembersKey = generateOrgMembersKey(organizationTrackingId);
        if (!memberIds.isEmpty()) {
            String[] memberIdArray = memberIds.toArray(new String[0]);
            setOps.add(orgMembersKey, memberIdArray);
            redisTemplate.expire(orgMembersKey, Duration.ofSeconds(TTL));
        }
    }

    // 멤버 ID를 모임의 멤버 Set에 추가(멤버가 모임에 추가된 경우)
    public void addMemberToOrganization(String organizationTrackingId, String userTrackingId) {
        String orgMembersKey = generateOrgMembersKey(organizationTrackingId);
        setOps.add(orgMembersKey, userTrackingId);
        redisTemplate.expire(orgMembersKey, Duration.ofSeconds(TTL));
    }

    // 멤버 ID를 모임의 멤버 Set에서 제거(멤버가 모임에서 제거된 경우)
    public void removeMemberFromOrganization(String organizationTrackingId, String userTrackingId) {
        String orgMembersKey = generateOrgMembersKey(organizationTrackingId);
        setOps.remove(orgMembersKey, userTrackingId);
    }

    // 개별 멤버 정보 조회
    public Optional<OrganizationMemberCache> getMemberInfo(String organizationTrackingId, String userTrackingId) {
        String memberKey = generateMemberKey(organizationTrackingId, userTrackingId);
        OrganizationMemberCache member = (OrganizationMemberCache) valueOps.get(memberKey);

        // 캐시 히트 시 TTL 갱신
        if (member != null) {
            redisTemplate.expire(memberKey, Duration.ofSeconds(TTL));
        }

        return Optional.ofNullable(member);
    }

    // 모임의 모든 멤버 ID 조회
    public Set<String> getOrganizationMemberIds(String organizationTrackingId) {
        String orgMembersKey = generateOrgMembersKey(organizationTrackingId);
        Set<String> memberIds = setOps.members(orgMembersKey);

        // 조회 시 TTL 갱신
        if (memberIds != null && !memberIds.isEmpty()) {
            redisTemplate.expire(orgMembersKey, Duration.ofSeconds(TTL));
        }
        else{
            return null;
        }

        return memberIds;
    }

    // 모임의 모든 멤버 정보 조회
    public List<OrganizationMemberCache> getAllMembersInOrganization(String organizationTrackingId) {
        // 1. 모임의 모든 멤버 ID 조회
        Set<String> memberIds = getOrganizationMemberIds(organizationTrackingId);
        if (memberIds==null || memberIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 모든 멤버 키 준비
        List<String> memberKeys = memberIds.stream()
                .map(userId -> generateMemberKey(organizationTrackingId, userId))
                .collect(Collectors.toList());

        // 3. 멤버 정보 일괄 조회
        List<Object> memberObjects = valueOps.multiGet(memberKeys);
        List<OrganizationMemberCache> members = new ArrayList<>();

        if (memberObjects != null) {
            for (Object obj : memberObjects) {
                if (obj != null) {
                    members.add((OrganizationMemberCache) obj);
                }
            }
        }

        return members;
    }

    // 멤버 정보 삭제 (캐시에서만 제거)
    public void deleteCacheMemberInfo(String organizationTrackingId, String userTrackingId) {
        String memberKey = generateMemberKey(organizationTrackingId, userTrackingId);
        redisTemplate.delete(memberKey);
    }

    // 모임의 모든 캐시 데이터 삭제
    public void deleteCacheOrganizationCache(String organizationTrackingId) {
        // 모임 멤버 Set 삭제
        String orgMembersKey = generateOrgMembersKey(organizationTrackingId);
        redisTemplate.delete(orgMembersKey);

        // 개별 멤버 캐시 삭제는 생략 (TTL로 관리)
    }

    // 키 생성 헬퍼 메서드
    private String generateMemberKey(String organizationTrackingId, String userTrackingId) {
        return "org:" + organizationTrackingId + ":user:" + userTrackingId;
    }

    private String generateOrgMembersKey(String organizationTrackingId) {
        return "org:" + organizationTrackingId + ":members";
    }
}
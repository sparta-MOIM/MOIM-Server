package com.sparta.moim.gathering.member.domain.repository;

import com.sparta.moim.gathering.member.domain.Member;
import com.sparta.moim.gathering.member.domain.enums.MemberType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.query.Param;

public interface MemberRepository {
  /**
 * Member 엔티티를 저장하고 저장된 인스턴스를 반환합니다.
 *
 * @param member 저장할 회원 엔티티
 * @return 저장된 회원 엔티티
 */
Member save(Member member);
  /**
 * 지정된 모임 ID와 회원 ID에 해당하는 회원을 삭제합니다.
 *
 * @param gatheringId 삭제할 회원이 속한 모임의 ID
 * @param memberId 삭제할 회원의 ID
 */
void deleteByGatheringIdAndMemberId(UUID gatheringId, String memberId);
  /**
 * 지정된 모임에서 주어진 멤버 ID 목록에 해당하는 모든 멤버를 삭제합니다.
 *
 * @param gatheringId 멤버를 삭제할 대상 모임의 ID
 * @param memberIds 삭제할 멤버들의 ID 목록
 */
void deleteAllByGatheringIdAndMembers(UUID gatheringId, List<String> memberIds);

  /**
 * 지정된 모임에 속한 모든 멤버 목록을 조회합니다.
 *
 * @param gatheringId 멤버를 조회할 모임의 고유 식별자
 * @return 해당 모임에 소속된 멤버들의 리스트
 */
List<Member> findMembers(UUID gatheringId);

  /**
 * 주어진 멤버 ID에 해당하는 멤버가 존재하는지 확인합니다.
 *
 * @param memberId 존재 여부를 확인할 멤버의 ID
 * @return 멤버가 존재하면 true, 그렇지 않으면 false
 */
boolean existsByMemberId(String memberId);

  /**
 * 지정된 모임과 회원 ID에 해당하는 회원의 회원 유형을 반환합니다.
 *
 * @param gatheringId 회원 유형을 조회할 모임의 고유 식별자
 * @param memberId 회원 유형을 조회할 회원의 고유 식별자
 * @return 해당 회원의 회원 유형
 */
MemberType findMemberType(@Param("gatheringId") UUID gatheringId, @Param("memberId") String memberId);
}

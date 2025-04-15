package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.gathering.gathering.domain.dto.criteria.SearchGatheringCriteria;
import com.sparta.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.common.page.Pagination;

public interface GatheringRepositoryCustom {
  /**
 * 주어진 검색 기준에 따라 모임(Gathering) 목록을 페이징하여 조회합니다.
 *
 * @param criteria 모임 검색 조건을 담은 객체
 * @return 검색 조건에 부합하는 모임의 페이징 결과
 */
Pagination<Gathering> searchGathering(SearchGatheringCriteria criteria);
}

package com.sparta.moim.gathering.gathering.domain.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.gathering.gathering.domain.dto.criteria.SearchGatheringCriteria;
import com.sparta.moim.gathering.gathering.domain.entity.Gathering;

public interface GatheringRepositoryCustom {
  Pagination<Gathering> searchGathering(SearchGatheringCriteria criteria);
}

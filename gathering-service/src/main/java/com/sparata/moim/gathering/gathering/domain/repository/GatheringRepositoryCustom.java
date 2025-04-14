package com.sparata.moim.gathering.gathering.domain.repository;

import com.sparata.moim.gathering.gathering.domain.dto.criteria.SearchGatheringCriteria;
import com.sparata.moim.gathering.gathering.domain.entity.Gathering;
import com.sparta.moim.common.page.Pagination;

public interface GatheringRepositoryCustom {
  Pagination<Gathering> searchGathering(SearchGatheringCriteria criteria);
}

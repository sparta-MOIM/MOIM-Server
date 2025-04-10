package com.sparta.moim.session.session.domain.repository;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.session.session.domain.dto.crtria.SearchSessionCriteria;
import com.sparta.moim.session.session.domain.entity.Session;

public interface SessionCustomRepository {
  Pagination<Session> searchSession(SearchSessionCriteria criteria);
}

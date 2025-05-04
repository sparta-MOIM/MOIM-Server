package com.sparta.moim.session.session.application.template.redis;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;

public interface SessionTemplate {

  void join(SendSessionEventMap event);
  void leave(SendSessionEventMap event);

}

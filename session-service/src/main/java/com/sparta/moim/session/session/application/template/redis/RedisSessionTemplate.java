package com.sparta.moim.session.session.application.template.redis;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;

public interface RedisSessionTemplate {

  void send(SendSessionEventMap event);

}

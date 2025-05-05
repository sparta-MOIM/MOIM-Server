package com.sparta.moim.session.session.infrastructure.manager.lua;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaScriptManager {
  public DefaultRedisScript<Long> load(String scriptName) {
    DefaultRedisScript<Long> script = new DefaultRedisScript<>();
    script.setScriptSource(new ResourceScriptSource(
        new ClassPathResource("lua/" + scriptName + ".lua")
    ));
    script.setResultType(Long.class);
    return script;
  }
}

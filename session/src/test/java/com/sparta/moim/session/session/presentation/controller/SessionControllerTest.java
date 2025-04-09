package com.sparta.moim.session.session.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.service.SessionService;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionApplyRequest;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SessionController.class)
@AutoConfigureMockMvc(addFilters = false)
class SessionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private SessionService sessionService;

  @Test
  @DisplayName("세션 생성 성공")
  void createSession_success() throws Exception {
    // given
    UUID userId = UUID.randomUUID();
    String username = "테스트유저";
    String role = "USER";
    String organizationId = UUID.randomUUID().toString();

    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    CreateSessionRequest request = CreateSessionRequest.builder()
        .organizationId(organizationId)
        .publisher(username)
        .title("스파르타 세션")
        .count(15)
        .status("READY")
        .openTime(LocalDateTime.now())
        .closeTime(LocalDateTime.now().plusHours(10))
        .applyInfo(new CreateSessionApplyRequest(null))
        .build();

    CreateSessionResult response = CreateSessionResult.builder()
        .sessionId(UUID.randomUUID())
        .organizationId(organizationId)
        .publisher(request.publisher())
        .title(request.title())
        .status(SessionStatus.valueOf(request.status()))
        .openTime(request.openTime())
        .closeTime(request.closeTime())
        .count(request.count())
        .applyTime(LocalDateTime.now())
        .build();

    when(sessionService.createSession(any()))
        .thenReturn(response);

    // when & then
    mockMvc.perform(post("/api/v1/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sessionId").exists())
        .andExpect(jsonPath("$.title").value(request.title()))
        .andExpect(jsonPath("$.count").value(request.count()))
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.applyTime").exists())
        .andExpect(jsonPath("$.openTime").exists())
        .andExpect(jsonPath("$.closeTime").exists());
  }
}

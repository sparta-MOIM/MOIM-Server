package com.sparta.moim.session.session.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionListResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionResult;
import com.sparta.moim.session.session.application.service.SessionService;
import com.sparta.moim.session.session.domain.enums.SessionStatus;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionApplyRequest;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateStateRequest;
import java.time.LocalDateTime;
import java.util.List;
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
        .openTime(LocalDateTime.now())
        .closeTime(LocalDateTime.now().plusHours(10))
        .applyInfo(new CreateSessionApplyRequest(null))
        .build();

    CreateSessionResult response = CreateSessionResult.builder()
        .sessionId(UUID.randomUUID())
        .organizationId(organizationId)
        .publisher(request.publisher())
        .title(request.title())
        .status(SessionStatus.OPEN)
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

  @Test
  @DisplayName("세션 단일 조회 성공")
  void getGathering_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();
    GetSessionResult response = GetSessionResult.builder()
        .organizationId("org123")
        .sessionId(sessionId)
        .status(SessionStatus.OPEN)
        .title("test")
        .members(List.of(
            new GetSessionMemberListResult("user1","PUBLISHER"),
            new GetSessionMemberListResult("user2","GENERAL")
        ))
        .openTime(LocalDateTime.now())
        .applyTime(LocalDateTime.now())
        .closeTime(LocalDateTime.now().plusHours(10))
        .confirmTime(LocalDateTime.now().plusHours(10))
        .reason("test")
        .count(10)
        .publisher("test")
        .build();

    when(sessionService.getSession(any()))
        .thenReturn(response);

    // when & then
    mockMvc.perform(get("/api/v1/session/{sessionId}", sessionId)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.organizationId").value("org123"))
        .andExpect(jsonPath("$.sessionId").value(sessionId.toString()))
        .andExpect(jsonPath("$.status").value(SessionStatus.OPEN.toString()))
        .andExpect(jsonPath("$.title").value("test"))
        .andExpect(jsonPath("$.openTime").exists())
        .andExpect(jsonPath("$.closeTime").exists())
        .andExpect(jsonPath("$.count").value(10))
        .andExpect(jsonPath("$.member[0].name").value("user1"))
        .andExpect(jsonPath("$.member[0].type").value("PUBLISHER"))
        .andExpect(jsonPath("$.member[1].name").value("user2"))
        .andExpect(jsonPath("$.member[1].type").value("GENERAL"))
        .andExpect(jsonPath("$.publisher").value("test"))
        .andExpect(jsonPath("$.applyInfo.applyTime").exists())
        .andExpect(jsonPath("$.applyInfo.confirmTime").exists())
        .andExpect(jsonPath("$.applyInfo.reason").value("test"));
  }

  @Test
  @DisplayName("세션 수정 성공")
  void updateSession_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();
    UpdateSessionRequest request = UpdateSessionRequest.builder()
        .count(10)
        .title("test")
        .build();

    // when & then
    mockMvc.perform(put("/api/v1/session/{sessionId}", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("세션 상태변경 성공")
  void updateStateSession_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();
    UpdateStateRequest request = new UpdateStateRequest("CLOSE");

    // when & then
    mockMvc.perform(patch("/api/v1/session/{sessionId}/status", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk());
  }


  @Test
  @DisplayName("세션 삭제 성공")
  void deleteSession_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String username = "테스트유저";
    String role = "USER";
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );
    // when & then
    mockMvc.perform(delete("/api/v1/session/{sessionId}", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("세션 허용 성공")
  void applySession_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();
    // when & then
    mockMvc.perform(patch("/api/v1/session/{sessionId}/apply", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("세션 검색 성공")
  void searchSession_success() throws Exception {
    // given
    UUID userId = UUID.randomUUID();
    String username = "테스트유저";
    String role = "USER";

    // SecurityContext에 인증 정보 설정
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    List<SearchSessionListResult> sessions = List.of(
        SearchSessionListResult.builder()
            .title("title")
            .publisher("publisher")
            .build()
    );

    SearchSessionResult response = SearchSessionResult.builder()
        .sessions(sessions)
        .page(0)
        .content(1)
        .total(1)
        .build();

    when(sessionService.searchSession(any()))
        .thenReturn(response);

    // when & then
    mockMvc.perform(get("/api/v1/session")
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sessions").isArray())
        .andExpect(jsonPath("$.sessions.length()").value(1))
        .andExpect(jsonPath("$.sessions[0].title").value("title"))
        .andExpect(jsonPath("$.sessions[0].publisher").value("publisher"))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.content").value(1))
        .andExpect(jsonPath("$.total").value(1));
  }
}

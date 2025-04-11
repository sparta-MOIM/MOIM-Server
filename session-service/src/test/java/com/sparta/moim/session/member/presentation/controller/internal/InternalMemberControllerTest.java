package com.sparta.moim.session.member.presentation.controller.internal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.member.application.MemberService;
import com.sparta.moim.session.member.application.dto.result.GetMemberListResult;
import com.sparta.moim.session.member.application.dto.result.GetMemberResult;
import com.sparta.moim.session.member.presentation.controller.external.ExternalMemberController;
import com.sparta.moim.session.member.presentation.dto.request.RemoveMemberRequest;
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

@WebMvcTest(InternalMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class InternalMemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private MemberService memberService;

  @Test
  @DisplayName("세션 맴버 조회 성공")
  void getMember_success() throws Exception {
    // given
    UUID userId = UUID.randomUUID();
    String username = "테스트유저";
    String role = "USER";

    UUID sessionId = UUID.randomUUID();

    List<GetMemberListResult> result = List.of(
        new GetMemberListResult("user1", "PUBLISHER"),
        new GetMemberListResult("user2", "GENERAL"),
        new GetMemberListResult("user3", "GENERAL")
    );
    when(memberService.getMember(any())).thenReturn(result);

    // when & then
    mockMvc.perform(get("/internal/v1/session/{sessionId}", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].memberName").value("user1"))
        .andExpect(jsonPath("$[0].type").value("PUBLISHER"))
        .andExpect(jsonPath("$[1].memberName").value("user2"))
        .andExpect(jsonPath("$[1].type").value("GENERAL"))
        .andExpect(jsonPath("$[2].memberName").value("user3"))
        .andExpect(jsonPath("$[2].type").value("GENERAL"))

    ;
  }



}

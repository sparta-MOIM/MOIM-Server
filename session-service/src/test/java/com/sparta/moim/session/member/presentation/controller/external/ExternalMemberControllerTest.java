package com.sparta.moim.session.member.presentation.controller.external;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.member.application.MemberService;
import com.sparta.moim.session.member.presentation.dto.request.RemoveMemberRequest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(ExternalMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExternalMemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private MemberService memberService;

  @Test
  @DisplayName("세션 참가 성공")
  void joinMember_success() throws Exception {
    UUID userId = UUID.randomUUID();
    String username = "testUser";
    String role = "USER";
    UUID sessionId = UUID.randomUUID();

    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    mockMvc.perform(post("/api/v1/session/{sessionId}/join", sessionId)
//            .param("userName", username)
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 참가",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("맴버 외부 API")
                .summary("세션 참가")
                .description("세션에 참가를 하기 위한 엔드포인트입니다.")
                .requestHeaders(
                    headerWithName("X-User-Name").description("로그인 계정 명"),
                    headerWithName("X-User-Role").description("로그인 계정 역할").optional(),
                    headerWithName("X-User-ID").description("로그인 계정 아이디").optional())
                .build()
            )));
  }


  @Test
  @DisplayName("세션 나가기 성공")
  void deleteMember_success() throws Exception {
    // given
    UUID userId = UUID.randomUUID();
    String username = "testUser";
    String role = "USER";

    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    UUID sessionId = UUID.randomUUID();

    // when & then
    mockMvc.perform(delete("/api/v1/session/{sessionId}/leave", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 나가기",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("맴버 외부 API")
                .summary("세션 나가기")
                .description("세션에서 나가기 기능을 위한 엔드포인트입니다.")
                .requestHeaders(
                    headerWithName("X-User-Name").description("로그인 계정 명"),
                    headerWithName("X-User-Role").description("로그인 계정 역할").optional(),
                    headerWithName("X-User-ID").description("로그인 계정 아이디").optional())
                .build()
            )));
  }


  @Test
  @DisplayName("세션 강퇴 성공")
  void removeMember_success() throws Exception {
    // given
    UUID userId = UUID.randomUUID();
    String username = "testUser";
    String role = "USER";

    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    UUID sessionId = UUID.randomUUID();
    UUID member1 = UUID.randomUUID();
    UUID member2 = UUID.randomUUID();
    UUID member3 = UUID.randomUUID();

    RemoveMemberRequest request = new RemoveMemberRequest(List.of(member1, member2, member3));

    // when & then
    mockMvc.perform(delete("/api/v1/session/{sessionId}/remove", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 맴버 강퇴",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("맴버 외부 API")
                .summary("세션 맴버 강퇴")
                .description("세션 맴버 강퇴 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .requestFields(
                    fieldWithPath("members[]").description("강퇴시킬 계정 아이디")
                )
                .build()
            )));
  }


}

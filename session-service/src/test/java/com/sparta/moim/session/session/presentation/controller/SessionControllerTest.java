package com.sparta.moim.session.session.presentation.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.session.application.dto.command.SearchSessionCommand;
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(SessionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
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
        .andExpect(jsonPath("$.closeTime").exists())
        .andDo(document("세션 - 생성",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 생성")
                .description("세션을 생성하기 위한 엔드포인트입니다.")
                .requestFields(
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("publisher").description("발표자"),
                    fieldWithPath("title").description("발표 제목"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("openTime").description("오픈 시간"),
                    fieldWithPath("closeTime").description("마감 시간"),
                    fieldWithPath("applyInfo").description("신청 여부"),
                    fieldWithPath("applyInfo.reason").description("신청 사유")
                )
                .responseFields(
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("sessionId").description("세션 아이디"),
                    fieldWithPath("publisher").description("발표자"),
                    fieldWithPath("title").description("세션 제목"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("status").description("모집 상태"),
                    fieldWithPath("openTime").description("오픈 시간"),
                    fieldWithPath("closeTime").description("마감 시간"),
                    fieldWithPath("applyTime").description("싱청 시간"))
                .build()
            )));
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
        .andExpect(jsonPath("$.publisher").value("test"))
        .andExpect(jsonPath("$.applyInfo.applyTime").exists())
        .andExpect(jsonPath("$.applyInfo.confirmTime").exists())
        .andExpect(jsonPath("$.applyInfo.reason").value("test"))
        .andDo(document("세션 - 단일 조회",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 단일 조회")
                .description("세션 단일조회 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .responseFields(
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("sessionId").description("세션 아이디"),
                    fieldWithPath("publisher").description("발표자"),
                    fieldWithPath("title").description("세션 제목"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("status").description("모집 상태"),
                    fieldWithPath("openTime").description("오픈 시간"),
                    fieldWithPath("closeTime").description("마감 시간"),
                    fieldWithPath("applyInfo").description("신청 정보"),
                    fieldWithPath("applyInfo.applyTime").description("신청 시간"),
                    fieldWithPath("applyInfo.confirmTime").description("승인 시간"),
                    fieldWithPath("applyInfo.reason").description("신청 사유")
                )
                .build()
            )));
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
        .andExpect(status().isOk())
        .andDo(document("세션 - 수정",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 수정")
                .description("세션을 수정하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .requestFields(
                    fieldWithPath("title").description("세션 제목"),
                    fieldWithPath("count").description("모집 인원"))
                .build()
            )));
  }

  @Test
  @DisplayName("세션 상태변경 성공")
  void updateStateSession_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();
    UpdateStateRequest request = new UpdateStateRequest("CLOSE");

    // when & then
    mockMvc.perform(patch("/api/v1/session/{sessionId}/state", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 상태 변경",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 상태 변경")
                .description("세션의 상태를 변경하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .requestFields(
                    fieldWithPath("status").description("세션 상태"))
                .build()
            )));
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
        .andExpect(status().isOk())
        .andDo(document("세션 - 삭제",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 삭제")
                .description("세션삭제를 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .build()
            )));
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
        .andExpect(status().isOk())
        .andDo(document("세션 - 승인",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 승인")
                .description("세션 승인을 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .build()
            )));
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
        .andExpect(jsonPath("$.total").value(1))
        .andDo(document("세션 - 검색",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Session-External")
                .summary("세션 검색")
                .description("세션 검색을 위한 엔드포인트입니다.")
//                .queryParameters(
//                    parameterWithName("reason").description("승인 사유"),
//                    parameterWithName("confirmTime").description("승인 시간"),
//                    parameterWithName("sort").description("정렬 방법"),
//                    parameterWithName("title").description("세션 제목"),
//                    parameterWithName("isDeleted").description("삭제 여부"),
//                    parameterWithName("size").description("가져올 데이터 수"),
//                    parameterWithName("closeTime").description("마감 시간"),
//                    parameterWithName("publisher").description("발표자"),
//                    parameterWithName("page").description("페이지 수"),
//                    parameterWithName("openTime").description("오픈 시간"),
//                    parameterWithName("applyTime").description("신청 시간"),
//                    parameterWithName("status").description("세션 상태")
//                )
                .responseFields(
                    fieldWithPath("sessions").description("세션 리스트"),
                    fieldWithPath("sessions[].title").description("세션 제목"),
                    fieldWithPath("sessions[].publisher").description("발표자"),
                    fieldWithPath("total").description("전체 갯수"),
                    fieldWithPath("page").description("페이지 수"),
                    fieldWithPath("content").description("현재 페이지에서 보여주는 아이템 수"))
                .build()
            )));
  }
}

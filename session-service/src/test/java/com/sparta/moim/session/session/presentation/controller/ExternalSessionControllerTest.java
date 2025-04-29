package com.sparta.moim.session.session.presentation.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
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
import com.sparta.moim.session.session.application.dto.result.CreateSessionResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;
import com.sparta.moim.session.session.application.dto.result.GetSessionResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionListResult;
import com.sparta.moim.session.session.application.dto.result.SearchSessionResult;
import com.sparta.moim.session.session.application.service.SessionService;
import com.sparta.moim.session.session.presentation.controller.external.ExternalSessionController;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionApplyRequest;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.SearchSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateStateRequest;
import com.sparta.moim.session.shared.enums.SessionStatus;
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
@WebMvcTest(ExternalSessionController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExternalSessionControllerTest {
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
    String username = "testUser";
    String role = "USER";
    String organizationId = UUID.randomUUID().toString();

    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    CreateSessionRequest request = CreateSessionRequest.builder()
        .organizationId(organizationId)
        .publisher(userId)
        .title("스파르타 세션")
        .totalCount(15)
        .openTime(LocalDateTime.now())
        .closeTime(LocalDateTime.now().plusHours(10))
        .applyInfo(new CreateSessionApplyRequest("test"))
        .build();

    CreateSessionResult response = CreateSessionResult.builder()
        .sessionId(UUID.randomUUID())
        .organizationId(organizationId)
        .publisher(request.publisher())
        .title(request.title())
        .status(SessionStatus.OPEN)
        .openTime(request.openTime())
        .closeTime(request.closeTime())
        .totalCount(request.totalCount())
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
        .andExpect(jsonPath("$.data.sessionId").exists())
        .andExpect(jsonPath("$.data.title").value(request.title()))
        .andExpect(jsonPath("$.data.totalCount").value(request.totalCount()))
        .andExpect(jsonPath("$.data.status").exists())
        .andExpect(jsonPath("$.data.applyTime").exists())
        .andExpect(jsonPath("$.data.openTime").exists())
        .andExpect(jsonPath("$.data.closeTime").exists())
        .andDo(document("세션 - 생성",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
                .summary("세션 생성")
                .description("세션을 생성하기 위한 엔드포인트입니다.")
                .requestHeaders(
                    headerWithName("X-User-Name").description("로그인 계정 명"),
                    headerWithName("X-User-Role").description("로그인 계정 타입"),
                    headerWithName("X-User-ID").description("로그인 계정 아이디").optional()
                )
                .requestFields(
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("publisher").description("발표자"),
                    fieldWithPath("title").description("발표 제목"),
                    fieldWithPath("totalCount").description("모집 인원"),
                    fieldWithPath("openTime").description("오픈 시간"),
                    fieldWithPath("closeTime").description("마감 시간"),
                    fieldWithPath("applyInfo").description("신청 여부"),
                    fieldWithPath("applyInfo.reason").description("신청 사유")
                )
                .responseFields(
                    fieldWithPath("code").description("코드"),
                    fieldWithPath("message").description("성공메시지"),
                    fieldWithPath("data.organizationId").description("모임 아이디"),
                    fieldWithPath("data.sessionId").description("세션 아이디"),
                    fieldWithPath("data.publisher").description("발표자"),
                    fieldWithPath("data.title").description("세션 제목"),
                    fieldWithPath("data.totalCount").description("모집 인원"),
                    fieldWithPath("data.status").description("모집 상태"),
                    fieldWithPath("data.openTime").description("오픈 시간"),
                    fieldWithPath("data.closeTime").description("마감 시간"),
                    fieldWithPath("data.applyTime").description("싱청 시간"))
                .build()
            )));
  }

  @Test
  @DisplayName("세션 단일 조회 성공")
  void getSession_success() throws Exception {
    // given
    UUID sessionId = UUID.randomUUID();

    UUID publisher = UUID.randomUUID();
    UUID user1 = UUID.randomUUID();
    UUID user2 = UUID.randomUUID();
    GetSessionResult response = GetSessionResult.builder()
        .organizationId("org123")
        .sessionId(sessionId)
        .status(SessionStatus.OPEN)
        .title("test")
        .member(List.of(
            new GetSessionMemberListResult(user1, "PUBLISHER"),
            new GetSessionMemberListResult(user2, "GENERAL")
        ))
        .openTime(LocalDateTime.now())
        .applyTime(LocalDateTime.now())
        .closeTime(LocalDateTime.now().plusHours(10))
        .confirmTime(LocalDateTime.now().plusHours(10))
        .reason("test")
        .totalCount(10)
        .currentCount(2)
        .publisher(publisher)
        .build();

    when(sessionService.getSession(any()))
        .thenReturn(response);

    // when & then
    mockMvc.perform(get("/api/v1/session/{sessionId}", sessionId)
            .header("X-User-Name", "testUser")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.organizationId").value("org123"))
        .andExpect(jsonPath("$.data.sessionId").value(sessionId.toString()))
        .andExpect(jsonPath("$.data.status").value(SessionStatus.OPEN.toString()))
        .andExpect(jsonPath("$.data.title").value("test"))
        .andExpect(jsonPath("$.data.openTime").exists())
        .andExpect(jsonPath("$.data.closeTime").exists())
        .andExpect(jsonPath("$.data.memberCount.total").value(10))
        .andExpect(jsonPath("$.data.memberCount.current").value(2))
        .andExpect(jsonPath("$.data.member").isArray())
        .andExpect(jsonPath("$.data.member[0].id").value(user1.toString()))
        .andExpect(jsonPath("$.data.member[0].type").value("PUBLISHER"))
        .andExpect(jsonPath("$.data.member[1].id").value(user2.toString()))
        .andExpect(jsonPath("$.data.member[1].type").value("GENERAL"))
        .andExpect(jsonPath("$.data.publisher").value(publisher.toString()))
        .andExpect(jsonPath("$.data.applyInfo.applyTime").exists())
        .andExpect(jsonPath("$.data.applyInfo.confirmTime").exists())
        .andExpect(jsonPath("$.data.applyInfo.reason").value("test"))
        .andDo(document("세션 - 단일 조회",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
                .summary("세션 단일 조회")
                .description("세션 단일조회 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                )
                .responseFields(
                    fieldWithPath("code").description("코드"),
                    fieldWithPath("message").description("성공메시지"),
                    fieldWithPath("data.organizationId").description("모임 아이디"),
                    fieldWithPath("data.sessionId").description("세션 아이디"),
                    fieldWithPath("data.publisher").description("발표자"),
                    fieldWithPath("data.title").description("세션 제목"),
                    fieldWithPath("data.memberCount.total").description("모집 인원"),
                    fieldWithPath("data.memberCount.current").description("현재 참여 인원"),
                    fieldWithPath("data.status").description("모집 상태"),
                    fieldWithPath("data.openTime").description("오픈 시간"),
                    fieldWithPath("data.closeTime").description("마감 시간"),
                    fieldWithPath("data.applyInfo").description("신청 정보"),
                    fieldWithPath("data.applyInfo.applyTime").description("신청 시간"),
                    fieldWithPath("data.applyInfo.confirmTime").description("승인 시간"),
                    fieldWithPath("data.applyInfo.reason").description("신청 사유"),
                    fieldWithPath("data.member[].id").description("참가자 ID"),
                    fieldWithPath("data.member[].type").description("참가자 타입")
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
            .header("X-User-Name", "testUser")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 수정",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
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
    mockMvc.perform(patch("/api/v1/session/{sessionId}/status", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "testUser")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 상태 변경",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
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
    String username = "testUser";
    String role = "USER";
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );
    // when & then
    mockMvc.perform(delete("/api/v1/session/{sessionId}", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", "testUser")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 삭제",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
                .summary("세션 삭제")
                .description("세션삭제를 위한 엔드포인트입니다.")
                .requestHeaders(
                    headerWithName("X-User-Name").description("로그인 계정 명"),
                    headerWithName("X-User-Role").description("로그인 계정 타입").optional(),
                    headerWithName("X-User-ID").description("로그인 계정 아이디").optional()
                )
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
            .header("X-User-Name", "testUser")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("세션 - 승인",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
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
    String username = "testUser";
    String role = "USER";
    UUID publisher = UUID.randomUUID();

    SearchSessionRequest request = SearchSessionRequest.builder()
        .title("title")
        .publisher("publisher")
        .status("OPEN")
        .reason("reason")
        .openTime(LocalDateTime.now())
        .closeTime(LocalDateTime.now())
        .applyTime(LocalDateTime.now())
        .isDeleted(false)
        .confirmTime(LocalDateTime.now())
        .page(1)
        .size(10)
        .sort("createdAt")
        .build();

    // SecurityContext에 인증 정보 설정
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    List<SearchSessionListResult> sessions = List.of(
        SearchSessionListResult.builder()
            .title("title")
            .publisher(publisher)
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
            .header("X-User-ID", userId.toString())
            .param("title", request.title())
            .param("publisher", request.publisher())
            .param("status", request.status())
            .param("reason", request.reason())
            .param("openTime", String.valueOf(request.openTime()))
            .param("closeTime", String.valueOf(request.closeTime()))
            .param("applyTime", String.valueOf(request.applyTime()))
            .param("isDeleted", String.valueOf(request.isDeleted()))
            .param("confirmTime", String.valueOf(request.confirmTime()))
            .param("page", String.valueOf(request.page()))
            .param("size", String.valueOf(request.size()))
            .param("sort", request.sort()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.sessions").isArray())
        .andExpect(jsonPath("$.data.sessions.length()").value(1))
        .andExpect(jsonPath("$.data.sessions[0].title").value("title"))
        .andExpect(jsonPath("$.data.sessions[0].publisher").value(publisher.toString()))
        .andExpect(jsonPath("$.data.page").value(0))
        .andExpect(jsonPath("$.data.content").value(1))
        .andExpect(jsonPath("$.data.total").value(1))
        .andDo(document("세션 - 검색",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("세션 외부 API")
                .summary("세션 검색")
                .description("세션 검색을 위한 엔드포인트입니다.")
                .requestHeaders(
                    headerWithName("X-User-Name").description("로그인 계정 명").optional(),
                    headerWithName("X-User-Role").description("로그인 계정 타입"),
                    headerWithName("X-User-ID").description("로그인 계정 아이디").optional()
                )
                .queryParameters(
                    parameterWithName("reason").description("승인 사유").optional(),
                    parameterWithName("confirmTime").description("승인 시간").optional(),
                    parameterWithName("sort").description("정렬 방법").optional(),
                    parameterWithName("title").description("세션 제목").optional(),
                    parameterWithName("isDeleted").description("삭제 여부").optional(),
                    parameterWithName("size").description("가져올 데이터 수").optional(),
                    parameterWithName("closeTime").description("마감 시간").optional(),
                    parameterWithName("publisher").description("발표자").optional(),
                    parameterWithName("page").description("페이지 수").optional(),
                    parameterWithName("openTime").description("오픈 시간").optional(),
                    parameterWithName("applyTime").description("신청 시간").optional(),
                    parameterWithName("status").description("세션 상태").optional()
                )
                .responseFields(
                    fieldWithPath("code").description("코드"),
                    fieldWithPath("message").description("성공메시지"),
                    fieldWithPath("data.sessions").description("세션 리스트"),
                    fieldWithPath("data.sessions[].title").description("세션 제목"),
                    fieldWithPath("data.sessions[].publisher").description("발표자"),
                    fieldWithPath("data.total").description("전체 갯수"),
                    fieldWithPath("data.page").description("페이지 수"),
                    fieldWithPath("data.content").description("현재 페이지에서 보여주는 아이템 수"))
                .build()
            )));
  }
}

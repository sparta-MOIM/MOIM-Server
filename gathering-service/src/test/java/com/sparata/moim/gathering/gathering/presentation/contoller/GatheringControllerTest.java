package com.sparata.moim.gathering.gathering.presentation.contoller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparata.moim.gathering.gathering.application.dto.command.CreateGatheringCommand;
import com.sparata.moim.gathering.gathering.application.dto.result.CreateGatheringResult;
import com.sparata.moim.gathering.gathering.application.dto.result.GetGatheringResult;
import com.sparata.moim.gathering.gathering.application.dto.result.SearchGatheringListResult;
import com.sparata.moim.gathering.gathering.application.dto.result.SearchGatheringResult;
import com.sparata.moim.gathering.gathering.application.service.GatheringService;
import com.sparata.moim.gathering.gathering.presentation.dto.request.UpdateGatheringRequest;
import com.sparta.moim.common.security.CustomUserDetails;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(GatheringController.class)
@AutoConfigureMockMvc(addFilters = false)  // 시큐리티 필터 비활성화
class GatheringControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private GatheringService gatheringService;


  @Test
  @DisplayName("게더링 생성 성공")
  void createGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();
    CreateGatheringCommand request = new CreateGatheringCommand(
        "org123",
        "테스트 모임",
        "주인장",
        10,
        true
    );

    CreateGatheringResult response = new CreateGatheringResult(
        gatheringId,
        "org123",
        "테스트 모임",
        "주인장",
        10,
        true
    );

    when(gatheringService.createGathering(any())).thenReturn(response);

    // when & then
    mockMvc.perform(post("/api/v1/gathering")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.gatheringId").value(gatheringId.toString()))
        .andExpect(jsonPath("$.organizationId").value("org123"))
        .andExpect(jsonPath("$.name").value("테스트 모임"))
        .andExpect(jsonPath("$.owner").value("주인장"))
        .andExpect(jsonPath("$.count").value(10))
        .andExpect(jsonPath("$.status").value(true))
        .andDo(document("소모임 - 생성",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Gathering-External")
                .summary("소모임 생성")
                .description("소모임을 생성하기 위한 엔드포인트입니다.")
                .requestFields(
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("name").description("소모임 명"),
                    fieldWithPath("owner").description("소유자 명"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("status").description("모집 상태"))
                .responseFields(
                    fieldWithPath("gatheringId").description("소모임 아이디"),
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("name").description("소모임 명"),
                    fieldWithPath("owner").description("소유자 명"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("status").description("모집 상태")
                )
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 수정 성공")
  void updateGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();
    UpdateGatheringRequest request = new UpdateGatheringRequest(
        "수정된 모임 이름",
        20,
        false
    );

    // when & then
    mockMvc.perform(put("/api/v1/gathering/{gatheringId}", gatheringId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("소모임 - 수정",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Gathering-External")
                .summary("소모임 수정")
                .description("소모임을 수정하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("gatheringId").description("소모임 아이디")
                )
                .requestFields(
                    fieldWithPath("name").description("소모임 명"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("status").description("모집 상태"))
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 단일 조회 성공")
  void getGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();
    GetGatheringResult response = new GetGatheringResult(
        gatheringId,
        "org123",
        "테스트 모임",
        "테스트유저",
        10,
        true,
        LocalDateTime.now(),
        "아이디",
        LocalDateTime.now(),
        "아이디"
    );

    when(gatheringService.getGathering(any()))
        .thenReturn(response);

    // when & then
    mockMvc.perform(get("/api/v1/gathering/{gatheringId}", gatheringId)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.gatheringId").value(gatheringId.toString()))
        .andExpect(jsonPath("$.organizationId").value("org123"))
        .andExpect(jsonPath("$.name").value("테스트 모임"))
        .andExpect(jsonPath("$.owner").value("테스트유저"))
        .andExpect(jsonPath("$.count").value(10))
        .andExpect(jsonPath("$.status").value(true))
        .andDo(document("소모임 - 단일 조회",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Gathering-External")
                .summary("소모임 단일 조회")
                .description("소모임을 단일 조회하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("gatheringId").description("소모임 아이디")
                )
                .responseFields(
                    fieldWithPath("gatheringId").description("소모임 아이디"),
                    fieldWithPath("organizationId").description("모임 아이디"),
                    fieldWithPath("name").description("소모임 명"),
                    fieldWithPath("owner").description("소유자 명"),
                    fieldWithPath("count").description("모집 인원"),
                    fieldWithPath("status").description("모집 상태"),
                    fieldWithPath("createAt").description("생성시간"),
                    fieldWithPath("createBy").description("생성자"),
                    fieldWithPath("updateAt").description("수정시간"),
                    fieldWithPath("updateBy").description("수정자")
                )
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 삭제 성공")
  void deleteGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String username = "user1";
    String role = "USER";

    // SecurityContext에 인증 정보 설정
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );
    // when & then
    mockMvc.perform(delete("/api/v1/gathering/{gatheringId}", gatheringId)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("소모임 - 삭제",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Gathering-External")
                .summary("소모임 삭제")
                .description("소모임을 삭제하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("gatheringId").description("소모임 아이디")
                )
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 검색 성공")
  void searchGathering_success() throws Exception {
    // given
    UUID userId = UUID.randomUUID();
    String username = "테스트유저";
    String role = "USER";

    // SecurityContext에 인증 정보 설정
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );

    List<SearchGatheringListResult> gatherings = List.of(
        new SearchGatheringListResult(
            UUID.randomUUID(),
            "org123",
            "스파르타 모임",
            10,
            true
        )
    );

    SearchGatheringResult response = SearchGatheringResult.builder()
        .gatherings(gatherings)
        .page(0)
        .content(1)
        .total(1)
        .build();

    when(gatheringService.searchGathering(any()))
        .thenReturn(response);

    // when & then
    mockMvc.perform(get("/api/v1/gathering")
            .param("name", "스파르타")
            .param("isDeleted", "false")
            .param("startTime", LocalDateTime.now().toString())
            .param("endTime", LocalDateTime.now().toString())
            .param("sort", "createdAt")
            .param("status", "true")
            .param("page", "0")
            .param("size", "10")
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.gatherings").isArray())
        .andExpect(jsonPath("$.gatherings.length()").value(1))
        .andExpect(jsonPath("$.gatherings[0].organizationId").value("org123"))
        .andExpect(jsonPath("$.gatherings[0].name").value("스파르타 모임"))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.content").value(1))
        .andExpect(jsonPath("$.total").value(1))
        .andDo(document("소모임 - 기본 조회",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Gathering-External")
                .summary("소모임 검색")
                .description("소모임을 검색하기 위한 엔드포인트입니다.")
                .queryParameters(
                    parameterWithName("name").description("소모임 명").optional(),
                    parameterWithName("status").description("모임 상태").optional(),
                    parameterWithName("isDeleted").description("삭제 여부").optional(),
                    parameterWithName("startTime").description("검색 시작 시간").optional(),
                    parameterWithName("endTime").description("검색 종료 시간").optional(),
                    parameterWithName("page").description("현재 페이지").optional(),
                    parameterWithName("size").description("가져올 데이터 크기").optional(),
                    parameterWithName("sort").description("정렬 기준").optional()
                )
                .build()
            )));
  }
}

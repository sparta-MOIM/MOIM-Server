package com.sparta.moim.gathering.gathering.presentation.contoller.external;

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
import com.sparta.moim.common.exception.GlobalExceptionHandler;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.result.CreateGatheringResult;
import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringMemberListResult;
import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringResult;
import com.sparta.moim.gathering.gathering.application.dto.result.SearchGatheringListResult;
import com.sparta.moim.gathering.gathering.application.dto.result.SearchGatheringResult;
import com.sparta.moim.gathering.gathering.application.exception.ExistsNameGatheringException;
import com.sparta.moim.gathering.gathering.application.service.GatheringService;
import com.sparta.moim.gathering.gathering.presentation.dto.request.UpdateGatheringRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@Import(GlobalExceptionHandler.class)
@WebMvcTest(GatheringController.class)
@AutoConfigureMockMvc(addFilters = false)  // 시큐리티 필터 비활성화
class GatheringControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private GatheringService gatheringService;


  @Nested
  class CreateGatheringTest {
    UUID gatheringId = UUID.randomUUID();
    CreateGatheringCommand request = new CreateGatheringCommand(
        "org123",
        "테스트 모임",
        "주인장",
        10,
        true
    );

    FieldDescriptor[] fieldDescriptors = {fieldWithPath("organizationId").description("모임 아이디"),
        fieldWithPath("name").description("소모임 명"),
        fieldWithPath("owner").description("소유자 명"),
        fieldWithPath("count").description("모집 인원"),
        fieldWithPath("status").description("모집 상태")};

    @Test
    @DisplayName("게더링 생성 성공")
    void createGathering_success() throws Exception {
      // given
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
          .andExpect(jsonPath("$.data.gatheringId").value(gatheringId.toString()))
          .andExpect(jsonPath("$.data.organizationId").value("org123"))
          .andExpect(jsonPath("$.data.name").value("테스트 모임"))
          .andExpect(jsonPath("$.data.owner").value("주인장"))
          .andExpect(jsonPath("$.data.count").value(10))
          .andExpect(jsonPath("$.data.status").value(true))
          .andDo(document("소모임 - 생성",
              preprocessRequest(Preprocessors.prettyPrint()),
              preprocessResponse(Preprocessors.prettyPrint()),
              resource(ResourceSnippetParameters.builder()
                  .tag("Gathering-External")
                  .summary("소모임 생성")
                  .description("소모임을 생성하기 위한 엔드포인트입니다.")
                  .requestFields(fieldDescriptors)
                  .responseFields(
                      fieldWithPath("data.gatheringId").description("소모임 아이디"),
                      fieldWithPath("data.organizationId").description("모임 아이디"),
                      fieldWithPath("data.name").description("소모임 명"),
                      fieldWithPath("data.owner").description("소유자 명"),
                      fieldWithPath("data.count").description("모집 인원"),
                      fieldWithPath("data.status").description("모집 상태"),
                      fieldWithPath("code").description("코드"),
                      fieldWithPath("message").description("성공메시지")
                  )
                  .build()
              )));
    }

    @Test
    @DisplayName("중복된 이름을 넣는 경우")
    void createGathering_fail_exitsName() throws Exception {
      //given
      // when
      when(gatheringService.createGathering(any())).thenThrow(new ExistsNameGatheringException());
      //then
      mockMvc.perform(post("/api/v1/gathering")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request))
              .header("X-User-Name", "테스트유저")
              .header("X-User-Role", "USER")
              .header("X-User-ID", UUID.randomUUID().toString()))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.code").value("G002"))
          .andExpect(jsonPath("$.message").value("A gathering with this title already exists"))
          .andDo(document("소모임 - 이름 중복",
              preprocessRequest(Preprocessors.prettyPrint()),
              preprocessResponse(Preprocessors.prettyPrint()),
              resource(ResourceSnippetParameters.builder()
                  .tag("Gathering-External")
                  .summary("소모임 생성")
                  .description("소모임을 생성시 이름 중복이 발생했을때의 엔드포인트입니다.")
                  .requestFields(fieldDescriptors)
                  .responseFields(
                      fieldWithPath("data").description("데이터"),
                      fieldWithPath("code").description("에러 코드"),
                      fieldWithPath("message").description("메시지")
                  )
                  .build()
              )));

    }

    @Test
    @DisplayName("모집인원이 0명인 경우")
    void createGathering_fail_countZero() throws Exception {
      //given
      CreateGatheringCommand request = new CreateGatheringCommand(
          "org123",
          "테스트 모임",
          "주인장",
          0,
          true
      );
      // when
      //then
      mockMvc.perform(post("/api/v1/gathering")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request))
              .header("X-User-Name", "테스트유저")
              .header("X-User-Role", "USER")
              .header("X-User-ID", UUID.randomUUID().toString()))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.code").value("0"))
          .andExpect(jsonPath("$.message").value("must be greater than 0"))
          .andDo(document("소모임 - 모집인원을 0명을 선택한 경우",
              preprocessRequest(Preprocessors.prettyPrint()),
              preprocessResponse(Preprocessors.prettyPrint()),
              resource(ResourceSnippetParameters.builder()
                  .tag("Gathering-External")
                  .summary("소모임 생성")
                  .description("소모임을 생성시 0명을 모집헀을때 발생했을때의 엔드포인트입니다.")
                  .requestFields(fieldDescriptors)
                  .responseFields(
                      fieldWithPath("data").description("데이터"),
                      fieldWithPath("code").description("에러 코드"),
                      fieldWithPath("message").description("메시지")
                  )
                  .build()
              )));

    }
  }


  @Test
  @DisplayName("게더링 수정 성공")
  void updateGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();
    UpdateGatheringRequest request = new UpdateGatheringRequest(
        "수정된 모임 이름",
        "chnaged",
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
                    fieldWithPath("owner").description("변경되어지는 관리자"),
                    fieldWithPath("status").description("모집 상태"))
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 단일 조회 성공")
  void getGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();

    List<GetGatheringMemberListResult> members = List.of(
        new GetGatheringMemberListResult("abc", "ADMIN"));

    GetGatheringResult response = new GetGatheringResult(
        gatheringId,
        "org123",
        "테스트 모임",
        "테스트유저",
        10,
        true,
        members,
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
        .andExpect(jsonPath("$.data.gatheringId").value(gatheringId.toString()))
        .andExpect(jsonPath("$.data.organizationId").value("org123"))
        .andExpect(jsonPath("$.data.name").value("테스트 모임"))
        .andExpect(jsonPath("$.data.member[0].memberId").value("abc"))
        .andExpect(jsonPath("$.data.member[0].type").value("ADMIN"))
        .andExpect(jsonPath("$.data.owner").value("테스트유저"))
        .andExpect(jsonPath("$.data.count").value(10))
        .andExpect(jsonPath("$.data.status").value(true))
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
                    fieldWithPath("data.gatheringId").description("소모임 아이디"),
                    fieldWithPath("data.organizationId").description("모임 아이디"),
                    fieldWithPath("data.name").description("소모임 명"),
                    fieldWithPath("data.owner").description("소유자 명"),
                    fieldWithPath("data.count").description("모집 인원"),
                    fieldWithPath("data.status").description("모집 상태"),
                    fieldWithPath("data.createAt").description("생성시간"),
                    fieldWithPath("data.createBy").description("생성자"),
                    fieldWithPath("data.updateAt").description("수정시간"),
                    fieldWithPath("data.updateBy").description("수정자"),
                    fieldWithPath("data.member[].memberId").description("멤버 명"),
                    fieldWithPath("data.member[].type").description("멤버 타입"),

                    fieldWithPath("code").description("코드"),
                    fieldWithPath("message").description("성공메시지")
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
        .andExpect(jsonPath("$.data.gatherings").isArray())
        .andExpect(jsonPath("$.data.gatherings.length()").value(1))
        .andExpect(jsonPath("$.data.gatherings[0].organizationId").value("org123"))
        .andExpect(jsonPath("$.data.gatherings[0].name").value("스파르타 모임"))
        .andExpect(jsonPath("$.data.page").value(0))
        .andExpect(jsonPath("$.data.content").value(1))
        .andExpect(jsonPath("$.data.total").value(1))
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

package com.sparta.moim.gathering.gathering.presentation.contoller.external;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.exception.GlobalExceptionHandler;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.presentation.dto.request.RemoveGatheringRequest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("소모임 - 맴버 테스트")
@Import(GlobalExceptionHandler.class)
@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)  // 시큐리티 필터 비활성화
class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private MemberService memberService;


  FieldDescriptor[] successCode = {fieldWithPath("code").description("코드"),
      fieldWithPath("message").description("성공메시지"),
      fieldWithPath("data").description("반환 데이터")};

  @Test
  @DisplayName("게더링 참가 성공")
  void gathering_join_success() throws Exception {
    UUID gatheringId = UUID.randomUUID();

    String username = "testUser";
    UUID userId = UUID.randomUUID();
    String role = "USER";

    setupSecurityContext(username, role, userId);

    // when & then
    mockMvc.perform(post("/api/v1/gathering/member/{gatheringId}", gatheringId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("소모임 - 참가",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Member-External")
                .summary("소모임 참가")
                .description("소모임에 참가하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("gatheringId").description("소모임 아이디")
                )
                .responseFields(
                    successCode
                )
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 나가기 성공")
  void gathering_leave_success() throws Exception {
    UUID gatheringId = UUID.randomUUID();

    String username = "testUser";
    UUID userId = UUID.randomUUID();
    String role = "USER";

    setupSecurityContext(username, role, userId);

    // when & then
    mockMvc.perform(delete("/api/v1/gathering/member/{gatheringId}/leave", gatheringId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("소모임 - 나가기",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Member-External")
                .summary("소모임 나가기")
                .description("소모임에 나가기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("gatheringId").description("소모임 아이디")
                )
                .responseFields(
                    successCode
                )
                .build()
            )));
  }

  @Test
  @DisplayName("게더링 강퇴 성공")
  void gathering_remove_success() throws Exception {
    //given
    UUID gatheringId = UUID.randomUUID();

    String username = "testUser";
    UUID userId = UUID.randomUUID();
    String role = "USER";

    RemoveGatheringRequest request = new RemoveGatheringRequest(List.of(UUID.randomUUID()));

    setupSecurityContext(username, role, userId);

    // when & then
    mockMvc.perform(delete("/api/v1/gathering/member/{gatheringId}/remove", gatheringId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-User-Name", "테스트유저")
            .header("X-User-Role", "USER")
            .header("X-User-ID", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andDo(document("소모임 - 강퇴",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("Member-External")
                .summary("소모임 강퇴")
                .description("소모임에서 강퇴를 하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("gatheringId").description("소모임 아이디")
                )
                .responseFields(
                    successCode
                )
                .build()
            )));
  }


  private void setupSecurityContext(String username, String role, UUID userId) {
    CustomUserDetails customUserDetails = new CustomUserDetails(username, role, userId);
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
    );
  }

}

package com.sparta.moim.session.member.presentation.controller.internal;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.session.member.application.MemberService;
import com.sparta.moim.session.member.application.dto.result.GetMemberListResult;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
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
    String username = "testUser";
    String role = "USER";

    UUID sessionId = UUID.randomUUID();

    UUID member1 = UUID.randomUUID();
    UUID member2 = UUID.randomUUID();
    UUID member3 = UUID.randomUUID();
    List<GetMemberListResult> result = List.of(
        new GetMemberListResult(member1, "PUBLISHER"),
        new GetMemberListResult(member2, "GENERAL"),
        new GetMemberListResult(member3, "GENERAL")
    );
    when(memberService.getMember(any())).thenReturn(result);

    // when & then
    mockMvc.perform(get("/internal/v1/session/{sessionId}", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .header("X-User-ID", userId.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(member1.toString()))
        .andExpect(jsonPath("$[0].type").value("PUBLISHER"))
        .andExpect(jsonPath("$[1].id").value(member2.toString()))
        .andExpect(jsonPath("$[1].type").value("GENERAL"))
        .andExpect(jsonPath("$[2].id").value(member3.toString()))
        .andExpect(jsonPath("$[2].type").value("GENERAL"))
        .andDo(document("세션 - 맴버 조회",
            preprocessRequest(Preprocessors.prettyPrint()),
            preprocessResponse(Preprocessors.prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("맴버 내부 API")
                .summary("세션 맴버 조회")
                .description("세션에서 참가한 맴버들을 조회 하기 위한 엔드포인트입니다.")
                .pathParameters(
                    parameterWithName("sessionId").description("세션 아이디")
                ).responseFields(
                    fieldWithPath("[].id").description("참여한 참가자 명"),
                    fieldWithPath("[].type").description("참가한 참가자 타입"))
                .build()
            )));

  }


}

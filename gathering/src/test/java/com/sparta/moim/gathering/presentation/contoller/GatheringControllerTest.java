package com.sparta.moim.gathering.presentation.contoller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.gathering.application.dto.command.CreateGatheringCommand;
import com.sparta.moim.gathering.application.dto.query.CreateGatheringQuery;
import com.sparta.moim.gathering.application.dto.query.GetGatheringQuery;
import com.sparta.moim.gathering.application.service.GatheringService;
import com.sparta.moim.gathering.presentation.dto.request.UpdateGatheringRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GatheringController.class)
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
        10,
        true
    );

    CreateGatheringQuery response = new CreateGatheringQuery(
        gatheringId,
        "org123",
        "테스트 모임",
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
        .andExpect(jsonPath("$.count").value(10))
        .andExpect(jsonPath("$.Status").value(true));
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
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("게더링 단일 조회 성공")
  void getGathering_success() throws Exception {
    // given
    UUID gatheringId = UUID.randomUUID();
    GetGatheringQuery response = new GetGatheringQuery(
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
        .andExpect(jsonPath("$.status").value(true));
  }
}

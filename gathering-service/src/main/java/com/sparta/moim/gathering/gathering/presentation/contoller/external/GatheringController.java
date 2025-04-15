package com.sparta.moim.gathering.gathering.presentation.contoller.external;

import com.sparta.moim.gathering.gathering.application.dto.command.DeleteGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.GatheringService;
import com.sparta.moim.gathering.gathering.presentation.dto.request.CreateGatheringRequest;
import com.sparta.moim.gathering.gathering.presentation.dto.request.SearchGatheringRequest;
import com.sparta.moim.gathering.gathering.presentation.dto.request.UpdateGatheringRequest;
import com.sparta.moim.gathering.gathering.presentation.dto.response.CreateGatheringResponse;
import com.sparta.moim.gathering.gathering.presentation.dto.response.GetGatheringResponse;
import com.sparta.moim.gathering.gathering.presentation.dto.response.SearchGatheringResponse;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gathering")
@RequiredArgsConstructor
public class GatheringController {
  private final GatheringService gatheringService;

  /**
   * 새로운 모임을 생성합니다.
   *
   * 요청 본문으로 전달된 정보를 바탕으로 모임을 생성하고, 생성된 모임의 상세 정보를 반환합니다.
   *
   * @param request 생성할 모임의 정보를 담은 요청 객체
   * @return 생성된 모임의 정보를 포함한 성공 응답
   */
  @PostMapping()
  public ResponseEntity<ApiResponseData<CreateGatheringResponse>> createGathering(@RequestBody @Valid CreateGatheringRequest request) {
    return ResponseEntity.ok(ApiResponseData.success(
        CreateGatheringResponse.create(gatheringService.createGathering(request.toCommand()))));
  }

  /**
   * 주어진 검색 조건과 인증된 사용자 정보를 기반으로 모임 목록을 조회합니다.
   *
   * @param request 모임 검색 조건이 담긴 요청 객체
   * @param details 인증된 사용자 정보
   * @return 검색 결과가 포함된 ApiResponseData 래퍼 객체
   */
  @GetMapping
  public ResponseEntity<ApiResponseData<SearchGatheringResponse>> searchGathering(@ModelAttribute SearchGatheringRequest request, @AuthenticationPrincipal CustomUserDetails details) {
    return ResponseEntity.ok(ApiResponseData.success(
        SearchGatheringResponse.search(gatheringService.searchGathering(request.toCommand(details)))));
  }

  /**
   * 지정된 모임 ID에 해당하는 모임 정보를 조회합니다.
   *
   * @param gatheringId 조회할 모임의 UUID
   * @return 조회된 모임 정보를 포함한 성공 응답
   */
  @GetMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<GetGatheringResponse>> getGathering(@PathVariable UUID gatheringId) {
    return ResponseEntity.ok(
        ApiResponseData.success(GetGatheringResponse.get(gatheringService.getGathering(gatheringId))));
  }


  /**
   * 주어진 모임 ID에 해당하는 모임 정보를 수정합니다.
   *
   * @param gatheringId 수정할 모임의 UUID
   * @param request 수정할 모임 정보가 담긴 요청 객체
   * @return 성공 여부를 나타내는 응답
   */
  @PutMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<Void>> updateGathering(@PathVariable UUID gatheringId, @RequestBody @Valid UpdateGatheringRequest request) {
    gatheringService.updateGathering(request.toCommand(gatheringId));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  /**
   * 지정된 모임(gathering)을 삭제합니다.
   *
   * @param gatheringId 삭제할 모임의 UUID
   * @param customUserDetails 인증된 사용자 정보
   * @return 삭제 성공 시 성공 응답을 반환합니다.
   */
  @DeleteMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<Void>> deleteGathering(@PathVariable UUID gatheringId,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    gatheringService.deleteGathering(new DeleteGatheringCommand(gatheringId, customUserDetails));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }
}

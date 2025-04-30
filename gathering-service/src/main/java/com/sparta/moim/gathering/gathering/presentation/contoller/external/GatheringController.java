package com.sparta.moim.gathering.gathering.presentation.contoller.external;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.gathering.gathering.application.dto.command.DeleteGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.struct.GatheringService;
import com.sparta.moim.gathering.gathering.presentation.dto.request.CreateGatheringRequest;
import com.sparta.moim.gathering.gathering.presentation.dto.request.SearchGatheringRequest;
import com.sparta.moim.gathering.gathering.presentation.dto.request.UpdateGatheringRequest;
import com.sparta.moim.gathering.gathering.presentation.dto.response.CreateGatheringResponse;
import com.sparta.moim.gathering.gathering.presentation.dto.response.GetGatheringResponse;
import com.sparta.moim.gathering.gathering.presentation.dto.response.SearchGatheringResponse;
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

  @PostMapping()
  public ResponseEntity<ApiResponseData<CreateGatheringResponse>> createGathering(@RequestBody @Valid CreateGatheringRequest request) {
    return ResponseEntity.ok(ApiResponseData.success(
        CreateGatheringResponse.create(gatheringService.createGathering(request.toCommand()))));
  }

  @GetMapping
  public ResponseEntity<ApiResponseData<SearchGatheringResponse>> searchGathering(@ModelAttribute SearchGatheringRequest request, @AuthenticationPrincipal CustomUserDetails details) {
    return ResponseEntity.ok(ApiResponseData.success(
        SearchGatheringResponse.search(gatheringService.searchGathering(request.toCommand(details)))));
  }

  @GetMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<GetGatheringResponse>> getGathering(@PathVariable UUID gatheringId) {
    return ResponseEntity.ok(
        ApiResponseData.success(GetGatheringResponse.get(gatheringService.getGathering(gatheringId))));
  }


  @PutMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<Void>> updateGathering(@PathVariable UUID gatheringId, @RequestBody @Valid UpdateGatheringRequest request) {
    gatheringService.updateGathering(request.toCommand(gatheringId));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  @DeleteMapping("/{gatheringId}")
  public ResponseEntity<ApiResponseData<Void>> deleteGathering(@PathVariable UUID gatheringId,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    gatheringService.deleteGathering(new DeleteGatheringCommand(gatheringId, customUserDetails));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }
}

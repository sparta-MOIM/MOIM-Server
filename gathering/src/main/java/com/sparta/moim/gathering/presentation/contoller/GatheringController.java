package com.sparta.moim.gathering.presentation.contoller;

import com.sparta.moim.gathering.application.service.GatheringService;
import com.sparta.moim.gathering.presentation.dto.request.CreateGatheringRequest;
import com.sparta.moim.gathering.presentation.dto.request.UpdateGatheringRequest;
import com.sparta.moim.gathering.presentation.dto.response.CreateGatheringResponse;
import com.sparta.moim.gathering.presentation.dto.response.GetGatheringResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  public CreateGatheringResponse createGathering(@RequestBody @Valid CreateGatheringRequest request) {
    return CreateGatheringResponse.create(gatheringService.createGathering(request.toCommand()));
  }

  @GetMapping
  public void searchGathering() {

  }

  @GetMapping("/{gatheringId}")
  public GetGatheringResponse getGathering(@PathVariable UUID gatheringId) {
    return GetGatheringResponse.get(gatheringService.getGathering(gatheringId));
  }


  @PutMapping("/{gatheringId}")
  public void updateGathering(@PathVariable UUID gatheringId, @RequestBody @Valid UpdateGatheringRequest request) {
    gatheringService.updateGathering(request.toCommand(gatheringId));
  }

  @DeleteMapping("/{gatheringId}")
  public void deleteGathering(@PathVariable UUID gatheringId) {
    gatheringService.deleteGathering(gatheringId);
  }
}

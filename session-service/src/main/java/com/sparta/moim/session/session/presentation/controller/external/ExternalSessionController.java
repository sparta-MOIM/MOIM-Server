package com.sparta.moim.session.session.presentation.controller.external;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.common.security.CustomUserDetails;
import com.sparta.moim.session.session.application.dto.DeleteSessionCommand;
import com.sparta.moim.session.session.application.service.SessionService;
import com.sparta.moim.session.session.presentation.dto.request.CreateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.SearchSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateSessionRequest;
import com.sparta.moim.session.session.presentation.dto.request.UpdateStateRequest;
import com.sparta.moim.session.session.presentation.dto.response.CreateSessionResponse;
import com.sparta.moim.session.session.presentation.dto.response.GetSessionResponse;
import com.sparta.moim.session.session.presentation.dto.response.SearchSessionResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class ExternalSessionController {
  private final SessionService sessionService;

  @PostMapping
  public ResponseEntity<ApiResponseData<CreateSessionResponse>> createSession(@RequestBody @Valid CreateSessionRequest request,
                                                                             @AuthenticationPrincipal CustomUserDetails details) {
    return ResponseEntity.ok(ApiResponseData.success(CreateSessionResponse.create(
        sessionService.createSession(request.toCommand(details.getUsername(), details.getRole())))));
  }


  @GetMapping("/{sessionId}")
  public ResponseEntity<ApiResponseData<GetSessionResponse>> getSession(@PathVariable UUID sessionId) {
    return ResponseEntity.ok(ApiResponseData.success(GetSessionResponse.get(sessionService.getSession(sessionId))));
  }

  @GetMapping
  public ResponseEntity<ApiResponseData<SearchSessionResponse>> searchSession(@ModelAttribute SearchSessionRequest request, @AuthenticationPrincipal CustomUserDetails details) {
    return ResponseEntity.ok(ApiResponseData.success(
        SearchSessionResponse.search(sessionService.searchSession(request.toCommand(details.getRole())))));
  }


  @PutMapping("/{sessionId}")
  public ResponseEntity<ApiResponseData<Void>> updateSession(@PathVariable UUID sessionId, @RequestBody @Valid UpdateSessionRequest request) {
    sessionService.updateSession(request.toCommand(sessionId));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  @PatchMapping("/{sessionId}/status")
  public ResponseEntity<ApiResponseData<Void>> updateStateSession(@PathVariable UUID sessionId, @RequestBody @Valid UpdateStateRequest request) {
    sessionService.statusUpdateSession(request.toCommand(sessionId));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  @DeleteMapping("/{sessionId}")
  public ResponseEntity<ApiResponseData<Void>> deleteSession(@PathVariable UUID sessionId, @AuthenticationPrincipal CustomUserDetails details) {
    sessionService.deleteSession(new DeleteSessionCommand(sessionId, details.getUsername()));
    return ResponseEntity.ok(ApiResponseData.success(null));
  }

  @PatchMapping("/{sessionId}/apply")
  public ApiResponseData<Void> applySession(@PathVariable UUID sessionId) {
    sessionService.applySession(sessionId);
    return ApiResponseData.success(null);
  }


}

package com.sparta.moim.common.security.handler;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.response.ApiResponseData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@RequiredArgsConstructor
public class ForbiddenHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  private static final String FORBIDDEN_ERROR_MESSAGE = "접근 권한이 없습니다.";

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ApiResponseData<String> apiResponse = ApiResponseData.failure(
        FORBIDDEN.value(), FORBIDDEN_ERROR_MESSAGE);

    response.setStatus(apiResponse.getCode());
    response.setContentType(APPLICATION_JSON_VALUE);

    String jsonResponse = objectMapper.writeValueAsString(apiResponse);
    response.getWriter().write(jsonResponse);
  }
}

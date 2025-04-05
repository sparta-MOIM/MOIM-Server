package com.sparta.moim.common.security.handler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.response.ApiResponseData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
public class UnauthorizedHandler implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  private static final String UNAUTHORIZED_ERROR_MESSAGE = "인증에 실패하였습니다.";

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    ApiResponseData<String> apiResponse = ApiResponseData.failure(
        UNAUTHORIZED.value(), UNAUTHORIZED_ERROR_MESSAGE);

    response.setStatus(apiResponse.getCode());
    response.setContentType(APPLICATION_JSON_VALUE);

    String jsonResponse = objectMapper.writeValueAsString(apiResponse);
    response.getWriter().write(jsonResponse);
  }
}

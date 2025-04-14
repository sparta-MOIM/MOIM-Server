package com.sparta.moim.chat.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // WebSocket을 활성화하고 메시지 브로커 사용가능
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//  private final StompHandler stompHandler;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
    // /room/{chatNo}로 주제 구독 가능
    registry.enableSimpleBroker("/room");

    //클라이언트에서 보낸 메세지를 받을 prefix
    // /send/message로 메세지 전송 컨트롤러 라우팅 기능
    registry.setApplicationDestinationPrefixes("/send");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat")   //SockJS 연결 주소, STOMP 엔드포인트 설정
        .setAllowedOriginPatterns("*");// 모든 Origin 허용 -> 배포시에는 보안을 위해 Origin을 정확히 지정
        //.withSockJS(); //버전 낮은 브라우저에서도 적용 가능
    // 주소 : ws://localhost:8087/chat
  }

  // 클라이언트 인바운드 채널을 구성하는 메서드
  //@Override
//  public void configureClientInboundChannel(ChannelRegistration registration) {
//    // stompHandler를 인터셉터로 등록하여 STOMP 메시지 핸들링을 수행
//    registration.interceptors(stompHandler);
//  }

  // STOMP에서 64KB 이상의 데이터 전송을 못하는 문제 해결
  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
    registry.setMessageSizeLimit(160 * 64 * 1024);
    registry.setSendTimeLimit(100 * 10000);
    registry.setSendBufferSizeLimit(3 * 512 * 1024);
  }

}

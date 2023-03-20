package com.github.nyamnyam.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${api.kakao.host}")
    private String kakaoHost;

    @Value(value = "${api.naver.host}")
    private String naverHost;

    @Value(value = "${api.keys.kakao.auth}")
    private String kakaoAuth;

    @Value(value = "${api.keys.naver.client-id}")
    private String naverClientId;

    @Value(value = "${api.keys.naver.client-secret}")
    private String naverClientSecret;


    @Bean
    public WebClient getKakaoWebClient() throws Exception {
        WebClient client = WebClient.builder()
                .baseUrl(kakaoHost)
                .defaultHeader("Authorization",kakaoAuth)
                .build();
        return client;
    }

    @Bean
    public WebClient getNaverWebClient() {
        WebClient client = WebClient.builder()
                .baseUrl(naverHost)
                .defaultHeader("X-Naver-Client-Id",naverClientId)
                .defaultHeader("X-Naver-Client-Secret", naverClientSecret)
                .build();
        return client;
    }

}

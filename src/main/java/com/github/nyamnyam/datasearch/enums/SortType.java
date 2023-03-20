package com.github.nyamnyam.datasearch.enums;

public enum SortType {
    ACCURACY("accuracy","sim"),
    RECENCY("recency","date")
    ;

    SortType(String kakaoApi, String naverApi) {
        this.kakaoApi = kakaoApi;
        this.naverApi = naverApi;
    }

    private String kakaoApi;
    private String naverApi;


    public String getSortType(ApiPlatform apiPlatform) {
        switch (apiPlatform) {
            case KAKAO_API -> {
                return this.kakaoApi;
            }
            case NAVER_API -> {
                return this.naverApi;
            }
            default -> {
                return null;
            }
        }
    }
}
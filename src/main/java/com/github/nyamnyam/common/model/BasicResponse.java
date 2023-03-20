package com.github.nyamnyam.common.model;

import com.github.nyamnyam.common.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class BasicResponse {
    private final String code;
    private final String message;
    private final Object data;

    public static BasicResponse toResponse(ResponseCode responseCode, Object data) {
        return BasicResponse.builder()
                            .code(responseCode.getCode())
                            .message(responseCode.getMessage())
                            .data(Objects.requireNonNullElse(data, Map.of()))
                .build();
    }

}

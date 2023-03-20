package com.github.nyamnyam.common.enums;


public enum ResponseCode {

    SUCCESS("200", "success"),

    ERROR_1000("E1000", "Validation Error"),

    ERROR_9001("E9001", "IllegalException"),
    ERROR_9999("E9999","Server Error"),

    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}

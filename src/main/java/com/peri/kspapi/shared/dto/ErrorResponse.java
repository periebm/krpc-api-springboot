package com.peri.kspapi.shared.dto;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ErrorResponse {
    private String error;
    private int status;
    private String timestamp;

    public ErrorResponse(String error, int status) {
        this.error = error;
        this.status = status;
        this.timestamp = ZonedDateTime.now().toString();
    }


}
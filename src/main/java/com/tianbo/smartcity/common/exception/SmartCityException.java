package com.tianbo.smartcity.common.exception;

import lombok.Data;


@Data
public class SmartCityException extends RuntimeException {

    private String msg;

    public SmartCityException(String msg) {
        super(msg);
        this.msg = msg;
    }
}

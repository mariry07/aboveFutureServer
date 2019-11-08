package com.tianbo.smartcity.modules.your.vo;

import lombok.Data;

@Data
public class DeviceFaultVo {
    private String deviceAll;
    private String untreatedAll;
    private String handleAll;
    private String monthAll;
    private String ctime;
    private int count;
}

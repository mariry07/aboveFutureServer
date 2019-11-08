package com.tianbo.smartcity.common.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}

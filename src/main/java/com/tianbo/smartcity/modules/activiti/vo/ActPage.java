package com.tianbo.smartcity.modules.activiti.vo;

import lombok.Data;

import java.util.List;


@Data
public class ActPage<T> {

    List<T> content;

    Long totalElements;
}

package com.tianbo.smartcity.config.activiti;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Exrick
 */
@Data
@ConfigurationProperties(prefix = "spring.activiti.smart-city")
public class ActivitiExtendProperties {

    /**
     * 流程图字体配置
     */
    private String activityFontName = "宋体";

    /**
     * 流程图字体配置
     */
    private String labelFontName = "宋体";
}

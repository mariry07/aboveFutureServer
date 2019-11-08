package com.tianbo.smartcity.common.enums;

/**
 * 第三方平台常数
 *
 * @author yxk
 */
public enum ThirdConstant {

    /**
     * token参数头
     */
    HEADER("accessToken"),

    /**
     * 请求类型
     */
    REQUEST_TYPE("requestType"),

    /**
     * 第三方标识
     */
    ACCESS_KEY("accessKey"),

    /**
     * 秘钥
     */
    SECRET_KEY("secretKey"),

    /**
     * 请求时间戳
     */
    TIMESTAMP("timestamp"),

    /**
     * 随机数
     */
    NONCE("nonce"),

    /**
     * 签名
     */
    SIGN("sign"),

    /**
     * 缓存签名前缀
     */
    REDIS_NONCE("REQUEST_NONCE:"),

    /**
     * 缓存第三方标识
     */
    THIRD_ACCESS_KEY("THIRD_ACCESS_KEY:"),

    /**
     * 标识前缀
     */
    ACCESS_KEY_PRE("THIRD_ACCESS_KEY_PRE:"),

    /**
     * 请求类型 第三方接口平台
     */
    THIRD_TYPE("2");

    private String name;

    ThirdConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

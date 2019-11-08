package com.tianbo.smartcity.common.enums;

/**
 * 巡更/巡查计划类型枚举类
 * 对应的定时任务规则
 * @author WangCH
 * @create 2019-08-12 09:22
 */
public enum PatrolPlanType {

    //测试20秒执行一次
    //DAY(0,"日检","*/20 * * * * ?",1),
    // 日检
    DAY(0,"日检","0 0 0 * * ?",1),
    // 周检
    WEEK(1,"周检","0 0 0 ? * MON",7),
    // 月检
    MONTH(2 ,"月检","0 0 0 1 * ?",30),
    // 季检
    QUARTER(3,"季检","0 0 0 1 1/3 ?",92),
    // 半年检
    HALFYEAR(4,"半年检","0 0 0 1 1/6 ?",182),
    // 年检
    YEAR(5,"年检","0 0 0 1 1 ?",365),
    // 临时
    TEMP(6,"临时","* * * * *",5);

    /**
     * 数据库及数据字典编码
     */
    private final Integer code;
    /**
     * 计划类型名称
     */
    private final String name;
    /**
     * 定时任务规则
     */
    private final String rule;

    /**
     * 对应时间
     */
    private final Integer days;

    PatrolPlanType(Integer code, String name, String rule,Integer days) {
        this.code = code;
        this.name = name;
        this.rule = rule;
        this.days = days;
    }
    public Integer getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public String getRule() {
        return rule;
    }
    public Integer getDays() {
        return days;
    }

    /**
     *  根据编码获取类型名称
     */
    public static String getNameByCode(Integer code){
        for (PatrolPlanType type : PatrolPlanType.values()) {
            if (type.getCode().equals(code)) {
                return type.name;
            }
        }
        return null;
    }

    /**
     *  根据编码获取定时任务规则
     */
    public static String getRuleByCode(Integer code){
        for (PatrolPlanType type : PatrolPlanType.values()) {
            if (type.getCode().equals(code)) {
                return type.rule;
            }
        }
        return null;
    }

    /**
     *  根据编码获取定时任务规则
     */
    public static Integer getDaysByCode(Integer code){
        for (PatrolPlanType type : PatrolPlanType.values()) {
            if (type.getCode().equals(code)) {
                return type.days;
            }
        }
        return null;
    }
}

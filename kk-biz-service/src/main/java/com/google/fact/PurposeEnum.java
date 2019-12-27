package com.google.fact;

/**
 * 策略枚举类
 */
public enum PurposeEnum {

    TYPE_1(1, "中国银行", "com.google.fact.impl.ZGFactApplyStrategy"),

    TYPE_2(2, "光大银行", "com.google.fact.impl.GdFactApplyStrategy"),

    TYPE_3(3, "交通银行", "com.google.fact.impl.JTFactApplyStrategy"),
    ;

    private Integer code;
    private String mark;
    private String className;

    private PurposeEnum(Integer code, String mark, String className) {
        this.code = code;
        this.mark = mark;
        this.className = className;
    }

    public Integer getCode() {
        return code;
    }

    public String getMark() {
        return mark;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public static PurposeEnum getEnumObjByCode(Integer code) {
        for (PurposeEnum be : values()) {
            if (be.getCode().equals(code)) {
                return be;
            }
        }
        return null;
    }
}

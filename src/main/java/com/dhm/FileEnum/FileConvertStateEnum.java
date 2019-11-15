package com.dhm.FileEnum;

public enum FileConvertStateEnum {
    /**
     * 未转化
     */
    UNCONVERT("2", "未转化"),
    /**
     * 转化中
     */
    CONVERTING("1", "转化中"),
    /**
     * 已完成
     */
    FINSH("0", "已完成");

    private String code;
    private String state;

    FileConvertStateEnum(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}



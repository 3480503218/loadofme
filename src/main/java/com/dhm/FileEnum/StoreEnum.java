package com.dhm.FileEnum;

/**
 * 存储协议的枚举类
 */
public enum StoreEnum {
    /**
     * 硬盘
     */
    DISK(0),
    /**
     * fastDFS
     */
    FDFS(1),
    /**
     * OSS
     */
    OSS(2),
    /**
     * anyshare
     */
    ANYSHARE(3);

    private int value;

    StoreEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StoreEnum valueOf(int value) {
        switch (value) {
            case 0:
                return DISK;
            case 1:
                return FDFS;
            case 2:
                return OSS;
            case 3:
                return ANYSHARE;
            default:
                return null;
        }
    }

}

package com.dhm.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Address implements Serializable {
    /**
     * 协议
     */
    private String proto;
    /**
     * server ip
     */
    private String ipAddr;
    /**
     * server port
     */
    private String port;
    /**
     * 请求地址
     */
    private String requestPath;
    /**
     * token标识
     */
    private String token;
    /**
     * 文件类型集合
     */
    private Set<String> typeSet;
    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 请求路径
     */
    private String storePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件原名
     */
    private String originName;
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 是否源文件下载
     */
    private boolean source;

    public Address() {
        super();
    }

    public Address(String proto, String ipAddr, String port, String requestPath, String token) {
        this.proto = proto;
        this.ipAddr = ipAddr;
        this.port = port;
        this.requestPath = requestPath;
        this.token = token;
    }

    public Address(String proto, String ipAddr, String port, String requestPath, String token, boolean source) {
        this.proto = proto;
        this.ipAddr = ipAddr;
        this.port = port;
        this.requestPath = requestPath;
        this.token = token;
        this.source = source;
    }
}

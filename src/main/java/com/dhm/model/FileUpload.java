package com.dhm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload implements Serializable {
    /**
     * server ip
     */
    private String ipAddr;
    /**
     * server port
     */
    private String port;
    /**
     * 文件存储地址
     */
    private String storePath;
    /**
     * 存储类型(保护存储协议和存储路径信息，唯一)
     */
    private String type;
    /**
     * 文件存储名称
     */
    private String fileName;
    /**
     * 成功失败信息
     */
    private String message;
    /**
     * 文件大小
     */
    private int fileSize;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件名称
     */
    private String requestPath;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 本地存储路径
     */
    private String localPath;
}

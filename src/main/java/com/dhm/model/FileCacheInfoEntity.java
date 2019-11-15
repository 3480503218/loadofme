package com.dhm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileCacheInfoEntity {
    /**
     * id
     */
    /*private String id;*/
    /**
     * tenant_id
     */
    /*private String tenantId;*/
    /**
     * app_id
     */
    /*private String appId;*/
    /**
     * redis key
     */
    private String redisKey;
    /**
     * 存储服务ip
     */
    private String ip;
    /**
     * 存储服务端口
     */
    private String port;
    /**
     * 相对存储路径
     */
    private String storePath;
    /**
     * 存储类型
     */
    private String type;
    /**
     * 文件存储服务转义后的存储文件名称
     */
    private String fileName;
    /**
     * 转换为pdf的状态 0:已完成  1:转换中  2:未转换
     */
    private String pdfState;
    /**
     * 转换为image的状态 0:已完成  1:转换中  2:未转换
     */
    private String imageState;
    /**
     * pdf文件在文件服务器上的绝对路径
     */
    private String pdfStorePath;
    /**
     * img文件在文件服务器上的绝对路径
     */
    private String imageStorePath;
    /**
     * 添加水印的状态
     */
    private String watermarkState;
    /**
     * 水印文件的存储路径
     */
    private String watermarkPath;
    /**
     * 文件转换为图片后的数量
     */
    private String imageNumber;
    /**
     * 图片宽度
     */
    private String imageWidth;
    /**
     * 图片高度
     */
    private String imageHeight;
    /**
     * url访问当前资源的绝对路径
     */
    private String requestPath;
}
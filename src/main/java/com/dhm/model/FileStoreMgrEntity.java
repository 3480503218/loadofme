package com.dhm.model;

import lombok.Data;

import java.util.Map;
@Data
public class FileStoreMgrEntity {
    /**
     * id
     */
    private String id;
    /**
     * tenant_id
     */
    private String tenantId;
    /**
     * app_id
     */
    private String appId;
    /**
     * 业务类型
     */
    private int bizType;
    /**
     * 业务实体ID
     */
    private String bizId;
    /**
     * 业务数据ID
     */
    private String bizdataId;
    /**
     * 文件、文件夹名称
     */
    private String name;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 所在文件夹
     */
    private String folderId;
    /**
     * 子文件id
     */
    private String subjectId;
    /**
     * 存储载体
     */
    private String storageType;
    /**
     * 存储路径
     */
    private String storagePath;
    /**
     * 文件存储名称
     */
    private String storageName;
    /**
     * 类型
     */
    private String fileType;
    /**
     * 大小
     */
    private long fileSize;
    /**
     * 大类
     */
    private String mainType;
    /**
     * 子类
     */
    private String subType;
    /**
     * 摘要信息
     */
    private String summary;
    /**
     * 文件拥有者
     */
    private String ownerId;
    /**
     * 文件版本
     */
    private int version;
    /**
     * 文件状态
     */
    private int status;
    /**
     * 文件(夹)创建者
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Long createdAt;
    /**
     * 文件(夹)更新人员
     */
    private String updatedBy;
    /**
     * 修改时间
     */
    private Long updatedAt;
    /**
     * MD5(文件快传)
     */
    private String md5;
    /**
     * 文件是否删除
     */
    private int isDeleted;
    /**
     * 上级文件夹
     */
    private String parentId;
    /**
     * 文件数量
     */
    private int fileCount;
    /**
     * 文件、文件夹描述
     */
    private String description;
    /**
     * 排序
     */
    private int idx;
    /**
     * 是否收藏
     */
    private int isLike;
    /**
     * 静态请求路径
     */
    private String requestPath;
    /**
     * 扩展字段信息
     */
    private Map<String, Object> config;
    /**
     * 业务文件类型(详情页端，与文件无关，原样传递、原样返回)
     */
    private String bizFileType;
}


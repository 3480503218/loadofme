package com.dhm.service;

import com.dhm.model.FileUpload;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface FileRedisQueueService {
    /**
     * 缓存文件信息至PDF文件信息未转化队列
     * @param uploadInfo
     */
    void cacheFileInfoToPdfUnconvertQueue(FileUpload uploadInfo);
    /**
     * 上传PDF成功直接存储至redis哈希表
     * @param uploadInfo
     */
    void cachePdfFileToRedisHash(FileUpload uploadInfo);
    /**
     * 上传img成功直接存储至redis哈希表
     * @param uploadInfo
     */
    void cacheImageFileToRedisHash(FileUpload uploadInfo);
    /**
     * 上传token存储至redis哈希表
     * @param token
     * @param map
     * @param expire
     */
    void cacheTokenToRedisHash(String token, Map<String, Object> map, Long expire) ;
    /**
     * 根据给定的文件信息获取文件路径
     * @param fileName
     * @param type
     * @param param
     * @return
     */
    String getPdfOrImagePath(String fileName, String type, String param);
    /**
     * 根据给定的token是否过期
     * @param token
     * @return
     */
    boolean judgeTokenExpire(String token);
    /**
     * 根据给定的token判定是否通过拦截
     * @param token
     * @param paramMap
     * @return
     */
    boolean judgePassByToken(String token, Map<String, String> paramMap);
}


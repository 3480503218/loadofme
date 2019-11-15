package com.dhm.service;

import com.alibaba.fastjson.JSONObject;
import com.dhm.model.Address;
import com.dhm.model.FileUpload;
import com.jingdata.common.entity.Context;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface FileStoreService {
    /**
     * 文件上传服务
     * @param file
     * @param type
     * @return
     */
    FileUpload uploadFile(MultipartFile file, String type);
    /**
     * 临时文件上传服务
     * @param file
     * @param type
     * @return
     */
    FileUpload uploadTempFile(MultipartFile file, String type);

    /**
     * 获取要下载的文件的位置，主要是进行文件判断是否存在
     * @param storePath
     * @param type
     * @param fileName
     * @return
     */
    String getDownLoadFileUrl(String storePath, String type, String fileName);

    /**
     * 获取要下载的文件的位置，主要是进行文件判断是否存在
     * @param storePaths
     * @param type
     * @param fileName
     * @return
     */
    Set<String> getDownLoadFilesUrl(Set<String> storePaths, String type, String fileName);

    /**
     * 文件下载服务
     * @param storePath
     * @param type
     * @param fileName
     * @return
     */
    InputStreamResource downloadFile(String storePath, String type, String fileName);

    /**
     * 下载pdf文件
     * @param storePath
     * @param type
     * @param fileName
     * @return
     */
    InputStreamResource downloadPdfFile(String storePath, String type, String fileName);

    /**
     * 下载image文件
     * @param storePath
     * @param type
     * @param fileName
     * @return
     */
    InputStreamResource downloadImageFile(String storePath, String type, String fileName);

    /**
     * 多文件下载服务
     * @param storePaths
     * @param type
     * @param fileName
     * @return
     * @throws IOException
     */
    String downloadFiles(Set<String> storePaths, String type,String fileName) throws IOException;

    /**
     * 获取预览图片列表
     * @param fileName
     * @param type
     * @param storagePath
     * @param tenantId
     * @param appId
     * @param account
     * @param count
     * @return
     */
    JSONObject getSkanImageList(String fileName, String type, String storagePath, String tenantId, String appId, String account, String count);

    /**
     * 请求预览pdf
     * @param context
     * @param fileId
     * @param fileName
     * @param type
     * @param storePath
     * @param originName
     * @param expire
     * @return
     */
    Address skanPdfRequest(Context context, String fileId, String fileName, String type, String storePath, String originName, Long expire);

    /**
     * 预览pdf 路径
     * @param originName
     * @param fileName
     * @param type
     * @param storePath
     * @param tenantId
     * @param appId
     * @param account
     * @return
     */
    JSONObject skanPdfUrl(String originName,String fileName,String type,String storePath, String tenantId,String appId,String account);

    /**
     * 获取图片请求路径
     * @param fileName
     * @param type
     * @param storagePath
     * @param expire
     * @return
     */
    Address getImageRequestPath(String fileName, String type, String storagePath, Long expire);
    /**
     * 文件上传服务
     * @param file
     * @param type
     * @return
     */
    FileUpload uploadFiles(MultipartFile file, String type);
}

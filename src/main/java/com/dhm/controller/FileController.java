package com.dhm.controller;

import com.alibaba.fastjson.JSON;
import com.dhm.FileEnum.FileEnum;
import com.dhm.Req.FileBatchDownloadReq;
import com.dhm.Req.FileUploadReq;
import com.dhm.Util.TokenUtil;
import com.dhm.ce.CommonInfo;
import com.dhm.init.InitConfig;
import com.dhm.model.Address;
import com.dhm.model.FileUpload;
import com.dhm.service.FileRedisQueueService;

import com.dhm.service.FileStoreService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.jingdata.common.response.BaseResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class FileController {
    static List<String> officeCategory = Lists.newArrayList(FileEnum.DOC.getValue(), FileEnum.DOCX.getValue(), FileEnum.PPT.getValue(), FileEnum.PPTX.getValue(), FileEnum.TXT.getValue(), FileEnum.XLS.getValue(), FileEnum.XLSX.getValue());
private InitConfig config=InitConfig.getInitConfig();
@Resource
FileStoreService fileStoreService;
@Resource
FileRedisQueueService fileRedisQueueService;

    @PostMapping(value = "/uploadPath")
    public BaseResult uploadPath(@RequestBody FileUploadReq req) {
        //System.out.println("我进来了");
        BaseResult res = new BaseResult();
        String token = TokenUtil.md5(String.valueOf(System.currentTimeMillis()), "");
        //存储token到redis
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        String requestPath = null;
        if(Objects.equals("batch",req.getUploadType())){
            requestPath = config.getStoreProto() + config.getStoreIp() + config.getStorePort() + "/uploadFilesToken";
            System.out.println("request的路径"+requestPath);
        }else{
            requestPath = config.getStoreProto() + config.getStoreIp() + config.getStorePort() + "/uploadFileToken";
            System.out.println("request的路径"+requestPath);
        }
        Address address = new Address(config.getStoreProto(), config.getStoreIp(), config.getStorePort(), requestPath, token);
        System.out.println("address的"+address);
        System.out.println(requestPath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        res.setResult(address);
        System.out.println(res);
        return res;
    }


    @PostMapping(value = "/uploadFileToken")
    public BaseResult uploadFileToken(@RequestParam("file") MultipartFile file, String type, String token, String localPath) {
        BaseResult res = new BaseResult();
        //文件唯一状态 store_type_1=upload_disk_opt
        type = "upload_disk_opt";
        FileUpload ui = fileStoreService.uploadFile(file, type);

        if (ui.getMessage().equals(CommonInfo.UPLOAD_SUCCESS_INFO)) {
            if (StringUtils.isNotBlank(localPath)) {
                ui.setLocalPath(localPath);
            }
            String pattern = ui.getFileName().split("\\.")[1].toLowerCase();
            //文件上传成功,执行文件转换工作
            if (officeCategory.contains(pattern)) {
                //log.info("FileStore--文件(" + ui.getFileName() + ")上传成功，执行转化工作");
                CompletableFuture.supplyAsync(() -> {
                    fileRedisQueueService.cacheFileInfoToPdfUnconvertQueue(ui);
                    return "";
                });
            }
        }

        res.setResult(ui);
        //log.info("输出参数信息{}", JSON.toJSONString(res));
        return res;
    }

    @PostMapping(value = "/uploadFileTokens")
    public BaseResult uploadFileTokens() {
        System.out.println("我是/uploadFileTokenssss");
        return null;
    }


    @PostMapping(value = "/uploadss")
    public void uploadFileToken(@RequestParam("file") MultipartFile file) {
        System.out.println("文件上传现在开始");
    //return null;
    }


    @PostMapping(value = "/uploadPathOfme")
    public BaseResult upload(@RequestBody FileUploadReq fileUploadReq){
        BaseResult baseResult=new BaseResult();
        //先拼出token
        String token = TokenUtil.md5(String.valueOf(System.currentTimeMillis()), "");
        System.out.println(token);
        //返回IP proto port token request
        //requestPath: "https://investment-test.jingdata.com:443/api/file/store/uploadFileToken"
        String requestPath=null;
        if("batch".equals(fileUploadReq.getUploadType())){
            requestPath=config.getStoreProto()+config.getStoreIp()+config.getStorePort()+"/upload";
            System.out.println(requestPath);
        }
        else {
            requestPath=config.getStoreProto()+config.getStoreIp()+config.getStorePort()+"/upload";
            System.out.println(requestPath);
        }
        //String proto, String ipAddr, String port, String requestPath, String token
        Address address=new Address(config.getStoreProto(),config.getStoreIp(),config.getStorePort(),requestPath,token);
        baseResult.setResult(address);
        return baseResult;
    }

/*
/fileName: "a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477.png"
fileSize: 27251
fileSuffix: "png"
ipAddr: "investment-test.jingdata.com"
localPath: ""
message: "success"
name: "图片1.png"
port: ":443"
requestPath: "https://investment-test.jingdata.com:443/api/file/store/compressImage/a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477/a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477.png"
storePath: "/023/241/a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477.png"
type: "upload_disk_opt"
 */

    @PostMapping("/upload")
    public BaseResult uploadtrue(@RequestParam("file") MultipartFile file,String token,String localPath,String type){
        System.out.println("真实测试Of me");
        BaseResult baseResult=new BaseResult();
        //先判断token
        if(StringUtils.isBlank(token) || "".equals(token)){
            throw new RuntimeException("token为空");
        }
        type="upload_disk_opt";
        //0:/Users/dhm
        if(StringUtils.isBlank(config.getStoreConfigMap().get(type))){
            throw new RuntimeException("类型错误，无法获得存储路径");
        }
        FileUpload fileUpload = fileStoreService.uploadFiles(file, type);

            baseResult.setResult(fileUpload);

        return baseResult;
    }
    @PostMapping("/downloadOfme")
    public BaseResult down(@RequestBody FileBatchDownloadReq req)  {
        BaseResult baseResult=new BaseResult();
        String type="upload_disk_opt";
        System.out.println(req.getFileName()+req.getStorePaths());
        if(CollectionUtils.isEmpty(req.getStorePaths())){
            throw new RuntimeException("没有文件路径，无法下载");
        }
        //2019-11-14T16:36:38.855
        System.out.println(LocalDateTime.now());
        //201911141641
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        String fileName;
        if(StringUtils.isBlank(req.getFileName())){
           fileName=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        }
        else {
            fileName=req.getFileName()+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            // haomiao201911141641
            System.out.println(fileName);
        }
        //判断文件是否存在
        Set<String> downLoadFilesUrl = fileStoreService.getDownLoadFilesUrl(req.getStorePaths(), type, fileName);
        System.out.println("存在文件路径"+downLoadFilesUrl);
        //开始下载
        try {
            String zipfiles = fileStoreService.downloadFiles(req.getStorePaths(), type, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("下载文件IO异常");
        }
        return baseResult;
    }
}

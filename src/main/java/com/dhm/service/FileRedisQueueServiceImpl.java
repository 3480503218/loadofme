package com.dhm.service;

import com.dhm.FileEnum.FileConvertStateEnum;
import com.dhm.FileEnum.FileEnum;
import com.dhm.html.HtmlConvertThread;
import com.dhm.init.InitConfig;
import com.dhm.mapper.FileMapper;
import com.dhm.model.FileCacheInfoEntity;
import com.dhm.model.FileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
@Service
public class FileRedisQueueServiceImpl implements FileRedisQueueService{
 @Resource
private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private InitConfig config=InitConfig.getInitConfig();
    @Resource
    private FileMapper fileMapper;
    @Override
    public void cacheFileInfoToPdfUnconvertQueue(FileUpload fileUpload) {
        //docx
        String suffix = (fileUpload.getFileName().substring(fileUpload.getFileName().lastIndexOf(".") + 1)).toLowerCase();
        System.out.println("后缀  "+suffix);
        //0:/Users/dhm
        String value = config.getStoreConfigMap().get(fileUpload.getType());
        System.out.println("value1 "+value);
        // /Users/dhm/138/072/e375ac7e-08b4-4bc4-a1b1-1cd7ae7a5438_2022946924.docx
        String storePath = config.getStoreConfigMap().get(fileUpload.getType()).split(":")[1]
                + fileUpload.getStorePath();
        System.out.println("storePath  "+storePath);

        String url = config.getLoadProto() + config.getLoadIp() + config.getLoadPort() + "/" + fileUpload.getFileName();

        System.out.println("url    "+url);
        System.out.println(fileUpload);
        FileCacheInfoEntity fileCacheInfoEntity = new FileCacheInfoEntity
                //得到不加后缀的名字
                (FilenameUtils.getBaseName(fileUpload.getFileName()),
                        fileUpload.getIpAddr(),
                        fileUpload.getPort(),
                        storePath,
                        fileUpload.getType(),
                        fileUpload.getFileName(),
                        FileConvertStateEnum.UNCONVERT.getCode(),
                        FileConvertStateEnum.UNCONVERT.getCode(), "", "",
                        FileConvertStateEnum.UNCONVERT.getCode(), "", "",
                "", "", url);
        //fileMapper.insert(fileCacheInfoEntity);

        System.out.println("先转化成html");
        if (Objects.equals(suffix, FileEnum.XLS.getValue()) || Objects.equals(suffix, FileEnum.XLSX.getValue())) {
            //excel单独处理：先转化成html再转化成图片
            System.out.println("先转化成html再转化成图片");
            threadPoolTaskExecutor.execute(new HtmlConvertThread(fileCacheInfoEntity.getType(), fileCacheInfoEntity.getFileName(), fileCacheInfoEntity.getStorePath(), threadPoolTaskExecutor));
        } else {
            //开启PDF线程去执行转化任务
//            PdfConvertComponent pdfConvertComponent = new PdfConvertComponent(fileCacheInfoEntity.getType(), fileCacheInfoEntity.getFileName(), fileCacheInfoEntity.getStorePath(), threadPoolTaskExecutor);
//            pdfConvertComponent.convertPdf();
        }
    }

    @Override
    public void cachePdfFileToRedisHash(FileUpload uploadInfo) {

    }

    @Override
    public void cacheImageFileToRedisHash(FileUpload uploadInfo) {

    }

    @Override
    public void cacheTokenToRedisHash(String token, Map<String, Object> map, Long expire) {

    }

    @Override
    public String getPdfOrImagePath(String fileName, String type, String param) {
        return null;
    }

    @Override
    public boolean judgeTokenExpire(String token) {
        return false;
    }

    @Override
    public boolean judgePassByToken(String token, Map<String, String> paramMap) {
        return false;
    }
}

package com.dhm.service;


import com.alibaba.fastjson.JSONObject;
import com.dhm.ce.CommonInfo;
import com.dhm.init.InitConfig;
import com.dhm.mapper.FileMapper;
import com.dhm.model.Address;
import com.dhm.model.FileCacheInfoEntity;
import com.dhm.model.FileUpload;
import com.jingdata.common.entity.Context;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.shareall.charset.detector.CharsetDetector;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileStoreServiceImpl implements FileStoreService{
    private InitConfig config=InitConfig.getInitConfig();
    @Resource
private FileMapper fileMapper;
    public FileUpload uploadFile(MultipartFile file, String type) {
        if (StringUtils.isAnyBlank(type)) {
            System.out.println("参数为空");
            throw new RuntimeException("参数为空");
        }
        //根据存储类型来选择存储协议和存储路径
        //  0:/Users/dhm
        String value = config.getStoreConfigMap().get(type);

        if (StringUtils.isBlank(value)) {
            System.out.println("上传失败：文件上传传递的存储类型错误");
            throw new RuntimeException("上传失败：文件上传传递的存储类型错误");
        }
        FileUpload ui = new FileUpload();
        String proto = value.split(":")[0];
        //硬盘
        if (String.valueOf(0).equals(proto)) {
            System.out.println("进入UI");
            ui = uploadByDisk(file, type, value.split(":")[1]);
        }
        return ui;
    }

    @Override
    public FileUpload uploadTempFile(MultipartFile file, String type) {
        return null;
    }

    @Override
    public String getDownLoadFileUrl(String storePath, String type, String fileName) {
        return null;
    }

    @Override
    public Set<String> getDownLoadFilesUrl(Set<String> storePaths, String type, String fileName) {
            //判断存储状态
        if(StringUtils.isBlank(config.getStoreConfigMap().get(type))){
            throw new RuntimeException("文件存储状态错误");
        }
       String path= config.getStoreConfigMap().get(type).split(":")[1];
        //0
        String disk=config.getStoreConfigMap().get(type).split(":")[0];
        //硬盘
        if(Objects.equals("0",disk)){
            Set<String> set=storePaths;
            for(String  storePath:set){
                System.out.println("文件路径"+storePath);
                File file=new File(path+storePath);
                if(!file.exists() || file.isDirectory()){
                    throw new RuntimeException("文件不存在");
                }
                else {
                    System.out.println("文件存在");
                }
            }
            return set;
        }
        //只判断硬盘，否则为null
        return null;
    }

    @Override
    public InputStreamResource downloadFile(String storePath, String type, String fileName) {
        return null;
    }

    @Override
    public InputStreamResource downloadPdfFile(String storePath, String type, String fileName) {
        return null;
    }

    @Override
    public InputStreamResource downloadImageFile(String storePath, String type, String fileName) {
        return null;
    }

    @Override
    public String downloadFiles(Set<String> storePaths, String type, String fileName) throws IOException {
        if(StringUtils.isBlank(config.getStoreConfigMap().get(type))){
            throw new RuntimeException("文件存储状态错误");
        }
        if(CollectionUtils.isEmpty(storePaths)){
            throw new RuntimeException("没有文件路径，无法下载");
        }
        //如果是硬盘
        System.out.println(fileName);
        System.out.println(storePaths);
        //String realPath=config.getStoreConfigMap().get(type).split(":")[1]+storePaths;

        if(Objects.equals("0",config.getStoreConfigMap().get(type).split(":")[0])){
            FileOutputStream out=null;
            InputStream in=null;
            for (String path:storePaths){
                try{
                    String subName = nameUpdate(path);
                    String zipPath=config.getStoreConfigMap().get(type).split(":")[1]+"/000/000"+File.separator+subName+LocalDateTime.now()+".zip";
                    //String zipPath=config.getStoreConfigMap().get(type).split(":")[1]+"/000/000"+File.separator+subName+".zip";
                    in=new FileInputStream(config.getStoreConfigMap().get(type).split(":")[1]+path);
                    out=new FileOutputStream(zipPath);

                int j = 0;
                byte[] buffer = new byte[1024*1024];
                while ((j = in.read(buffer)) > 0) {
                    out.write(buffer);
                }

                    out.flush();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("下载文件IO异常");
                }
                finally {
                    // 关闭输入流


                }
            }
            in.close();
            out.close();
        }
        return null;
    }

    @Override
    public JSONObject getSkanImageList(String fileName, String type, String storagePath, String tenantId, String appId, String account, String count) {
        return null;
    }

    @Override
    public Address skanPdfRequest(Context context, String fileId, String fileName, String type, String storePath, String originName, Long expire) {
        return null;
    }

    @Override
    public JSONObject skanPdfUrl(String originName, String fileName, String type, String storePath, String tenantId, String appId, String account) {
        return null;
    }

    @Override
    public Address getImageRequestPath(String fileName, String type, String storagePath, Long expire) {
        return null;
    }
/*
/*
&&/fileName: "a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477.png"
&&fileSize: 27251
&&fileSuffix: "png"
%%ipAddr: "investment-test.jingdata.com"
localPath: ""
message: "success"
$$name: "图片1.png"
$$$port: ":443"
requestPath: "https://investment-test.jingdata.com:443/api/file/store/compressImage/a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477/a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477.png"
$$$storePath: "/023/241/a2d9c7c4-2b3a-4346-b49f-a70f26f73477_-658596477.png"
&&type: "upload_disk_opt"
 */

    @Override
    public FileUpload uploadFiles(MultipartFile file, String type){
        String fileName=null;
        //redis教案-郭永峰.docx
        fileName = file.getOriginalFilename();
        //文件后缀
        String suffix=FilenameUtils.getExtension(fileName).toLowerCase();

        //名字修改为转换的名字
        //如果文件没有名字
        if(StringUtils.isBlank(fileName)){
            //file
            System.out.println(file.getName());
            fileName=file.getName();
        }
        String newName = nameToNew(fileName);
        System.out.println(newName);
        //文件大小
       int size = (int) file.getSize();
        String storeIp = config.getStoreIp();
        String storePort = config.getStorePort();
        //    /Users/dhm
        String path=(config.getStoreConfigMap().get(type).split(":"))[1];
        String storePath=getRealPath(newName,path);
        System.out.println("路径是"+storePath);
        String uploadSuccessInfo = CommonInfo.UPLOAD_SUCCESS_INFO;

        FileUpload fileUpload=new FileUpload(storeIp,storePort,storePath,type,newName,uploadSuccessInfo,size,fileName,"",suffix,"");
        if(Objects.equals("0",(config.getStoreConfigMap().get(type).split(":"))[0])){
            //存储在硬盘
            uploadToDisk(file,type,storePath);
        }
        File isFile =new File(storePath);
        if(isFile.exists()){
            String path1 = getPath(newName);
            FileCacheInfoEntity fileCacheInfoEntity=new FileCacheInfoEntity(null,storeIp,storePort,path1,type,fileName,null,null,null,null,null,null,null,null,null,null);
            fileMapper.insert(fileCacheInfoEntity);
            System.out.println(path1);
            FileCacheInfoEntity fileCacheInfoEntity1 = fileMapper.selectByPath(path1);
            System.out.println("得到fileCacheInfoEntity实体"+fileCacheInfoEntity1);
        }
        else {
            throw new RuntimeException("文件不存在");
        }
        return fileUpload;
    }

    private void uploadToDisk(MultipartFile file,String type,String path) {
        if(StringUtils.isAnyBlank(type,path)){
           throw new RuntimeException("参数为null，无法储存文件");
        }

        try {
            File newfile=new File(path);
            newfile.createNewFile();
            file.transferTo(newfile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
    }





    private FileUpload uploadByDisk(MultipartFile multipartFile, String type, String storePath) {
        //文件的大小
        long size = multipartFile.getSize();
        if (StringUtils.isBlank(storePath)) {
            System.out.println("参数为空");
            throw  new RuntimeException("参数为空");
        }
        //File转换成MutipartFile
        FileInputStream inputStream = null;
        //获取文件名称（真实的，例如尚硅谷学习笔记）
        String temp = multipartFile.getOriginalFilename();
        System.out.println("multipartFile.getOriginalFilename"+temp);
        if (StringUtils.isBlank(temp)) {
            temp = multipartFile.getName();
        }
        //获得文件名字
        String fileName = genFileName(temp);
        System.out.println("fileName"+fileName);
        //获取文件上传路径
        //  /120/231/6abc2330-0d2d-4b60-aef7-046e0556ab1e_-1004466730.docx
        String tempPath = getStorePath(fileName, storePath);
        System.out.println(tempPath);
        try {
            File f = new File(storePath + tempPath);
            System.out.println("storePath + tempPath    "+storePath + tempPath);
            f.createNewFile();
            multipartFile.transferTo(f);

            if (Objects.equals(FilenameUtils.getExtension(fileName).toLowerCase(), "txt")) {
                saveFile2Charset(f, "UTF-8");
            }

            String url = config.getLoadProto() + config.getLoadIp() + config.getLoadPort() + "/compressImage/" + fileName.split("\\.")[0] + File.separator + fileName;
            return new FileUpload(config.getStoreIp(), config.getStorePort(),
                    tempPath, type, fileName, CommonInfo.UPLOAD_SUCCESS_INFO, Integer.parseInt(String.valueOf(size)), temp, url, temp.substring(temp.lastIndexOf(".") + 1), "");
        } catch (Exception e) {
            e.printStackTrace();
            //返回存储失败信息+记录日志

            throw new RuntimeException("上传失败：文件上传过程出现异常");
        }
    }
    /**
     * 以指定编码方式写文本文件，存在会覆盖
     *
     * @param file
     *            要写入的文件
     * @param toCharsetName
     *            要转换的编码
     * @throws Exception
     */
    public static void saveFile2Charset(File file, String toCharsetName) throws Exception {
        StringBuilder content = new StringBuilder("");

        if (!Charset.isSupported(toCharsetName)) {
            throw new UnsupportedCharsetException(toCharsetName);
        }
        //log.info("文件:{},编码为:{}",file.getName(),CharsetDetector.detect(file));
        //获取文件原始编码
       InputStreamReader isr = new InputStreamReader(new FileInputStream(file), CharsetDetector.detect(file));
        BufferedReader br = new BufferedReader(isr);
        String str = "";
        while (null != (str = br.readLine())) {
            //log.info("str:{}",str);
            content.append(str);
            content.append("\r\n");
       }
        OutputStream outputStream = new FileOutputStream(file);
        //增加头文件标识
       outputStream.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
       OutputStreamWriter outWrite = new OutputStreamWriter(outputStream, toCharsetName);
        outWrite.write(content.toString());
       outWrite.close();
        //log.info("----------文件转码成功----------");
    }


   private String genFileName(String name) {
       return UUID.randomUUID().toString() + "_" + name.hashCode()
                + name.substring(name.lastIndexOf("."));
    }
    private String getStorePath(String name, String storePath) {
        int hash = name.hashCode();
        //散列分布到二级目录中
        //0--255
        int d1 = hash & 0xff;
       int d2 = (hash & 0xff0) >> 4;
     return File.separator + ("00" + d1).substring(("00" + d1).length() - 3)
                + File.separator + ("00" + d2).substring(("00" + d2).length() - 3)
                + File.separator + name;
    }



    public String nameToNew(String name){
        name=UUID.randomUUID().toString()+"_"+name.hashCode()+name.substring(name.lastIndexOf("."));
        return name;
    }
    public String getRealPath(String name,String path){
        int i = name.hashCode();
        int i1 = i & 0xff;
        int i2 = (i & 0xff0)>>4;
        name=path+File.separator+String.valueOf("00"+i1).substring(("00"+i1).length()-3)+File.separator+String.valueOf("00"+i2).substring(("00"+i2).length()-3)+File.separator+name;
        return name;
    }


    public String getPath(String name){
        int i = name.hashCode();
        int i1 = i & 0xff;
        int i2 = (i & 0xff0)>>4;
        name=File.separator+String.valueOf("00"+i1).substring(("00"+i1).length()-3)+File.separator+String.valueOf("00"+i2).substring(("00"+i2).length()-3)+File.separator+name;
        return name;
    }
public String nameUpdate(String path){
    FileCacheInfoEntity fileCacheInfoEntity = fileMapper.selectByPath(path);
    System.out.println(fileCacheInfoEntity);
    String name = fileCacheInfoEntity.getFileName();
    String subName = name.substring(0,name.lastIndexOf("."));
    return subName;
}
}

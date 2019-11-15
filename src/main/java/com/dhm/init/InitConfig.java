package com.dhm.init;

import afu.org.checkerframework.checker.igj.qual.I;
import com.dhm.FileEnum.StoreEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Data
public class InitConfig {
    private static InitConfig initConfig=new InitConfig();
    //上传信息
    private Properties storeProp;
    //下载信息
    private Properties loadProp;

    //设置参数
    private String storeProto;
    private String storeIp;
    private String storePort;
    private String loadProto;
    private String loadIp;
    private String loadPort;
    private Map<String, String> storeConfigMap;
    private Map<String, String> loadConfigMap;
    //saas鉴权部分ip、端口
//    private String saasProto;
//    private String saasIp;
//    private String saasPort;
private InitConfig(){
    loadConfig();
    this.storeProto=storeProp.getProperty("store_server_proto");
    this.storeIp = storeProp.getProperty("store_server_ip");
    this.storePort = storeProp.getProperty("store_server_port");
    this.loadProto = loadProp.getProperty("download_server_proto");
    this.loadIp = loadProp.getProperty("download_server_ip");
    this.loadPort = loadProp.getProperty("download_server_port");
    //封装上传时参数
    storeConfigMap=new HashMap<String,String>();
    fillMap(storeConfigMap, storeProp, "store_type_", "store_proto_", "store_path_", true);
    //封装下载时参数
    loadConfigMap = new HashMap<String, String>();
    fillMap(loadConfigMap, loadProp, "download_type_", "download_proto_", "download_path_", true);
    //System.out.println(initConfig);
}
    public static InitConfig getInitConfig(){
    return initConfig;
}

    //参数map填充
    private void fillMap(Map<String, String> map, Properties p, String type, String proto, String path, boolean flag) {
        Set<String> set = p.stringPropertyNames();
        //System.out.println("set"+set);
        for (String key : set) {
           //System.out.println("key    "+key);
            if (key.startsWith(type)) {
                //这是上传下载路径
                map.put(p.getProperty(key), p.getProperty(proto + key.substring(type.length())) + ":"
                        + p.getProperty(path + key.substring(type.length())));
                //map的值    {upload_disk_opt=0:/Users/dhm}
                //System.out.println("map的值    "+map);
                //只有在存储时，如果是硬盘，则进行初始化生成多级目录
                //比较存储协议
                if (flag && String.valueOf(StoreEnum.DISK.getValue()).
                        equals(p.getProperty(proto + key.substring(type.length())))) {//硬盘
                    //StoreEnum.DISK.getValue()=0
                    //System.out.println("jjdahd哈还有"+StoreEnum.DISK.getValue());
                    //    /Users/dhm
                    System.out.println(p.getProperty(path + key.substring(type.length())));
                    createDirs(p.getProperty(path + key.substring(type.length())));
                }
            }
        }
    }


    //创建二级多个目录
    private void createDirs(String storePath) {
        if (!StringUtils.isBlank(storePath)) {
            File dir = new File(storePath);
            if (!dir.exists() || dir.list().length < 2) {
                //不存在创建该目录
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String temp1;
                String temp2;
                File t;
                for (int i = 0; i < 255; i++) {
                    temp1 = storePath + File.separator + ("00" + i).substring(("00" + i).length() - 3);
                    //System.out.println(temp1);
                    t = new File(temp1);
                    t.mkdirs();
                    for (int j = 0; j < 255; j++) {
                        temp2 = temp1 + File.separator + ("00" + j).substring(("00" + j).length() - 3);
                        t = new File(temp2);
                        t.mkdirs();
                    }
                }
            } else {
                System.out.println("##########[文件夹目录：" + storePath + "已初始化！]##########");

            }
        } else {
            System.out.println("##########[上传目录文件夹：" + storePath + "为空！]##########");
            System.out.println("上传目录为空");
            //throw new BusinessException(StoreExceptionMsg.IS_NULL_STORE_DIR.getCode(), StoreExceptionMsg.IS_NULL_STORE_DIR.getMessage());
        }

    }

    private void loadConfig() {
    storeProp=new Properties();
    loadProp=new Properties();
        InputStream store=null;
        InputStream load=null;
        try {
            store=new FileInputStream(new File("/Users/jingdata-10286/IdeaProjects/loadofme/src/main/resources/fileconfig/file.properties"));
            storeProp.load(store);
            load=new FileInputStream(new File("/Users/jingdata-10286/IdeaProjects/loadofme/src/main/resources/fileconfig/file.properties"));
            loadProp.load(load);
        } catch (FileNotFoundException e) {
            System.out.println("文件找不到");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
        }
        finally {
            try {
                store.close();
                load.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("关闭时IO异常");
            }
        }
    }

    public static void main(String[] args) {
        InitConfig initConfig=new InitConfig();

        System.out.println(initConfig.getStoreConfigMap().get("upload_disk_opt"));
       String str= initConfig.getStoreConfigMap().get("upload_disk_opt");
        String[] split = str.split(":");
        String s=split[0];
        System.out.println(s);
        System.out.println();
    }
}

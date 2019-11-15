package com.dhm.html;

import com.alibaba.fastjson.JSON;
import com.dhm.init.ConvertConfig;
import com.dhm.redis.RedisManager;
import com.google.common.collect.Maps;
import org.apache.commons.io.FilenameUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HtmlConvertThread implements Runnable {
    /**
     * 要转化的文件所在的位置
     */
    private String origin_path;
    /**
     * 文件类型(标识)
     */
    private String file_type;
    /**
     * 文件名称
     */
    private String file_name;
    private ThreadPoolTaskExecutor taskExecutor;
    private ConvertConfig convertConfig = ConvertConfig.getInstance();

    public HtmlConvertThread(String file_type, String file_name, String origin_path, ThreadPoolTaskExecutor taskExecutor) {
        this.file_type = file_type;
        this.file_name = file_name;
        this.origin_path = origin_path;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void run() {
        String nameWithoutPattern = FilenameUtils.getBaseName(file_name);
        //excel生成html根据sheet生成多个html
        String htmlDir = convertConfig.getPdfStoreDir() + File.separator + nameWithoutPattern;
        //log.info("htmlDir output:{}",htmlDir);
        ExcelToHtml excelToHtml = new ExcelToHtml();
        excelToHtml.convertExceltoHtml(origin_path, htmlDir, nameWithoutPattern);
        //开启IMAGE新线程去执行转化图片任务
        taskExecutor.execute(new HtmToImageThread(file_type, file_name, htmlDir, convertConfig));
    }
}

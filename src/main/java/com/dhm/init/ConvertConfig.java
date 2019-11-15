package com.dhm.init;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Data
public class ConvertConfig {
    private static ConvertConfig instance = new ConvertConfig();
    private Properties convertProp;

    /**
     * word转PDF的脚本位置
     */
    private String wordPdfShPath;
    /**
     * word转PPT的脚本位置
     */
    private String pptPdfShPath;
    /**
     * 可执行程序soffice所在的位置
     */
    private String sofficePath;
    /**
     * 指定转化后的PDF的存储目录
     */
    private String pdfStoreDir;
    /**
     * PDF转图片的脚本位置
     */
    private String pdfImageShPath;
    /**
     * 可执行程序ghostscript所在的位置
     */
    private String gsPath;
    /**
     * 可执行程序magick所在的位置
     */
    private String magickPath;
    /**
     * 转化后的image的存储目录
     */
    private String imageStoreDir;
    /**
     * 压缩后的image的存储目录
     */
    private String imageCompressDir;
    /**
     * 图片压缩的脚本位置
     */
    private String imageCompressShPath;
    /**
     * 生成水印透明图片的存储目录
     */
    private String imageWatermarkDir;
    /**
     * html转image的脚本位置
     */
    private String htmlImageShPath;
    /**
     * 可执行程序wkhtmltoimage所在的位置
     */
    private String wkhtmltoimagePath;
    /**
     * html转pdf的脚本位置
     */
    private String htmlPdfShPath;
    /**
     * 可执行程序wkhtmltopdf所在的位置
     */
    private String wkhtmltopdfPath;

    private ConvertConfig() {
        loadConfig();
        this.wordPdfShPath = convertProp.getProperty("word_pdf_sh_path");
        this.pptPdfShPath = convertProp.getProperty("ppt_pdf_sh_path");
        this.sofficePath = convertProp.getProperty("soffice_path");
        this.pdfStoreDir = convertProp.getProperty("pdf_store_dir");
        this.pdfImageShPath = convertProp.getProperty("pdf_image_sh_path");
        this.gsPath = convertProp.getProperty("gs_path");
        this.magickPath = convertProp.getProperty("magick_path");
        this.imageStoreDir = convertProp.getProperty("image_store_dir");
        this.imageCompressDir = convertProp.getProperty("image_compress_dir");
        this.imageCompressShPath = convertProp.getProperty("image_compress_sh_path");
        this.imageWatermarkDir = convertProp.getProperty("image_watermark_dir");
        this.htmlImageShPath = convertProp.getProperty("html_image_sh_path");
        this.wkhtmltoimagePath = convertProp.getProperty("wkhtmltoimage_path");
        this.htmlPdfShPath = convertProp.getProperty("html_pdf_sh_path");
        this.wkhtmltopdfPath = convertProp.getProperty("wkhtmltopdf_path");
        //初始化生成图片转化和压缩的目录
        createStoreDirs(pdfStoreDir, imageStoreDir, imageCompressDir, imageWatermarkDir);
    }

    public static ConvertConfig getInstance() {
        return instance;
    }

    //加载配置文件
    private void loadConfig() {

        convertProp = new Properties();
        InputStream convertIn = null;
        try {
            /*convertIn = new FileInputStream(new File("/Users/cygr-0101-01-0134/Documents/company/file.properties"));*/
            convertIn = new FileInputStream(new File("/Users/jingdata-10286/IdeaProjects/loadofme/src/main/resources/fileconfig/file.properties"));

            convertProp.load(convertIn);
            //log.info("文件加载成功!");
        } catch (Exception e) {
            e.printStackTrace();
            //log.info("转换文件夹初始化失败!");
            throw new RuntimeException("初始文件夹转换失败");
        } finally {
            try {
                //log.info("关闭文件流!");
                convertIn.close();
            } catch (Exception e) {
                //log.info("文件流关闭失败!");
                e.printStackTrace();
               throw new RuntimeException("图片转化和压缩目录为空");
            }
        }
    }

    //创建存储目录
    private void createStoreDirs(String pdfStoreDir, String imageStoreDir, String imageCompressDir, String imageWatermarkDir) {
        //log.info("##########[初始化创建PDF、IMAGE文件夹]##########");
        if (!StringUtils.isBlank(pdfStoreDir) && !StringUtils.isBlank(imageStoreDir)
                && !StringUtils.isBlank(imageCompressDir)) {
            //连同水印PDF目录一起生成
            File pdfDir = new File(pdfStoreDir + File.separator + "watermark");
            File imageDir = new File(imageStoreDir);
            File compressDir = new File(imageCompressDir);
            File watermarkDir = new File(imageWatermarkDir);
            if (!pdfDir.exists()) {
               // log.info("##[初始化pdfDir]##:" + pdfStoreDir);
                pdfDir.mkdirs();
            }
            if (!imageDir.exists()) {
                //log.info("##[初始化imageDir]##:" + imageDir);
                imageDir.mkdirs();
            }
            if (!compressDir.exists()) {
               // log.info("##[初始化compressDir]##:" + compressDir);
                compressDir.mkdirs();
            }
            if (!watermarkDir.exists()) {
               // log.info("##[初始化watermarkDir]##:" + watermarkDir);
                watermarkDir.mkdirs();
            }
        } else {
            throw new RuntimeException("图片转化和压缩目录为空");
        }
    }

    public static void main(String[] args) {
        ConvertConfig c=new ConvertConfig();
        System.out.println(c);
    }
}


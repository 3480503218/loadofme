package com.dhm.html;


import com.dhm.FileEnum.FileConvertStateEnum;
import com.dhm.Util.CommonUtil;
import com.dhm.Util.ImageWaterMark;
import com.dhm.init.ConvertConfig;
import org.apache.commons.exec.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 执行html文件转化为image的线程任务
 *
 * @author zhangpengfei@jingdata.com
 */

public class HtmToImageThread implements Runnable {

    private ConvertConfig convertConfig = ConvertConfig.getInstance();
    /**
     * 要转化的文件所在的目录
     */
    private String html_dir;
    /**
     * 文件类型(标识)
     */
    private String file_type;
    /**
     * 文件名称
     */
    private String file_name;
    /**
     * 宽度
     */
    private String width = "1024";

    public HtmToImageThread(String file_type, String file_name, String html_dir, ConvertConfig convertConfig) {
        this.file_type = file_type;
        this.file_name = file_name;
        this.html_dir = html_dir;
        this.convertConfig = convertConfig;
    }

    @Override
    public void run() {
        String nameWithoutPattern = FilenameUtils.getBaseName(file_name);
        //以文件名作为生成图片的目录
        String image_dir = convertConfig.getImageStoreDir() + File.separator + nameWithoutPattern;
        File dir = new File(image_dir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //HTML目录
        File htmlDir = new File(html_dir);
        if (htmlDir.exists() && htmlDir.isDirectory()) {
            for (int i = 0; i < htmlDir.listFiles().length; i++) {
                File html = htmlDir.listFiles()[i];
                if (html.getName().endsWith(".html")) {
                    String img_path = image_dir + File.separator + nameWithoutPattern + "-" + i + ".jpg";
                    String html_path = html_dir + File.separator + html.getName();
                    try {
                        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
                        Executor executor = new DefaultExecutor();
                        //设置0为正常退出值
                        executor.setExitValue(0);
                        //超时时间设置，超时将自杀
                        executor.setWatchdog(new ExecuteWatchdog(60 * 1000));
                        executor.execute(new CommandLine("/bin/sh").addArguments(
                                new String[]{
                                        "-c",
                                        convertConfig.getHtmlImageShPath() + " " + convertConfig.getWkhtmltoimagePath() + " " + html_path + " " + img_path + " " + width
                                }, false
                        ), resultHandler);
                        //此处阻塞等待
                        resultHandler.waitFor();
                        if (resultHandler.hasResult()) {
                            long endTime = System.currentTimeMillis();
                            int exitValue = resultHandler.getExitValue();

                            if (Objects.equals(exitValue, 0)) {
                                //获取生成的图片总数和图片尺寸
                                List<String> temp = CommonUtil.getimageFileList(image_dir);
                                String imageNumber = null;
                                String imageWidth = null;
                                String imageHeight = null;
                                if (temp != null && temp.size() > 0) {
                                    Map<String, Integer> sizeMap = new ImageWaterMark().getImgWidthAndHeight(new File(image_dir + File.separator + temp.get(0)));
                                    imageNumber = String.valueOf(temp.size());
                                    imageWidth = sizeMap.get("width") == null ? String.valueOf(880) : String.valueOf(sizeMap.get("width"));
                                    imageHeight = sizeMap.get("height") == null ? String.valueOf(1414) : String.valueOf(sizeMap.get("height"));

                                }

                            }else{
                                //FIXME 异常退出无需处理

                            }
                        }
                    } catch (Exception e) {
                        //修改状态

                        e.printStackTrace();
                        throw new RuntimeException("转换图片异常");
                    }
                }
            }
        }
    }
}

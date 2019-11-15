package com.dhm.Util;

import com.jingdata.common.exception.BusinessException;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 为图片添加水印
 * 两种方式：
 * 1／是通过java.awt的方式实现(执行效率远低于magick)
 * 2／是通过imagemagick实现(文字水印实现不好)
 *
 * @author zhangpengfei@jingdata.com
 */
public class ImageWaterMark {

    /**
     * 添加图片水印
     * @param srcImgPath 源图片路径
     * @param destImgPath 保存的图片路径
     * @param watermarkPath 水印图片路径
     */
    public void addImageMark(String srcImgPath, String destImgPath, String watermarkPath) {
        FileOutputStream outStream = null;
        try {
            // 读取原图片信息
            //得到文件
            File srcImgFile = new File(srcImgPath);
            //文件转化为图片
            Image srcImg = ImageIO.read(srcImgFile);
            //读取水印图片信息
            File watermarkFile = new File(watermarkPath);
            Image watermarkImg = ImageIO.read(watermarkFile);
            //获取图片的宽
            int srcImgWidth = srcImg.getWidth(null);
            //获取图片的高
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,
                                                        BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.drawImage(watermarkImg, 0, 0, srcImgWidth, srcImgHeight, null);

            g.dispose();
            // 输出图片
            outStream = new FileOutputStream(destImgPath);
            ImageIO.write(bufImg, "jpg", outStream);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片文件加文字异常");
        }finally{
            try {
                if(outStream != null){
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("图片文件加文字水印流关闭异常");
            }
        }
    }

    /**
     * @param srcImgPath 源图片路径
     * @param destImgPath 保存的图片路径
     * @param content 水印内容
     * @param color 水印颜色
     * @param font 水印字体
     * @param degree 字体倾斜角度-55
     * @param alpha 字体倾斜角度0.3f
     */
    public void addStringMark(String srcImgPath, String destImgPath, String content,
                             Color color,Font font,float degree,float alpha) {
        if(color == null){
            //默认淡灰色
            color= Color.LIGHT_GRAY;
        }
        if(font == null){
            //默认字体
            font = new Font("STSong-Light", Font.PLAIN, 40);
        }
        FileOutputStream outImgStream = null;
        try {
            // 读取原图片信息
            //得到文件
            File srcImgFile = new File(srcImgPath);
            //文件转化为图片
            Image srcImg = ImageIO.read(srcImgFile);
            //获取图片的宽
            int srcImgWidth = srcImg.getWidth(null);
            //获取图片的高
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,
                                                            BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //根据图片的背景设置水印颜色、设置字体
            g.setColor(color);
            g.setFont(font);
            //设置透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            //旋转
            g.rotate(Math.toRadians(degree),(double) srcImgWidth / 2,
                                                (double) srcImgHeight / 2);
            //设置水印的坐标
            int x = srcImgWidth - 2 * getWatermarkLength(content, g);
            int y = srcImgHeight - 2 * getWatermarkLength(content, g);
            if(srcImgWidth < srcImgHeight){
                //竖版图
                g.drawString(content, 400, 1100);
                g.drawString(content, 800, 300);
                g.drawString(content, 100,1800);
            }else{
                //横版图
                g.drawString(content, 900, 800);
                g.drawString(content, 500, 350);
                g.drawString(content, 200,200);
            }
            //画出水印
            g.drawString(content, x, y);
            g.dispose();
            // 输出图片
            outImgStream = new FileOutputStream(destImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片文件加文字异常");
        }finally{
            try {
                if(outImgStream != null){
                    outImgStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("图片文件加文字水印流关闭异常");
            }
        }
    }

    /**
     *根据图片流获取图片的尺寸
     */
    public Map<String, Integer> getImgWidthAndHeight(File file) {
        Map<String, Integer> imageMap = new HashMap<>();
        InputStream is = null;
        BufferedImage src = null;
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            imageMap.put("width", src.getWidth(null));
            imageMap.put("height", src.getHeight(null));
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片文件加文字水印流关闭异常");
        }
        return imageMap;
    }

    private int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(),0, waterMarkContent.length());
    }
}

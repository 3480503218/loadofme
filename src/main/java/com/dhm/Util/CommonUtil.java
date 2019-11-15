package com.dhm.Util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jingdata.common.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = Maps.newHashMap();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("POJO转MAP异常");
        }
        return map;
    }

    /**
     * 获取image图片列表
     *
     * @param imageDir：图片文件存储目录
     */
    public static List<String> getimageFileList(String imageDir) {
        List<String> fileList = Lists.newArrayList();
        File dir = new File(imageDir);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files.length > 0) {
                for (File image : files) {
                    if (image.exists() && !image.isDirectory() && image.getName().endsWith(".jpg")) {
                        fileList.add(image.getName());
                    }
                }
            }
        }
        //排序
        if (CollectionUtils.isNotEmpty(fileList)) {
            Collections.sort(fileList);
        }
        return fileList;
    }
}

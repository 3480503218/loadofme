package com.dhm.Req;


import com.jingdata.common.request.BaseRequest;
import lombok.Data;

@Data
public class FileUploadReq extends BaseRequest {
    /**
     * 设置超时时间
     */
    private Long expire;
    /**
     * 上传类型(batch：批量上传，默认为单文件上传)
     */
    private String uploadType;
}

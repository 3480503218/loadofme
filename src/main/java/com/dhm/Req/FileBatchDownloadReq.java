package com.dhm.Req;

import com.jingdata.common.request.BaseRequest;
import lombok.Data;

import java.util.Set;

@Data
public class FileBatchDownloadReq extends BaseRequest {
    /**
     * 存储路径
     */
    private Set<String> storePaths;
    /**
     * zip包名称
     */
    private String fileName;
}

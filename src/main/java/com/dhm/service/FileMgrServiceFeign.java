//package com.dhm.service;
//
//import com.dhm.model.FileStoreMgrEntity;
//import com.jingdata.common.response.BaseResult;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
//public interface FileMgrServiceFeign {
//    @FeignClient(value = "${service.file.name}", path = "/api/file/storeMgr", fallback = FileMgrServiceFallback.class)
//    public interface FileMgrServiceFeign {
//
//
//        /**
//         * 依据存储路径获取文件
//         */
//        @RequestMapping(value="/getFileInfoByStorePaths", method=RequestMethod.POST)
//        BaseResult<List<FileStoreMgrEntity>> getFileInfoByStorePaths(@RequestBody FileBatchDownloadStorePathsReq req);
//
//    }
//}

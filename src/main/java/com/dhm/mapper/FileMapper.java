package com.dhm.mapper;

import com.dhm.model.FileCacheInfoEntity;
import com.dhm.model.FileStoreMgrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface FileMapper {
    public FileStoreMgrEntity selectById(String id);
    public void insert(FileCacheInfoEntity fileCacheInfoEntity);
    public FileCacheInfoEntity selectByPath(String storePath);
}

package com.dhm.controller;

import com.dhm.mapper.FileMapper;
import com.dhm.model.FileStoreMgrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;


@RestController
//代表的是一个资源可以接受的 MIME 类型
@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
//代表的是一个资源可以返回的 MIME 类型。
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class TestController {
    @Resource
    public FileMapper fileMapper;

    @GetMapping("/hello/{id}")
    public void test(@PathVariable("id") String id){
        FileStoreMgrEntity fileStoreMgrEntity = fileMapper.selectById(id);
        System.out.println(fileStoreMgrEntity);
    }
}

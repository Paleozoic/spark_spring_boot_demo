package com.maxplus1.spark.demo.java.service;

import com.maxplus1.spark.demo.java.dao.test1db.Test1Dao;
import com.maxplus1.spark.demo.java.entity.Test1Pojo;
import com.maxplus1.spark.demo.java.service.remote.ITest1Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiaolong.qiu on 2017/1/19.
 */
@Service
public class Test1Service implements ITest1Service {


    @Resource
    private Test1Dao test1Dao;

    @Override
    public Test1Pojo getTest1(Long id) {
        return test1Dao.getTest1(id);
    }
}

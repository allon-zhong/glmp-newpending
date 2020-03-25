package com.sinosoft.cloud.controller;

import com.sinosoft.cloud.entity.StudentEntity;
import com.sinosoft.cloud.entity.UserEntity;
import com.sinosoft.cloud.hxDao.HxDao;
import com.sinosoft.cloud.ztDao.ZtDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
public class ApiController {

    @Autowired
    private HxDao hxDao;
    @Autowired
    private ZtDao ztDao;


    @RequestMapping(value = "/api/createUser", method = RequestMethod.POST)
    public String createUser(@RequestBody UserEntity user, HttpServletRequest request, HttpServletResponse response) {

        log.info("请求参数为:{}",user);
        hxDao.createUser(user);
        return "";
    }
    @RequestMapping(value = "/api/createStudent", method = RequestMethod.POST)
    public String createStudent(@RequestBody StudentEntity student, HttpServletRequest request, HttpServletResponse response) {

        log.info("请求参数为:{}",student);
        ztDao.createStudent(student);
        return "";
    }
    @RequestMapping(value = "/api/getUser", method = RequestMethod.GET)
    public String getUser(String name, HttpServletRequest request, HttpServletResponse response) {

        log.info("请求参数为:{}",name);
        UserEntity user = hxDao.getUser(name);
        UserEntity user1 = ztDao.getUser(name);
        ArrayList<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(user);
        userEntities.add(user1);
        log.info("响应报文为:{}",userEntities);
        return userEntities.toString();
    }

    @RequestMapping(value = "/api/getStudent", method = RequestMethod.GET)
    public String getStudent(String name, HttpServletRequest request, HttpServletResponse response) {

        log.info("请求参数为:{}",name);
        StudentEntity student = ztDao.getStudent(name);
        log.info("响应报文为:{}",student);
        return student.toString();
    }

    //测试有事务控制,结果回滚了,两个数据库都没有插入数据
    @Transactional(transactionManager = "xatx")
    @RequestMapping(value = "/api/testTransaction", method = RequestMethod.POST)
    public String testTransaction(@RequestBody StudentEntity student, HttpServletRequest request, HttpServletResponse response) {
        log.info("请求参数为:{}",student);
        ztDao.createStudent(student);
        int i = 1/0;
        hxDao.createStudent(student);
        return "";
    }

    //测试没有事务控制,结果没有回滚, zt数据库插入了一条脏数据
    @RequestMapping(value = "/api/testnoTransaction", method = RequestMethod.POST)
    public String testnoTransaction(@RequestBody StudentEntity student, HttpServletRequest request, HttpServletResponse response) {
        log.info("请求参数为:{}",student);
        ztDao.createStudent(student);
        int i = 1/0;
        hxDao.createStudent(student);
        return "";
    }

}

package com.demo.controller;

import com.demo.dal.TestUserMapper;
import com.demo.dto.TestUserDTO;
import com.demo.entities.TestUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: TestUserController
 * @description: 测试连接池连接释放
 * @version: 1.0
 * @author: minsky
 * @date: 2022/12/13
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestUserController {


    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private TestUserMapper testUserMapper;

    @RequestMapping("addUser")
    public void addUser(@RequestBody TestUserDTO userDTO){

        TestUser testUser = new TestUser();
        testUser.setUsername(userDTO.getUsername());
        testUser.setAge(userDTO.getAge());
        transactionTemplate.executeWithoutResult(status -> {
            testUserMapper.insert(testUser);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.error("执行出现异常:",e);
            }
        });

    }
}

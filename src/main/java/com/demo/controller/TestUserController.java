package com.demo.controller;

import com.demo.dal.TestUserMapper;
import com.demo.dto.TestUserDTO;
import com.demo.entities.TestUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: TestUserController
 * @description: TODO
 * @version: 1.0
 * @author: minsky
 * @date: 2022/12/13
 */
@RestController
@RequestMapping("test")
public class TestUserController {


    @Resource
    private TestUserMapper testUserMapper;

    @RequestMapping("addUser")
    public void addUser(@RequestBody TestUserDTO userDTO){

        TestUser testUser = new TestUser();
        testUser.setUsername(userDTO.getUsername());
        testUser.setAge(userDTO.getAge());

        testUserMapper.insert(testUser);
    }
}

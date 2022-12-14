package com.demo.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entities.TestUser;

public interface TestUserMapper extends BaseMapper<TestUser> {
    int deleteByPrimaryKey(Long id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
}
package com.demo.entities;

import java.io.Serializable;
import lombok.Data;

/**
 * test_user
 * @author 
 */
@Data
public class TestUser implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    /**
     * 职位
     */
    private String position;

    private static final long serialVersionUID = 1L;
}
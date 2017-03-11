package com.wubaoguo.springboot.service;

public interface UserService {
	/**
     * 新增一个用户
     * @param name
     * @param age
     */
    void create(String name, Integer age);
    /**
     * 根据name删除一个用户高
     * @param name
     */
    void deleteByName(String name);
    
    /**
     * 根据name获取用户
     * @param name
     */
    Integer findAgeByName(String name);
    
    /**
     * 获取用户总量
     */
    Integer getAllUsers();
    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}

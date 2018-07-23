package com.wubaoguo.springboot.rest.service;

import com.wubaoguo.springboot.dao.MyBatisTestDao;
import com.wubaoguo.springboot.entity.SysAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wustrive.java.common.util.StringUtil;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:52
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Service
public class MyBatisTestService {

    @Autowired
    MyBatisTestDao myBatisTestDao;

    public SysAdmin getUserById(String id) {
        return myBatisTestDao.getUserById(id);
    }

    public String getRoleNameById(String id) {
        return myBatisTestDao.getRoleNameById(id);
    }

    public Integer addSysAdmin(com.wubaoguo.springboot.model.SysAdmin admin) {
        return myBatisTestDao.addSysAdmin(StringUtil.getUUID(), admin.getName(), admin.getPhoneNumber());
    }
}

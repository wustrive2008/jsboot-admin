package com.wubaoguo.springboot.manage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wustrive.java.common.secret.MD5Encrypt;
import org.wustrive.java.common.util.ConvertUtil;
import org.wustrive.java.common.util.StringUtil;
import org.wustrive.java.core.bean.AuthBean;
import org.wustrive.java.core.bean.CurrentRole;
import org.wustrive.java.core.bean.CurrentUser;
import org.wustrive.java.core.exception.LoginSecurityException;
import org.wustrive.java.dao.jdbc.dao.BaseDao;

import com.google.common.collect.ImmutableMap;
import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.entity.SysAdmin;
import com.wubaoguo.springboot.entity.SysRole;

/**
 * Description: 管理后台登录验证
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:20
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Service
public class AuthenticationService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    SysConfigService sysConfigService;


    public AuthBean login(AuthBean auth) throws AccountException,
            LoginSecurityException {
        // 查询预登录员工信息
        SysAdmin baseUser = new SysAdmin().setAccount(auth.getUsername()).queryForBean();
        if (baseUser != null && StringUtil.isNotBlank(baseUser.getId())) {
            if (ShiroConstants.IS_ACTIVITY_NO.equals(baseUser.getIs_activity())) {
                throw new DisabledAccountException("当前登录账号已被禁用");
            }
            if (StringUtil.isNoneBlank(auth.getPassword())) {
                String passwordMD5 = MD5Encrypt.MD5Encode(auth.getPassword());
                // 验证密码是否正确
                if (baseUser.getPassword().equals(passwordMD5)) {
                    // 验证成功登录返回员工信息
                    // 员工帐号密码值需向上copy
                    CurrentUser currentUser = new CurrentUser();
                    BeanUtils.copyProperties(baseUser, currentUser);
                    // 初始化 系统配置到当前 用户session
                    sysConfigService.initSysConfigToSession(currentUser.getId());
                    return auth.copy(currentUser);
                } else {
                    throw new IncorrectCredentialsException("密码错误");
                }
            } else {
                throw new IncorrectCredentialsException("密码未正确填写");
            }
        } else {
            throw new UnknownAccountException("帐号不存在");
        }
    }

    public Set<String> findShiroRoles(String currentUsername) {
        List<Map<String, Object>> rolesListMap = baseDao.queryForList("SELECT r.`code` FROM sys_admin a LEFT JOIN sys_admin_role r ON a.id = r.admin_id where a.account = ?", currentUsername);
        Set<String> rolesSet = new HashSet<String>(rolesListMap.size());
        for (Map<String, Object> rolesMap : rolesListMap) {
            rolesSet.add(ConvertUtil.obj2Str(rolesMap.get("code")));
        }
        return rolesSet;
    }

    public List<CurrentRole> findUserRoles(String currentUsername) {
        // 根据管理员帐号 查询 绑定角色
        String sql = "select r.* from sys_admin a,sys_admin_role ar,sys_role r where a.id = ar.admin_id and ar.`code` = r.`code` and r.is_activity = 1 and a.account = :account order by r.sort asc";
        // 执行查询
        List<SysRole> roles = baseDao.queryForBeanList(sql, ImmutableMap.of("account", currentUsername), new SysRole());
        /// 结果集转换
        List<CurrentRole> list = new ArrayList<CurrentRole>();
        for (SysRole role : roles) {
            CurrentRole r = new CurrentRole();
            r.setName(role.getName());
            r.setCode(role.getCode());
            list.add(r);
        }
        return list;
    }

}

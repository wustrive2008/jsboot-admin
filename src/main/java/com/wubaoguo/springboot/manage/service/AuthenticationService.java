package com.wubaoguo.springboot.manage.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.collect.ImmutableMap;
import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.bean.CurrentRole;
import com.wubaoguo.springboot.core.bean.CurrentUser;
import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.dao.jdbc.dao.BaseDao;
import com.wubaoguo.springboot.core.exception.LoginSecurityException;
import com.wubaoguo.springboot.entity.sys.SysAdmin;
import com.wubaoguo.springboot.entity.sys.SysRole;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description: 管理后台登录验证
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:20
 */
@Service
public class AuthenticationService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    SysConfigService sysConfigService;


    public CurrentUser login(LoginParam auth) throws AccountException,
            LoginSecurityException {
        // 查询预登录员工信息
        SysAdmin baseUser = new SysAdmin().setAccount(auth.getUsername()).queryForBean();
        if (baseUser != null && StringUtils.isNotBlank(baseUser.getId())) {
            if (ShiroConstants.IS_ACTIVITY_NO.equals(baseUser.getIs_activity())) {
                throw new DisabledAccountException("当前登录账号已被禁用");
            }
            if (StringUtils.isNoneBlank(auth.getPassword())) {
                String passwordMD5 = DigestUtil.md5Hex(auth.getPassword());
                // 验证密码是否正确
                if (baseUser.getPassword().equals(passwordMD5)) {
                    // 验证成功登录返回员工信息
                    // 员工帐号密码值需向上copy
                    CurrentUser currentUser = new CurrentUser();
                    currentUser.setId(baseUser.getId());
                    // 初始化 系统配置到当前 用户session
                    sysConfigService.initSysConfigToSession(currentUser.getId());
                    return currentUser;
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
            rolesSet.add(Convert.toStr(rolesMap.get("code")));
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

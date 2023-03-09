package com.wubaoguo.springboot.manage.service;


import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.collect.ImmutableMap;
import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.bean.CurrentRole;
import com.wubaoguo.springboot.core.request.BaseState;
import com.wubaoguo.springboot.dao.jdbc.dao.BaseDao;
import com.wubaoguo.springboot.entity.SysAdmin;
import com.wubaoguo.springboot.entity.SysResources;
import com.wubaoguo.springboot.manage.domain.SearchResults;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageService {

    @Autowired
    BaseDao baseDao;

    @SuppressWarnings("unchecked")
    public List<SysResources> findSysResourcesByRole(String roleCode, String userId) {
        Object current_resource = SecurityUtils.getSubject().getSession().getAttribute(ShiroConstants.SESSION_CURRENT_RESOURCE + roleCode);
        if (current_resource != null && current_resource instanceof List) {
            return (List<SysResources>) current_resource;
        }
        if (StringUtils.isNotBlank(roleCode)) {
            StringBuilder querySql = new StringBuilder("SELECT re.* FROM sys_admin a, sys_admin_role r, sys_role_resources rrl, sys_resources re ");
            querySql.append("WHERE rrl.role_code = r.`code` AND re.is_activity = 1 AND rrl.resources_id = re.id AND a.id = r.admin_id ");
            querySql.append("AND (r.`code` =:role_code OR r.code = 'all') ");
            querySql.append("AND a.id = :user_id ");
            querySql.append("ORDER BY re.sort ASC");
            List<SysResources> list = baseDao.queryForBeanList(querySql.toString(), ImmutableMap.of("role_code", roleCode, "user_id", userId), new SysResources());
            // 调整数据结构移除主要list中子节点数据 全部子节点临时存储集合
            List<SysResources> allChildrens = new ArrayList<SysResources>();
            for (SysResources parent : list) {
                List<SysResources> childrens = new ArrayList<SysResources>();
                for (SysResources children : list) {
                    if (parent.getId().equals(children.getGroup_id())) {
                        childrens.add(children);
                        allChildrens.add(children);
                    }
                }
                // 整理子节点到父节点list结构中
                parent.setChildrens(childrens);
            }
            // 子节点数据移除
            list.removeAll(allChildrens);
            SecurityUtils.getSubject().getSession().setAttribute(ShiroConstants.SESSION_CURRENT_RESOURCE, list);
            return list;
        } else {
            return null;
        }
    }

    public List<SearchResults> searchSysResource(String keywords) {
        CurrentRole currentRole = ShiroConstants.getCurrentRoles().get(0);
        Map<String, Object> paramMap = new HashMap<String, Object>(3);
        StringBuilder querySql = new StringBuilder("SELECT re.* FROM sys_admin a, sys_admin_role r, sys_role_resources rrl, sys_resources re ");
        querySql.append("WHERE rrl.role_code = r.`code` AND re.is_activity = 1 AND rrl.resources_id = re.id AND a.id = r.admin_id ");
        querySql.append("AND (r.`code` =:role_code OR r.code = 'all') ");
        querySql.append("AND re.uri != 'none' ");
        querySql.append("AND a.id = :user_id ");
        querySql.append("AND re.`menu_name` like :keywords ");
        paramMap.put("keywords", "%" + keywords + "%");
        paramMap.put("role_code", currentRole.getCode());
        paramMap.put("user_id", ShiroConstants.getCurrentUser().getUserId());
        List<SysResources> sysResourcess = baseDao.queryForBeanList(querySql.toString(), paramMap, new SysResources());
        List<SearchResults> rList = new ArrayList<SearchResults>();
        for (SysResources sysResources : sysResourcess) {
            StringBuilder details = new StringBuilder();
            // 不查询父节点菜单
            if ("root".equals(sysResources.getGroup_id())) {
                details.append("上级菜单[root]");
            } else {
                String parent_name = new SysResources().setId(sysResources.getGroup_id()).queryForBean().getMenu_name();
                details.append("上级菜单 [" + parent_name + "]");
            }
            rList.add(new SearchResults(sysResources.getMenu_name(), sysResources.getUri(), details.toString()));
        }
        return rList;
    }

    /**
     * 修改用户密码
     *
     * @param account 账号
     * @param pwd     明文新密码
     * @param oldpwd  明文旧密码
     */
    public BaseState updateUserPwd(String account, String pwd, String oldpwd) {
        if (StringUtils.isBlank(account)) {
            return new BaseState(BaseState.S_AUTH_ERROR, "账号不正确");
        }
        if (StringUtils.isBlank(pwd)) {
            return new BaseState(BaseState.S_AUTH_ERROR, "新密码未正确填写");
        }
        if (StringUtils.isBlank(oldpwd)) {
            return new BaseState(BaseState.S_AUTH_ERROR, "旧密码未正确填写");
        }

        SysAdmin baseUser = new SysAdmin().setAccount(account).queryForBean();
        if (baseUser == null || StringUtil.isBlank(baseUser.getId())) {
            return new BaseState(BaseState.S_AUTH_ERROR, "帐号不存在");
        }
        if (ShiroConstants.IS_ACTIVITY_NO.equals(baseUser.getIs_activity())) {
            return new BaseState(BaseState.S_AUTH_ERROR, "当前登录账号已被禁用");
        }

        String passwordMD5 = DigestUtil.md5Hex(oldpwd);
        // 验证旧密码是否正确
        if (baseUser.getPassword().equals(passwordMD5)) {
            // 验证成功，在验证新密码
            String sql = "update sys_admin set password=:pwd where account=:account";
            Map<String, String> paramMap = new HashMap<String, String>(2);
            paramMap.put("pwd", DigestUtil.md5Hex(pwd));
            paramMap.put("account", account);
            baseDao.execute(sql, paramMap);
            return new BaseState();
        } else {
            return new BaseState(BaseState.S_AUTH_ERROR, "旧密码错误");
        }

    }

    /**
     * 判断用户是否有权限访问此模块
     *
     * @param roleCode
     * @param uri
     * @return
     */
    public boolean hasPermission(String roleCode, String uri) {
        List<Map<String, Object>> permUrls = getPermUrlsByRoleCode(roleCode);
        if (null == permUrls || permUrls.isEmpty()) {
            return false;
        }
        int startIndex = 0;
        if (uri.indexOf("/manage") > 0) {
            startIndex = uri.indexOf("/manage");
        }
        String matchUrl = uri.substring(startIndex, uri.length());
        if (matchUrl.lastIndexOf("table") > 0) {
            matchUrl = matchUrl.replace("table", "main.html");
        }
        for (Map<String, Object> map : permUrls) {
            if (matchUrl.equals(map.get("uri").toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据角色获取授权url
     *
     * @param roleCode
     * @return
     */
    private List<Map<String, Object>> getPermUrlsByRoleCode(String roleCode) {
        String sql = "select a.uri from sys_resources a, sys_role_resources b where a.id = b.resources_id and b" +
                ".role_code=:roleCode ";
        return baseDao.queryForListMap(sql, ImmutableMap.of("roleCode", roleCode));
    }
}

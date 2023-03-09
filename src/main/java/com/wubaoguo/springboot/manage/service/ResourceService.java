package com.wubaoguo.springboot.manage.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.request.BaseState;
import com.wubaoguo.springboot.core.request.StateMap;
import com.wubaoguo.springboot.core.dao.jdbc.dao.BaseDao;
import com.wubaoguo.springboot.core.dao.jdbc.dao.QuerySupport;
import com.wubaoguo.springboot.entity.*;
import com.wubaoguo.springboot.manage.controller.commond.ResourceCommond;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResourceService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    private QuerySupport querySupport;

    public List<Map<String, Object>> findSysResource() {
        return baseDao.queryForListMap("select two.*,one.menu_name as one_name,one.icon as one_icon,one.is_activity as one_is_activity from sys_resources two left join sys_resources one on two.group_id = one.id where 0=0", null);
    }

    public List<Map<String, Object>> findSysResource(String group_id) {
        return baseDao.queryForListMap("select id,menu_name from sys_resources where group_id = :group_id order by sort asc ", ImmutableMap.of("group_id", group_id));
    }

    public List<Map<String, Object>> findSysResourceByCommond(ResourceCommond commond) {
        StringBuilder sql = new StringBuilder("select two.*,one.menu_name as one_name,one.icon as one_icon,one.is_activity as one_is_activity from sys_resources two left join sys_resources one on two.group_id = one.id where 1=1 ");
        Map<String, Object> param = Maps.newHashMap();
        if (StringUtils.isNotBlank(commond.getMenuName())) {
            sql.append(" and two.menu_name like :menu_name ");
            param.put("menu_name", "%" + commond.getMenuName().trim() + "%");
        }
        if (StringUtils.isNotBlank(commond.getParentMenu())) {
            sql.append(" and two.group_id = :parentMenu ");
            param.put("parentMenu", commond.getParentMenu());
        }
        sql.append("order by two.sort asc");
        commond.setPager(true);
        return querySupport.find(sql.toString(), param, commond);
    }

    public Map<String, Object> getSysResource(String id) {
        return baseDao.queryForMap("select * from sys_resources where id = ?", id);
    }

    public List<Map<String, Object>> findSysRoleResourcesByRoleCode(String roleCode) {
        return baseDao.queryForListMap("select resources_id from sys_role_resources where role_code = :role_code", ImmutableMap.of("role_code", roleCode));
    }

    public List<Map<String, Object>> findSysResourceZtree() {
        return baseDao.queryForListMap("select id,menu_name,group_id from sys_resources where 0=0 order by sort asc", null);
    }

    /**
     * 查询 资源菜单树
     *
     * @return
     */
    public List<Map<String, Object>> findSysResourceTree(String roleCode) {
        List<Map<String, Object>> rList = findSysResource("root");
        for (Map<String, Object> map : rList) {
            String resourceId = Convert.toStr(map.get("id"));
            map.put("isChoice", isBindResource(roleCode, resourceId));
            List<Map<String, Object>> rListB = findSysResource(resourceId);
            map.put("children", rListB);
            for (Map<String, Object> mapB : rListB) {
                mapB.put("isChoice", isBindResource(roleCode, resourceId));
            }
        }
        return rList;
    }

    private boolean isBindResource(String roleCode, String resourceId) {
        if (StringUtils.isAnyBlank(roleCode, resourceId)) {
            return false;
        }
        Integer count = baseDao.queryForInteger("select count(*) from sys_role_resources where role_code = :role_code and resources_id = :resources_id", ImmutableMap.of("role_code", roleCode, "resources_id", resourceId));
        return count > 0 ? true : false;
    }

    /**
     * 添加菜单项
     *
     * @param sysResources
     * @return
     */
    public BaseState saveSysResource(SysResources sysResources) {
        sysResources.insertOrUpdate();
        return new BaseState();
    }


    public String updateSysResources(String id, String column, String value) {
        SysResources sysResources = new SysResources();
        sysResources.setId(id);
        switch (column) {
            case "menu_name":
                sysResources.setMenu_name(value);
                break;
            case "sort":
                sysResources.setSort(Integer.valueOf(value));
                break;
            case "is_activity":
                sysResources.setIs_activity(Integer.valueOf(value));
                break;
        }
        SysResources dbSysResources = new SysResources(id).queryForBean();
        if (dbSysResources != null) {
            sysResources.update();
        }
        return value;
    }

    public List<Map<String, Object>> findSysRole() {
        return baseDao.queryForListMap("select * from sys_role order by sort asc", null);
    }

    public Map<String, Object> getSysRole(String id) {
        return baseDao.queryForMap("select * from sys_role where id = ?", id);
    }

    public Map<String, Object> getSysRoleCode(String Code) {
        return baseDao.queryForMap("select * from sys_role where code = ?", Code);
    }

    /**
     * 角色资源 存储
     *
     * @param sysRole
     * @param resourceIds
     * @return
     */
    public BaseState saveSysRole(SysRole sysRole, String[] resourceIds) {
        if (resourceIds == null || resourceIds.length == 0) {
            return new BaseState(StateMap.S_CLIENT_PARAM_ERROR, "角色资源必须绑定，若该角色暂不使用请作废。");
        }
        String roleCode = sysRole.getCode();
        if (StringUtil.isBlank(sysRole.getId())) {
            // 维护角色id为 null 校验 该角色编码是否已被使用
            SysRole dbSysRole = new SysRole().setCode(roleCode).queryForBean();
            if (dbSysRole != null && StringUtils.isNotBlank(dbSysRole.getId())) {
                return new BaseState(StateMap.S_CLIENT_PARAM_ERROR, "新建角色编码重复，添加失败");
            }
        } else {
            // 删除 该角色以绑定 旧资源数据
            baseDao.execute("delete from sys_role_resources where role_code = :roleCode", ImmutableMap.of("roleCode", roleCode));
        }
        sysRole.insertOrUpdate();
        for (String resourceId : resourceIds) {
            new SysRoleResources().setResources_id(resourceId).setRole_code(roleCode).insert();
        }
        return new BaseState();
    }

    public String updateSysRole(String id, String column, String value) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        switch (column) {
            case "sort":
                sysRole.setSort(Integer.valueOf(value));
                break;
        }
        SysRole dbsysRole = new SysRole(id).queryForBean();
        if (dbsysRole != null) {
            sysRole.update();
        }
        return value;
    }

    public List<Map<String, Object>> findSysAdmin() {
        List<Map<String, Object>> rList = baseDao.queryForListMap("select * from sys_admin order by add_time desc", null);
        for (Map<String, Object> map : rList) {
            map.put("roles", findSysAdminRole(Convert.toStr(map.get("id"))));
        }
        return rList;
    }

    public BaseState saveSysAdmin(SysAdmin sysAdmin, String[] roleCodes) {
        if (roleCodes == null || roleCodes.length == 0) {
            return new BaseState(StateMap.S_CLIENT_WARNING, "添加管理员必须选择角色");
        }

        String account = sysAdmin.getAccount();
        if (StringUtil.isBlank(sysAdmin.getId())) {
            // 维护角色id为 null 校验 该角色编码是否已被使用
            SysAdmin dbSysAdmin = new SysAdmin().setAccount(account).queryForBean();
            if (dbSysAdmin != null && StringUtils.isNotBlank(dbSysAdmin.getId())) {
                return new BaseState(StateMap.S_CLIENT_WARNING, "新增帐号已存在");
            }
            sysAdmin.setPassword(DigestUtil.md5Hex(sysAdmin.getPassword()));
            sysAdmin.setAdd_time(ShiroConstants.currentTimeSecond());
        } else {
            // 删除 该角色以绑定 旧资源数据
            baseDao.execute("delete from sys_admin_role where admin_id = :admin_id", ImmutableMap.of("admin_id", sysAdmin.getId()));
        }
        sysAdmin.insertOrUpdate();
        for (String roleCode : roleCodes) {
            new SysAdminRole().setAdmin_id(sysAdmin.getId()).setCode(roleCode).insert();
        }
        return new BaseState();
    }

    public BaseState doResetAdminPassword(String id, String password) {
        new SysAdmin(id).setPassword(DigestUtil.md5Hex(password)).update();
        return new BaseState();
    }

    public Map<String, Object> getSysAdmin(String adminId) {
        Map<String, Object> map = baseDao.queryForMap("select * from sys_admin where id = ?", adminId);
        if (!map.isEmpty()) {
            map.put("roles", findSysAdminRole(adminId));
        }
        return map;
    }

    public List<Map<String, Object>> findActivitySysRole() {
        return baseDao.queryForListMap("select * from sys_role where is_activity = 1 order by sort asc", null);
    }

    private List<Map<String, Object>> findSysAdminRole(String adminId) {
        return baseDao.queryForListMap("select r.`code`, r.`name` from sys_admin_role ar, sys_role r where ar.`code` = r.`code` and ar.admin_id =:admin_id order by r.sort asc", ImmutableMap.of("admin_id", adminId));
    }

    /**
     * 根据条件查询系统用户信息
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> findSysAdminByCommond(ResourceCommond commond) {
        StringBuilder sql = new StringBuilder(" select a.*,b.code from sys_admin a left join sys_admin_role b  on b.admin_id = a.id where 1=1 ");
        commond.setPager(true);
        Map<String, Object> param = Maps.newHashMap();
        if (StringUtils.isNotBlank(commond.getRoleCode())) {
            sql.append(" and b.code =:roleCode ");
            param.put("roleCode", commond.getRoleCode());
        }
        if (StringUtils.isNotBlank(commond.getName())) {
            sql.append(" and a.name like :name ");
            param.put("name", "%" + commond.getName() + "%");
        }
        if (StringUtils.isNotBlank(commond.getPhone())) {
            sql.append(" and a.phone_number like :phone ");
            param.put("phone", "%" + commond.getPhone() + "%");
        }

        if (StringUtils.isNotBlank(commond.getState())) {
            sql.append(" and a.is_activity like :state ");
            param.put("state", commond.getState());
        }

        sql.append(" group by a.id ");

        if (StringUtil.isBlank(commond.getOrderBy())) {
            commond.setOrderBy(" add_time desc ");
        }
        commond.setPager(true);
        List<Map<String, Object>> rList = querySupport.find(sql.toString(), param, commond);
        for (Map<String, Object> map : rList) {
            map.put("roles", findSysAdminRole(Convert.toStr(map.get("id"))));
        }
        return rList;
    }
}

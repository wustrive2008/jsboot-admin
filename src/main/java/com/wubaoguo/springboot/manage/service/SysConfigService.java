package com.wubaoguo.springboot.manage.service;

import cn.hutool.core.convert.Convert;
import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.dao.jdbc.dao.BaseDao;
import com.wubaoguo.springboot.entity.SysConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysConfigService {

    @Autowired
    BaseDao baseDao;

    public List<Map<String, Object>> findSysConfig(String admin_id) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("admin_id", admin_id);
        return baseDao.queryForListMap("select * from sys_config where sys_damin_id=:admin_id", param);
    }

    /**
     * 读取指定 管理员用户配置项 ，暂不添加缓存处理
     *
     * @param admin_id
     * @return
     */
    public Map<String, Object> findSysConfigToMap(String admin_id) {
        Map<String, Object> rMap = null;
        List<Map<String, Object>> rList = findSysConfig(admin_id);
        if (rList != null && rList.size() > 0) {
            rMap = new HashMap<>(rList.size());
            for (Map<String, Object> map : rList) {
                rMap.put(Convert.toStr(map.get("item_key")), map.get("item_value"));
            }
        }
        return rMap;
    }

    public void initSysConfigToSession(String userId) {
        Map<String, Object> rMap = findSysConfigToMap(StringUtils.isBlank(userId) ?
                ShiroConstants.getCurrentUser().getId() : userId);
        ShiroConstants.getSession().setAttribute(ShiroConstants.SESSION_SYS_CONFIG, rMap);
    }

    public boolean save(String item_key, String value) {
        String admin_id = ShiroConstants.getCurrentUser().getUserId();
        SysConfig sysConfig = new SysConfig();
        sysConfig.setSys_damin_id(admin_id);
        SysConfig dbSysConfig = sysConfig.setItem_key(item_key).queryForBean();
        if (dbSysConfig != null && StringUtils.isNotBlank(dbSysConfig.getId())) {
            dbSysConfig.setItem_value(value);
            dbSysConfig.update();
        } else {
            sysConfig.setItem_value(value);
            sysConfig.insert();
        }
        return true;
    }
}

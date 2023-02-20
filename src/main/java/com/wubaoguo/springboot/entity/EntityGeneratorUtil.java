package com.wubaoguo.springboot.entity;

import com.wubaoguo.springboot.dao.jdbc.SqlParameter;
import com.wubaoguo.springboot.dao.jdbc.dao.BaseDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;
import java.util.*;

/**
 * Description: 实体类生成工具
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2021-09-29 14:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityGeneratorUtil {

    //数据库名称
    private final static String TABLE_SCHEMA = "tw_gzt";
    //表名
    private final static String TABLE_NAME = "'payroll_factory_type'";
    //位置
    private final static String FILE_PATH = "src/main/java/com/tianwensk/gzt/entity/payroll/";
    //包名
    private final static String PACKAGE_PATH = "com.tianwensk.gzt.entity.payroll";

    private List<Map> freeMakerList;

    @Autowired
    BaseDao baseDao;

    private List<Map> getFreeMakerList() {
        if (freeMakerList == null || freeMakerList.size() == 0) {
            String sql = "select TABLE_NAME,COLUMN_NAME,DATA_TYPE as TYPE,COLUMN_COMMENT from information_schema.columns where TABLE_SCHEMA = '" + TABLE_SCHEMA + "' ";
            if (!StringUtils.isBlank(TABLE_NAME)) {
                sql += "AND TABLE_NAME IN (" + TABLE_NAME + ")";
            }
            List<Map> list = baseDao.queryForList(sql, new SqlParameter());
            for (Map map : list) {
                String column_type = map.get("TYPE").toString().toLowerCase();
                if (StringUtils.startsWith(column_type, "nvarchar") || StringUtils.startsWith(column_type, "char") || StringUtils.startsWith(column_type, "varchar") || StringUtils.startsWith(column_type, "text") || StringUtils.startsWith(column_type, "longtext")) {
                    map.put("COLUMN_TYPE", "String");
                } else if (StringUtils.startsWith(column_type, "int") || StringUtils.startsWith(column_type, "tinyint") || StringUtils.startsWith(column_type, "smallint")) {
                    map.put("COLUMN_TYPE", "Integer");
                    if (StringUtils.startsWith(column_type, "tinyint")) {
                        map.put("COLUMN_TYPE_TINYINT", "Integer");
                    }
                } else if (StringUtils.startsWith(column_type, "bigint")) {
                    map.put("COLUMN_TYPE", "Long");
                } else if (StringUtils.startsWith(column_type, "float") || StringUtils.startsWith(column_type, "double") || StringUtils.startsWith(column_type, "decimal")) {
                    map.put("COLUMN_TYPE", "Double");
                } else if (StringUtils.startsWith(column_type, "datetime") || StringUtils.startsWith(column_type, "date")) {
                    map.put("COLUMN_TYPE", "java.util.Date");
                }
                map.put("COLUMN_LENGTH", !StringUtils.startsWith(column_type, "text") ?
                        column_type.substring(column_type.indexOf("(") + 1, column_type.length() - 1)
                        : new Integer(65535));
            }
            freeMakerList = list;
        }
        return freeMakerList;
    }

    private List<Map> getTableInfoList() {
        List<Map> resultList = new ArrayList<>();
        Map bwlMap = new HashMap();
        List<Map> list = this.getFreeMakerList();
        for (Map map : list) {
            if (!bwlMap.containsKey(map.get("TABLE_NAME"))) {
                resultList.add(map);
                bwlMap.put(map.get("TABLE_NAME"), map);
                List<Map> tempList = new ArrayList<>();
                map.put("columns", tempList);
            } else {
                List<Map> tempList = (List<Map>) (((Map) bwlMap.get(map.get("TABLE_NAME"))).get("columns"));
                tempList.add(map);
            }
        }
        return resultList;
    }

    private Map getPk(String table_name) {
        Map root = new HashMap();
        String getPKSQL = "SELECT COLUMN_NAME " +
                " FROM " +
                "	information_schema.KEY_COLUMN_USAGE" +
                " WHERE " +
                "TABLE_SCHEMA = '" + TABLE_SCHEMA + "' "
                + "AND TABLE_NAME = '" + table_name + "'";
        Map columnMap = baseDao.queryForMap(getPKSQL, new SqlParameter());
        if (columnMap.isEmpty()) {
            // 若查询 没有主键，，直接 写死  主键 名称 id  查询 实体为视图的时候  则使用该方法
            columnMap.put("COLUMN_NAME", "id");
        }
        root.putAll(columnMap);
        return root;
    }

    private Map addPK() {

        Map root = new HashMap();

        String getPKSQL = "SELECT TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME as R_TABLE_NAME, REFERENCED_COLUMN_NAME as R_COLUMN_NAME " +
                " FROM " +
                "	information_schema.KEY_COLUMN_USAGE" +
                " WHERE " +
                " REFERENCED_TABLE_NAME IS NOT NULL" +
                " AND TABLE_SCHEMA = '" + TABLE_SCHEMA + "'";

        root.put("MYFK", baseDao.queryForList(getPKSQL, new SqlParameter()));
        return root;
    }

    private String getTableComments(String tableName) {
        String sql = "SELECT TABLE_COMMENT FROM information_schema.TABLES " +
                "WHERE " +
                "TABLE_SCHEMA = '" + TABLE_SCHEMA + "' "
                + "AND TABLE_NAME = '" + tableName + "' ";
        return baseDao.queryForString(sql, new SqlParameter());
    }

    private void table2Java(Configuration cfg) throws Exception {
        List<Map> list = getTableInfoList();
        Map fkMap = addPK();
        for (Map map : list) {
            Template template = cfg.getTemplate("bean.ftl");
            Map rootMap = new HashMap();
            rootMap.put("ClassName", getClassByTable(map.get("TABLE_NAME").toString()));
            rootMap.put("TABLENAME", map.get("TABLE_NAME").toString().toLowerCase());
            rootMap.put("columns", map.get("columns"));
            rootMap.put("MYFK", fkMap.get("MYFK"));
            rootMap.put("PK", getPk(map.get("TABLE_NAME").toString()));
            rootMap.put("COMMENTS", getTableComments(map.get("TABLE_NAME").toString()));
            rootMap.put("package", PACKAGE_PATH);

            File targetFile = new File(FILE_PATH + getClassByTable(map.get("TABLE_NAME").toString()) + ".java");
            if (!targetFile.exists()) {
                targetFile.getParentFile().mkdirs();
            }
            Writer writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
            template.process(rootMap, writer);

            writer.flush();
            writer.close();
        }
    }

    private String getClassByTable(String table) {
        table = table.toLowerCase();
        if (table.contains("_")) {
            StringBuffer sb = new StringBuffer();
            String[] array = table.split("_");
            for (int i = 0; i < array.length; i++) {
                sb.append(toUpperCaseFirstOne(array[i]));
            }
            return sb.toString();
        }

        return toUpperCaseFirstOne(table);
    }

    private String toUpperCaseFirstOne(String oneFiled) {
        if (oneFiled.length() > 1) {
            oneFiled = ("" + oneFiled.charAt(0)).toUpperCase() + oneFiled.substring(1);
        } else if (oneFiled.length() > 0) {
            oneFiled = ("" + oneFiled.charAt(0)).toUpperCase();
        }

        return oneFiled;
    }

    private Configuration getConfiguration(ClassLoader context, String templatePath) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        try {
            cfg.setClassLoaderForTemplateLoading(context, templatePath);
            cfg.setLocale(Locale.CHINA);
            cfg.setDefaultEncoding("utf-8");
            cfg.setClassicCompatible(true);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return cfg;
    }

    @Test
    public void startGenertor() {
        System.out.println("生成数据实体 开始....");
        Configuration cfg = getConfiguration(Thread.currentThread().getContextClassLoader(), "template");
        try {
            table2Java(cfg);
            System.out.println("数据库实体生成完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

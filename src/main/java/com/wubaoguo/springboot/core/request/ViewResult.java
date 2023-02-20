package com.wubaoguo.springboot.core.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.common.collect.Lists;
import com.wubaoguo.springboot.core.filter.ThreadContentFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewResult {

    Logger logger = LoggerFactory.getLogger(ViewResult.class);

    /**
     * 成功、失败 的附加提示
     */
    String message;

    /**
     * 操作状态 1：成功，其他：失败 根据业务定义失败原因
     */
    int state;


    /**
     * 结果数据
     */
    Map<String, ?> data;

    /**
     * 列表行数据
     */
    List<? extends Object> rows;

    /**
     * 创建一个实例
     */
    public static ViewResult newInstance() {
        return new ViewResult();
    }

    private ViewResult() {
        state = StateMap.S_SUCCESS;
        message = "操作成功";
    }

    private ValueFilter filter = (object, name, value) -> {
        if (name.equals("add_time") && null != value) {
            Date date = new Date((Long) value * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return value;
    };

    public String json() {
        Map<String, Object> result = new HashMap<>(3);
        Map<String, Object> content = new HashMap<>(2);
        result.put("message", message);
        result.put("state", state);

        if (data != null) {
            content.put("data", data);
        } else {
            content.put("data", new Object());
        }
        if (rows != null) {
            content.put("rows", rows);
        } else {
            content.put("rows", Lists.newArrayList());
        }

        result.put("content", content);
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        final String out = JSON.toJSONString(result, filter, SerializerFeature.WriteDateUseDateFormat);

        HttpServletRequest request = ThreadContentFilter.request();
        String jsoncallback = (String) request.getParameter("jsoncallback");

        logger.debug("result: {}", out);

        if (!StringUtils.isEmpty(jsoncallback)) {
            return jsoncallback + "(" + out + ")";
        } else {
            return out;
        }
    }


    public Map<String, ?> getData() {
        return data;
    }

    public ViewResult setData(Map<String, ?> data) {
        this.data = data;
        return this;
    }

    public List<? extends Object> getRows() {
        return rows;
    }

    public ViewResult setRows(List<? extends Object> rows) {
        this.rows = rows;
        return this;
    }

    public ViewResult success() {
        return state(1, "操作成功");
    }

    public ViewResult success(String message) {
        return state(1, message);
    }

    public ViewResult fail() {
        return fail("操作失败");
    }

    public ViewResult fail(String message) {
        return state(-1, message);
    }

    public ViewResult state(int state) {
        this.state = state;
        this.message = StateMap.get(state);
        return this;
    }

    public ViewResult state(BaseState baseState) {
        this.state = baseState.getState();
        this.message = baseState.getMessage();
        return this;
    }

    public ViewResult state(int state, String message) {
        this.message = message;
        this.state = state;
        return this;
    }

    public ViewResult fail(Exception e) {
        state(500, e.getMessage());
        return this;
    }

}

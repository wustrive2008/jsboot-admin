package com.wubaoguo.springboot.core.dao.jdbc;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public abstract class BaseCommond implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orderBy;// 排序字段
    private String orderTypeStr;
    private boolean isPager = false;
    private String searchId;
    private String selector;
    private String orderByClause;

    private Pager pager;

    public BaseCommond() {
        this.pager = new Pager();
    }

    public Integer getPageNumber() {
        return pager.getPageNumber();
    }

    public void setPageNumber(Integer pageNumber) {
        pager.setPageNumber(pageNumber);
    }

    public Integer getPageSize() {
        return pager.getPageSize();
    }

    public void setPageSize(Integer pageSize) {
        pager.setPageSize(pageSize);
    }

    public Integer getTotalCount() {
        return pager.getTotalCount();
    }

    public void setTotalCount(Integer totalCount) {
        pager.setTotalCount(totalCount);
    }

    public Integer getPageCount() {
        return pager.getPageCount();
    }

    public void setPageCount(Integer pageCount) {
        pager.setPageCount(pageCount);
    }


    public int getLimitStart() {
        if (isPager) {
            return pager.getPageStart();
        }
        return -1;
    }


    public int getLimitEnd() {
        if (isPager) {
            return pager.getPageSize();
        }
        return -1;
    }

    public boolean isPager() {
        return isPager;
    }

    public void setPager(boolean isPager) {
        this.isPager = isPager;
    }

    public abstract List<String> getLikes();

}

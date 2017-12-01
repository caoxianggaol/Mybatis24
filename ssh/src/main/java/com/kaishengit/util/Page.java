package com.kaishengit.util;

import java.util.List;

/**
 * 分页工具类
 * Created by xiaogao on 2017/12/1.
 */
public class Page<T> {

    private Integer start;
    private Integer pageSize;
    private Integer total;
    private Integer totalPageSize;
    private Integer pageNum;
    private List<T> items;

    public Page() {}

    public Page(int totalSize,Integer pageSize,Integer pageNum) {
        total = totalSize;

        //计算总页数 totalSize总条数  pageSize一页显示几条
        totalPageSize = totalSize / pageSize;
        if(totalSize % pageSize > 0) {
            totalPageSize++;
        }

        if(pageNum < 1) {
            pageNum = 1;
        }
        if(pageNum > totalPageSize) {
            pageNum = totalPageSize;
        }
        if(pageNum == 0) {
            pageNum = 1;
        }

        //计算起始行数
        //1 0 10
        //2 10 10
        start = (pageNum - 1) * pageSize;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(Integer totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

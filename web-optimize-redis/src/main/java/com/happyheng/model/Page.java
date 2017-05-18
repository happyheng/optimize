package com.happyheng.model;

/**
 *
 * Created by happyheng on 17/5/18.
 */
public class Page {

    /**
     * 页数
     */
    private int pageIndex;

    /**
     * 每页的大小
     */
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}

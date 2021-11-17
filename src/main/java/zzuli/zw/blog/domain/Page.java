package zzuli.zw.blog.domain;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private int page;  //当前页的页码
    private int records;  //总的记录数（通过从数据库中查询得到）
    private int limit;     //每个页面显示的记录数（自己定义）
    private int previousPage;  //上一页的页码
    private int nextPage;      //下一页的页码
    private List<T> pageBean;   //查询到的实体类的集合
    private int pages;     //总的页数（只有get方法，没有set方法，通过计算得到）

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<T> getPageBean() {
        return pageBean;
    }

    public void setPageBean(List<T> pageBean) {
        this.pageBean = pageBean;
    }

    public int getPages() {
        int temp = records/limit;
        return records % limit == 0 ? temp : temp+1;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", records=" + records +
                ", limit=" + limit +
                ", previousPage=" + previousPage +
                ", nextPage=" + nextPage +
                ", pageBean=" + pageBean +
                ", pages=" + pages +
                '}';
    }
}

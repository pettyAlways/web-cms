package org.yingzuidou.cms.cmsweb.core.paging;

/**
 * 分页实体类
 *
 * @author 鹰嘴豆
 * @date 2018/9/16     
 */
public class PageInfo {

    /**
     *  当前页数
     */
    private int page;

    /**
     * 页的大小(默认显示10条)
     */
    private int pageSize = 10;

    /**
     * 总数
     */
    private int counts;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}

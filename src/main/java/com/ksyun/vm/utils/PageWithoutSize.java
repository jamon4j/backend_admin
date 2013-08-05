package com.ksyun.vm.utils;

import java.io.Serializable;
import java.util.List;

public class PageWithoutSize implements Serializable {

    private static final long serialVersionUID = -2940983877096774934L;

    private List<?> data;

    private Integer pageIndex; // 当前页

    public PageWithoutSize(Integer pageIndex) {
        if (pageIndex > 1)
            this.pageIndex = pageIndex;
        else
            this.pageIndex = 1;
       
    }
    
	public Integer getPageIndex() {
        return pageIndex;
    }
	
    public List<?>  getData() {
        return data;
    }

    public void setData(List<?>  data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Page [data=" + data + ", pageIndex=" + pageIndex + "]";
    }
}
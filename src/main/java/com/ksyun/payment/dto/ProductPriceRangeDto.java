package com.ksyun.payment.dto;

import java.math.BigDecimal;

/**
 * 产品条目价格区间
 * @author ZhangYanchun
 * @date   2013-08-27
 */
public class ProductPriceRangeDto {
	//主键
    private Integer id;
    //价格区间开始   >=
    private Integer rangeBegin;
    //价格区间结束  <
    private Integer rangeEnd;
    //此价格区间价格
    private BigDecimal price;
    //价格区间标示
    private String itemNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRangeBegin() {
        return rangeBegin;
    }

    public void setRangeBegin(Integer rangeBegin) {
        this.rangeBegin = rangeBegin;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }
}
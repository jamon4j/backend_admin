package com.ksyun.payment.dto;

/**
 * 产品条目
 * @author ZhangYanchun
 * @date   2013-08-27
 */
public class ProductItemDto {
	//醒目边牧
    private String itemNo;
    //状态   1 可用    0 停用
    private Short status;
    //单位
    private String unit;
    //计费数量，每个月  按照 30天
    private Integer unitCount;
    //项目名称
    private String itemName;
    //产品类型 1 kvm  2 ks3
    private Byte productType;

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Byte getProductType() {
        return productType;
    }

    public void setProductType(Byte productType) {
        this.productType = productType;
    }
}
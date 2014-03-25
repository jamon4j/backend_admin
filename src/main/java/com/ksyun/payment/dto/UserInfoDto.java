package com.ksyun.payment.dto;

import java.util.Date;

/**
 * 用户信息model（建议以后合并user_info  user_account）
 * @author ZhangYanchun
 * @Date   2014-01-02
 */
public class UserInfoDto {
	
    private Long userId;

    private String name;

    private Integer type;

    private String detail;

    private Integer isActivated;

    private String mobile;

    private String email;

    private Date regTime;
    
    private short userType;
    
    private String userFrom;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public String getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(String userFrom) {
		this.userFrom = userFrom;
	}
	
	
    
    
}
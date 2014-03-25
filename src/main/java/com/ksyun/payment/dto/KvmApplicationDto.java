package com.ksyun.payment.dto;

import java.util.Date;

/**
 * kvm申请表
 * @author ZhangYanchun
 *
 */
public class KvmApplicationDto {
	
	
	private Long id;

	//用户ID
    private long userId;

    //联系电话
    private String phone;

    //联系人
    private String uname;

    //用户类型  1  公司  2 个人
    private short utype;

    //公司名称
    private String cname;
    
    //申请人职位
    private String uposition;

    //通讯
    private String im;

    //公司地址
    private String caddress;

    //业务类型
    private String bussinessType;

    //cpu核数 
    private int cpu;

    //内存大小
    private double mem;
    
    //数据盘
    private int ebs;

    //带宽
    private int net;

    //操作系统
    private String ops;
    
    //操作系统显示名称
    private String opsName;
    
    private short status;
    
    private String email;
    
    private Date applicationTime;
    
    private String referee;
    
    private String website;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public short getUtype() {
		return utype;
	}

	public void setUtype(short utype) {
		this.utype = utype;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getUposition() {
		return uposition;
	}

	public void setUposition(String uposition) {
		this.uposition = uposition;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public double getMem() {
		return mem;
	}

	public void setMem(double mem) {
		this.mem = mem;
	}

	public int getEbs() {
		return ebs;
	}

	public void setEbs(int ebs) {
		this.ebs = ebs;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}

	public String getOps() {
		return ops;
	}

	public void setOps(String ops) {
		this.ops = ops;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOpsName() {
		return opsName;
	}

	public void setOpsName(String opsName) {
		this.opsName = opsName;
	}
	
	
}

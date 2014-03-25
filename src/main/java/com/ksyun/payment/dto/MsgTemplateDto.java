package com.ksyun.payment.dto;

import java.util.Date;

/**
 * 邮件及短信模板DTO
 * @author ZhangYanchun
 * @date   2013-12-06
 */
public class MsgTemplateDto {
	
	private String bizType;
	
	private String msgType;
	
	private String comment;
	
	private String template;
	
	private Date lastModified;

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	

}

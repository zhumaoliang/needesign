package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;


/**
 * 返回广告Entity
 * @author tank
 *
 */
public class ResultAdverisement  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String adurl;//广告图片地址
	private String linkurl;//点击地址
	private String id;//PK
	private String companyname;//公司名称
	private String companylocation;//司公地址
	private String companyintroduction;//公司简介
	private String status;//态状  0 停用 1 正常
	private String isusead;//告广是否正在使用中   0 未  1 正在使用
	private String userid;//用户id
	public String getAdurl() {
		return adurl;
	}
	public void setAdurl(String adurl) {
		this.adurl = adurl;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanylocation() {
		return companylocation;
	}
	public void setCompanylocation(String companylocation) {
		this.companylocation = companylocation;
	}
	public String getCompanyintroduction() {
		return companyintroduction;
	}
	public void setCompanyintroduction(String companyintroduction) {
		this.companyintroduction = companyintroduction;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsusead() {
		return isusead;
	}
	public void setIsusead(String isusead) {
		this.isusead = isusead;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	

	
	
	
	
}

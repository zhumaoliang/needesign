package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;


/**
 * 推荐作品Entity
 * @author tank
 *
 */
public class IsRecommendVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userid;//用户id
	private String headurl;//头像url
	private String username;//用户名称
	private String goodfield;
	private Integer id;//PK
	private String name;//图片名称
	private String url;//图片URL
	private String state;//数据状态
	private int pointnum;//点赞次数
	private String ispoint;//是否被点赞
	
	
	
	
	
	public String getIspoint() {
		return ispoint;
	}
	public void setIspoint(String ispoint) {
		this.ispoint = ispoint;
	}
	public int getPointnum() {
		return pointnum;
	}
	public void setPointnum(int pointnum) {
		this.pointnum = pointnum;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGoodfield() {
		return goodfield;
	}
	public void setGoodfield(String goodfield) {
		this.goodfield = goodfield;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
	
}

package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;

/**
 * 推荐作品Entity
 * 
 * @author tank
 * 
 */
public class RecommendVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userid;// 用户id
	private String headurl;// 头像url
	private String username;// 用户名称
	private String goodfield;
	private String workid;// PK
	private String workey;// 图片名称
	private Integer votenum;//投票数量
	private String tags;//标签
	private String videoimgkey;//视频缩略图
	
	private String choiceshow;//是否显示在精选
	private String squareshow;//是否显示在广场
	
	
	
	
	
	
	public String getChoiceshow() {
		return choiceshow;
	}
	public void setChoiceshow(String choiceshow) {
		this.choiceshow = choiceshow;
	}
	public String getSquareshow() {
		return squareshow;
	}
	public void setSquareshow(String squareshow) {
		this.squareshow = squareshow;
	}
	public String getVideoimgkey() {
		return videoimgkey;
	}
	public void setVideoimgkey(String videoimgkey) {
		this.videoimgkey = videoimgkey;
	}
	public void setTags(String tags) {
		this.tags = tags;
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
	
	public String getWorkid() {
		return workid;
	}
	public void setWorkid(String workid) {
		this.workid = workid;
	}
	public String getWorkey() {
		return workey;
	}
	public void setWorkey(String workey) {
		this.workey = workey;
	}
	
	public Integer getVotenum() {
		return votenum;
	}
	public void setVotenum(Integer votenum) {
		this.votenum = votenum;
	}
	public String getTags() {
		return tags;
	}
	public void setTabs(String tags) {
		this.tags = tags;
	}
	

}

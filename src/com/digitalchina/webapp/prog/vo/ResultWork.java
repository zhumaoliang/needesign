package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.util.List;

public class ResultWork implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;// 用户id
	private String headurl;// 头像url
	private String username;// 用户名称
	private String goodfield;
	private String workid;// 作品id
	private Integer voteNum;// 被投票数量
	private String isVoted;// 是否被用户投票
	private String tags;// 标签
	private List<Works> works;// 作品url集合
	private String isFollowed;//是否被关注   0未关注  1 已关注

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

	public Integer getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}

	

	public List<Works> getWorks() {
		return works;
	}

	public void setWorks(List<Works> works) {
		this.works = works;
	}

	public String getIsVoted() {
		return isVoted;
	}

	public void setIsVoted(String isVoted) {
		this.isVoted = isVoted;
	}

	

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(String isFollowed) {
		this.isFollowed = isFollowed;
	}

}

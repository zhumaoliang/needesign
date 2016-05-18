package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 作品Entity
 * @author tank
 *
 */
public class WorkVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userid;//用户id
	private String id;//PK
	private String workey;//图片key集合
	private int votenum;//已被投票次数
	private int cnum;//已被评论次数
	private String state;//数据状态
	private Date createtime;//创建时间
	private Date updatetime;//修改时间
	private String tags;//图片标签
	private String videoimgkey;//视屏截图
	
	private String choiceshow;//是否显示在精选 0 否 1 是
	private String squareshow;//是否显示在广场  0 否 1 是

	
	
	
	
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
	public String getTags() {
		return tags;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkey() {
		return workey;
	}
	public void setWorkey(String workey) {
		this.workey = workey;
	}
	public int getVotenum() {
		return votenum;
	}
	public void setVotenum(int votenum) {
		this.votenum = votenum;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	
	
	
}

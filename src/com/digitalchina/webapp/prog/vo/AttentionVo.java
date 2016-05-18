package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 关注Entity
 * @author tank
 *
 */
public class AttentionVo implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	
	private int id;//PK
	private String userid;//关注id
	private String attention_userid;//被关注id
	private String state;//1 已关注  0 取消关注
	private Timestamp attention_time;//关注日期
	private Timestamp update_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getAttention_userid() {
		return attention_userid;
	}
	public void setAttention_userid(String attention_userid) {
		this.attention_userid = attention_userid;
	}
	public Timestamp getAttention_time() {
		return attention_time;
	}
	public void setAttention_time(Timestamp attention_time) {
		this.attention_time = attention_time;
	}
	
}

package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;

public class ResultNotify implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// id
	private String title;// 标题
	private String message;// 信息
	private String timestamp;// 时间戳
	private String userid;// 推送userid
	private String notify_type;
    private String dynamicid;
private String state;
	private String host_id;
	private String host_name;
    
    

	
	


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDynamicid() {
		return dynamicid;
	}

	public void setDynamicid(String dynamicid) {
		this.dynamicid = dynamicid;
	}

	public String getHost_id() {
		return host_id;
	}

	public void setHost_id(String host_id) {
		this.host_id = host_id;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}

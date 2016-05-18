package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class NotifyVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// id
	private String title;// 标题
	private String message;// 信息
	private String timestamp;// 时间戳
	private String userid;// 推送userid
	private String state;
	private String notify_type;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String dynamicid;

	public String getDynamicid() {
		return dynamicid;
	}

	public void setDynamicid(String dynamicid) {
		this.dynamicid = dynamicid;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

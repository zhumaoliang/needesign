package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 发布需求Entity
 * @author tank
 *
 */
public class RequirementVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//PK
	private String category;//需求类别
	private String background;//需求背景
	private String description;//需求描述
	private Timestamp createtime;//创建时间
	private Timestamp updatetime;//修改时间
	private String userid;//用户id
	private String state;//是否有效 0 无效 1 有效
	private String timestamp;//创建时间戳
	private String type;//需求类型 0 发布需求 1 销售需求 2 动态
	
	
	
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
	
	
	
	
	
}

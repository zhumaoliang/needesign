package com.digitalchina.webapp.admin.vo;

import java.io.Serializable;
import java.sql.Timestamp;

//admin Entity
public class AdminVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;//PK
	private String name;//账号名称
	private String pwd;//账号密码
	private String status;//账号状态 0 关闭状态 1 激活状态
	private Timestamp createtime;//创建时间
	private Timestamp updatetime;//账户信息修改时间
	private String avatar;//管理员头像
	private String email;//管理员邮箱
	private String phone;//管理员电话
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	

}

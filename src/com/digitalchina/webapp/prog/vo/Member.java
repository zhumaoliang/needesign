package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户Entity
 * @author tank
 *
 */
public class Member implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userid;//用户id
	private String username;//用户名称
	private String realname;//真是名称
	private String email;//用户邮箱
	private String phone;//用户电话
	private String contact;//联系方式
	private String question;//注册时设置问题(预留)
	private String answer;//答案
	private String password;//密码
	private String sex;//性别
	private String usertype;//角色
	private String state;//用户状态
	private Date registtime;//注册时间
	private Date updatetime;//更改信息事件
	private String avatar;//头像地址
	private String  work_link;//作品链接
	private String goodfield;//擅长领域
	private String chinnalid;//聊天备用
	private String validatacode;//重置密码唯一标示
	private Timestamp outdate;
	private String device_id;//设备型号
	private String device_sys;//作操系统
	private String um_device_token;//um_device_token
	private String rongcloud_token;//融云token
	private String qiniu_token;//七牛token
	private String user_token;//用户token
	private String userprofile;//用户自我描述
	private String headurl;// 返回头像url
	
	
	
	
	
	
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getUserprofile() {
		return userprofile;
	}
	public void setUserprofile(String userprofile) {
		this.userprofile = userprofile;
	}
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public String getQiniu_token() {
		return qiniu_token;
	}
	public void setQiniu_token(String qiniu_token) {
		this.qiniu_token = qiniu_token;
	}
	public String getRongcloud_token() {
		return rongcloud_token;
	}
	public void setRongcloud_token(String rongcloud_token) {
		this.rongcloud_token = rongcloud_token;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getRegisttime() {
		return registtime;
	}
	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getWork_link() {
		return work_link;
	}
	public void setWork_link(String work_link) {
		this.work_link = work_link;
	}
	public String getChinnalid() {
		return chinnalid;
	}
	public void setChinnalid(String chinnalid) {
		this.chinnalid = chinnalid;
	}
	public String getValidatacode() {
		return validatacode;
	}
	public void setValidatacode(String validatacode) {
		this.validatacode = validatacode;
	}
	public Timestamp getOutdate() {
		return outdate;
	}
	public void setOutdate(Timestamp outdate) {
		this.outdate = outdate;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_sys() {
		return device_sys;
	}
	public void setDevice_sys(String device_sys) {
		this.device_sys = device_sys;
	}
	public String getUm_device_token() {
		return um_device_token;
	}
	public void setUm_device_token(String um_device_token) {
		this.um_device_token = um_device_token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getGoodfield() {
		return goodfield;
	}
	public void setGoodfield(String goodfield) {
		this.goodfield = goodfield;
	}

	
	
	
	
}

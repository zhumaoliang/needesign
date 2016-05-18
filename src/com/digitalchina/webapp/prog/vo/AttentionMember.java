package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;


/**
 * 关注返回参数
 * @author tank
 *
 */
public class AttentionMember  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userid;//用户id
	private String username;//用户名称
	private String headurl;//头像地址
	private String isattention;//是否关注  1 已关注 0 未关注
	private String userProfile;//用户自我描述
	
	
	
	
	
	
	
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getIsattention() {
		return isattention;
	}
	public void setIsattention(String isattention) {
		this.isattention = isattention;
	}
	
}

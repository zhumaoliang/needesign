package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 短信验证码Entity
 * @author tank
 *
 */
public class VerifyCodeVo implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	
	private int id;//PK
	private String phone;//手机号
	private String code;//验证码
	private String type;//0 注册验证码 1 其他
	private Timestamp createtime;//发送日期
	private String isuse;//是否已使用0 未使用  1 已使用
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getIsuse() {
		return isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	
	
	
	
	
	
	
}

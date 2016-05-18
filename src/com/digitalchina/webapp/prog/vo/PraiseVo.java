package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 点赞Entity
 * @author tank
 *
 */
public class PraiseVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private int id;//PK
	private String userid;//用户id
	private String dynamicid;//图片id
	private Timestamp createtime;//创建日期
	private Timestamp updatetime;//更改日期
	private String state;//状态 0  取消点赞 1 点赞
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDynamicid() {
		return dynamicid;
	}
	public void setDynamicid(String dynamicid) {
		this.dynamicid = dynamicid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	
	
	
}

package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 投票Entity
 * @author tank
 *
 */
public class WorkVoteVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private int id;//pk
	private String userid;//投票用户id
	private String workid;//作品id
	private Timestamp createtime;//创建日期
	private Timestamp updatetime;//更改日期
	private String state;//是否投票  0 否 1 是
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
	
	public String getWorkid() {
		return workid;
	}
	public void setWorkid(String workid) {
		this.workid = workid;
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

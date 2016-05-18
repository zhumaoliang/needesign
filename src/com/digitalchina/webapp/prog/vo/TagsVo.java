package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 标签Entity
 * @author tank
 *
 */
public class TagsVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//PK
	private String parentchtype;//中文父类型
	private String chirldchtype;//中文 子类型
	private String parententype;//英 文父类型
	private String chirldentype;//英文子类型
	private String state;//数据转态0 停用 1 正常
	private Timestamp createtime;//建创时间
	private Timestamp updatetime;//修改时间
	private String operator;//添加者
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentchtype() {
		return parentchtype;
	}
	public void setParentchtype(String parentchtype) {
		this.parentchtype = parentchtype;
	}
	public String getChirldchtype() {
		return chirldchtype;
	}
	public void setChirldchtype(String chirldchtype) {
		this.chirldchtype = chirldchtype;
	}
	public String getParententype() {
		return parententype;
	}
	public void setParententype(String parententype) {
		this.parententype = parententype;
	}
	public String getChirldentype() {
		return chirldentype;
	}
	public void setChirldentype(String chirldentype) {
		this.chirldentype = chirldentype;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	
	
	

}

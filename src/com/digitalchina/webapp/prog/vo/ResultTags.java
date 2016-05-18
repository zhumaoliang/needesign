package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.util.List;


/**
 * 返回标签Entity
 * @author tank
 *
 */
public class ResultTags implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private List<String> parenttype;//父类型
	private List<String> chirldtype;// 子类型
	
	
	public List<String> getParenttype() {
		return parenttype;
	}
	public void setParenttype(List<String> parenttype) {
		this.parenttype = parenttype;
	}
	public List<String> getChirldtype() {
		return chirldtype;
	}
	public void setChirldtype(List<String> chirldtype) {
		this.chirldtype = chirldtype;
	}
	
	
	

}

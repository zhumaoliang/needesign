package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;

/**
 * 
 * 图片的缩略Entity
 * @author tank
 *
 */
public class Works implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String originalWork;// 原图片或者视屏
	private String type;// 0 图片 1 是视屏
	private String thumbnailWork;//缩略图片
	public String getOriginalWork() {
		return originalWork;
	}
	public void setOriginalWork(String originalWork) {
		this.originalWork = originalWork;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThumbnailWork() {
		return thumbnailWork;
	}
	public void setThumbnailWork(String thumbnailWork) {
		this.thumbnailWork = thumbnailWork;
	}
	
	
	
}

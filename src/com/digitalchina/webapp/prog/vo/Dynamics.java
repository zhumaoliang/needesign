package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;

/**
 * 
 * 图片的缩略Entity
 * @author tank
 *
 */
public class Dynamics implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String originalUrl;// 原图片或者视屏
	private String type;// 0 图片 1 是视屏
	private String thumbnailUrl;//缩略图片
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	
	
	
}

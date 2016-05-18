package com.digitalchina.webapp.prog.vo;
/**
 * 七牛Entity
 * @author tank
 *
 */
public class QiNiuVo {
	private static final long serialVersionUID = 1L;
	public long fsize;
	public String key;
	public String hash;
	public int width;
	public int height;

	public long getFsize() {
		return fsize;
	}

	public void setFsize(long fsize) {
		this.fsize = fsize;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}

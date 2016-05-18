package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 动态Entity
 * @author tank
 *
 */
public class DynamicVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//PK
//	private String title;//标题
	private String content;//内容
	private String imgurl;//图片URL
	private String videoimgurl;//视屏缩略图
	private int praisnum;//点赞总次数
	private int commentnum;//被评论次数
	private String state;//数据状态
	private Timestamp createtime;//创建时间
	private Timestamp updatetime;//修改时间
	private String userid;//用户id
	private String isfacebook;//是否同步到facebook  0 否   1是
	private String isweixin;//是否同步到微信朋友圈  0 否   1是
	private String location;//发布所在地址
	private String type;//0 需求  1 销售 2 动态
	private String timestamp;//创建时间戳
	
	
	
	
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVideoimgurl() {
		return videoimgurl;
	}
	public void setVideoimgurl(String videoimgurl) {
		this.videoimgurl = videoimgurl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public int getPraisnum() {
		return praisnum;
	}
	public void setPraisnum(int praisnum) {
		this.praisnum = praisnum;
	}
	public int getCommentnum() {
		return commentnum;
	}
	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}
	public String getIsfacebook() {
		return isfacebook;
	}
	public void setIsfacebook(String isfacebook) {
		this.isfacebook = isfacebook;
	}
	public String getIsweixin() {
		return isweixin;
	}
	public void setIsweixin(String isweixin) {
		this.isweixin = isweixin;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}

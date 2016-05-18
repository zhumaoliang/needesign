package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;


/**
 * 动态Entity
 * @author tank
 *
 */
public class IsDynamicVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//PK
	private String contents;//内容
	private String imgurl;//图片URL
	private int totalLikes;//点赞总次数
	private int totalComments;//点评论次数
	private String state;//数据状态
	private String createtime;//创建时间
	private String headurl;// 头像url
	private String username;// 用户名称
	private String userid;//用户id
	private String isLiked;// 是否被点赞
	private String location;//发布动态地址
	private String videoimgurl;//视屏缩略图url
	private String isFollowed;//是否关注
	public String postCategory;
	public String postBackground;
	public String postDetails;
	private String type;
	private String sell;
	private String price;
	private String photos;
	
	
	
	
	
	
	public String getSell() {
		return sell;
	}
	public void setSell(String sell) {
		this.sell = sell;
	}
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}
	public String getPostBackground() {
		return postBackground;
	}
	public void setPostBackground(String postBackground) {
		this.postBackground = postBackground;
	}
	public String getPostDetails() {
		return postDetails;
	}
	public void setPostDetails(String postDetails) {
		this.postDetails = postDetails;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsFollowed() {
		return isFollowed;
	}
	public void setIsFollowed(String isFollowed) {
		this.isFollowed = isFollowed;
	}
	public String getVideoimgurl() {
		return videoimgurl;
	}
	public void setVideoimgurl(String videoimgurl) {
		this.videoimgurl = videoimgurl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	
	
	public int getTotalLikes() {
		return totalLikes;
	}
	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}
	public int getTotalComments() {
		return totalComments;
	}
	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}
	public String getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(String isLiked) {
		this.isLiked = isLiked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
	
}

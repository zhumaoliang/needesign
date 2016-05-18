package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;


/**
 * 评论Entity
 * @author tank
 *
 */
public class CommentVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//PK
	private String contents;//内容
	private String imgurl;//图片URL
	private String dynamicid;//所属动态id
	private String commentid;//评论从属  跟评论id对应 为null时候  是 评论   其它是回复
	private String createdate;//评论时间
	private String touserid;//@对象id 
	private String fromuserid;//评论者id
	private String fromuser;//评论者name
	private String touser;//@对象名称
	private Long createtime;
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	public String getDynamicid() {
		return dynamicid;
	}
	public void setDynamicid(String dynamicid) {
		this.dynamicid = dynamicid;
	}
	public String getCommentid() {
		return commentid;
	}
	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	public String getTouserid() {
		return touserid;
	}
	public void setTouserid(String touserid) {
		this.touserid = touserid;
	}
	public String getFromuserid() {
		return fromuserid;
	}
	public void setFromuserid(String fromuserid) {
		this.fromuserid = fromuserid;
	}
	public String getFromuser() {
		return fromuser;
	}
	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	
	
}

package com.digitalchina.webapp.admin.utils;
/**
 * 后台错误集合码
 * @author tank
 *
 */
public class ErrorConstants {

	public static String  LOGINNAME_CODE="100001";
	public  static String LOGINNAME_MSG="*账号不存在";
	public static String  PWD_CODE="100002";
	public  static String PWD_MSG="*密码错误";
	public static String  LOGIN_CODE="100003";
	public  static String LOGIN_MSG="手机号不存在,请先注册";
	public static String  PSW_CODE="100004";
	public  static String PSW_MSG="密码错误";
	public static String  EMAIL_CODE="100005";
	public  static String EMAIL_MSG="手机号输入有误";
	public  static String SENDEMAILFAULT_CODE="100007";
	public  static String SENDEMAILFAULT_MSG="发送重置邮件失败";
	public  static String SENDEMAILSUC_CODE="100006";
	public  static String SENDEMAILSUC_MSG="发送重置邮件成功,请登录邮箱重置密码";
	public  static String IMG_CODE="100009";
	public  static String IMG_MSG="图片类型不符合jpg、png、jpeg、gif或者图片超出大小";
	public  static String IMGTYPE_CODE="100010";
	public  static String IMGTYPE_MSG="图片类型不符合jpg、png、jpeg、gif";
	public  static String IMGSIZE_CODE="100011";
	public  static String IMGSIZE_MSG="图片超出大小";
	public  static String USER_CODE="100012";
	public  static String USER_MSG="用户不存在";
	
	public  static String UPSW_CODE="100013";
	public  static String UPSW_MSG="修改密码失败";
	public  static String QINIU_CODE="100014";
	public  static String QINIU__MSG="获取七牛上传token失败";
	public  static String SMS_CODE="100015";
	public  static String SMS__MSG="手机获取验证码错误";
	public  static String VCODE_CODE="100016";
	public  static String VCODE__MSG="验证码错误";
	public  static String OUT_CODE="100008";
	public  static String OUT_MSG="验证码失效";
	public  static String ISUSE_CODE="100017";
	public  static String ISUSE_MSG="验证码已使用";
	public  static String VOTE_CODE="100018";
	public  static String VOTE_MSG="已投票";
	public  static String RONGCLOUD_CODE="100019";
	public  static String RONGCLOUD__MSG="获取融云token失败";
	public  static String UPPSW_CODE="100020";
	public  static String UPPSW__MSG="原密码错误";
	

}

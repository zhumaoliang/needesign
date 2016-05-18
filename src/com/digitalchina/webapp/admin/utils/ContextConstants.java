package com.digitalchina.webapp.admin.utils;
/**
 * 管理后台服务公共参数
 * @author tank
 *
 */
public class ContextConstants {

	//用户状态--正常
	public static String USER_STATE_NORMAL="1";
	//用户状态--停滞
	public static String USER_STATE_STOP="0";
	//管理员只含有汉字、数字、字母、下划线不能以下划线开头和结尾
	public static String regx="^(?!_)(?!.*?_$)[a-zA-Z0-9_u4e00-u9fa5]+$";
    
	//管理员参数
	public static String ADMIN_SESSION="adminsession";
	//管理有登录页面
	public static String ADMIN_LOGIN="http://localhost:80/needesign/admin/login.jsp";
	
}

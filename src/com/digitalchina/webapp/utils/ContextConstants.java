package com.digitalchina.webapp.utils;
/**
 * 公共参数类
 * @author tank
 *
 */
public class ContextConstants {

	//用户状态--正常
	public static String USER_STATE_NORMAL="1";
	//用户状态--停滞
	public static String USER_STATE_STOP="0";
	//邮箱正则表达式
	public static String regx="^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,2,5,6,7,8,9]\\d{8}$)|(147\\d{8})$)|(17[0,1,2,3,5,6,7,8,9]\\d{8}$)" ;
    //找回密码主题
	public static String email_subject="Needesign服务-密码找回";
	//最多获取注册验证码次数
	public static Integer MAX_REGISTER_CODE=5;
	//短信发送apikey
	public static String apikey = "d0b30e1e398f41c6c0eda61fcdb949a6";
	//中国手机前缀号
	public static String AREACODE = "+86";
	//中国手机前缀号+
	public static String PHONE_LOG = "+";
	//视屏格式
	public static String VEDIO_TYPR="avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob";
	//图片比例
	public static String IMAGE_SIZE="?imageView2/0/w/1024/h/768";
	//系统角色
	public static String RONGCLOUD_KEY="pkfcgjstfdh98";
	public static String RONGCLOUD_SECRET="Mrm9U9eLm9xqm";
	public static String RONGCLOUD_ID="00000000";
	public static String RONGCLOUD_NAME="Fashine";
	public static String RONGCLOUD_HEADURL="http://7xpv7y.com1.z0.glb.clouddn.com/4da683514f144ab396eaee15751d9330.jpg";
    public static String RONGCLOUD_CH_MESSAGE="欢迎加入Fashine，与我们一起开启高端时尚的设计之旅。";
    public static String RONGCLOUD_EN_MESSAGE="Welcome to start your design tour with Fashine!";
    //友盟推送系统消息
    public static String UM_APPKEY = "56f0d251e0f55ac314000891";
    public static String UM_APPMASTERSECRET = "2vd2sk0qvoylfmviiqztf09nlol7xzto";
	
}

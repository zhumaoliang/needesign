package com.digitalchina.webapp.prog.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.webapp.RongCloud.ApiHttpClient;
import com.digitalchina.webapp.RongCloud.TokenResult;
import com.digitalchina.webapp.RongCloud.models.FormatType;
import com.digitalchina.webapp.RongCloud.models.SdkHttpResult;
import com.digitalchina.webapp.prog.business.DynamicManager;
import com.digitalchina.webapp.prog.business.MemberManager;
import com.digitalchina.webapp.prog.business.QiNiuManager;
import com.digitalchina.webapp.prog.business.RecommendManager;
import com.digitalchina.webapp.prog.vo.AttentionMember;
import com.digitalchina.webapp.prog.vo.AttentionVo;
import com.digitalchina.webapp.prog.vo.DynamicVo;
import com.digitalchina.webapp.prog.vo.Dynamics;
import com.digitalchina.webapp.prog.vo.IsDynamicVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.NotifyVo;
import com.digitalchina.webapp.prog.vo.PageVo;
import com.digitalchina.webapp.prog.vo.RecommendVo;
import com.digitalchina.webapp.prog.vo.ResultDynamic;
import com.digitalchina.webapp.prog.vo.ResultNotify;
import com.digitalchina.webapp.prog.vo.VerifyCodeVo;
import com.digitalchina.webapp.prog.vo.Works;
import com.digitalchina.webapp.umpush.AndroidNotification;
import com.digitalchina.webapp.umpush.PushClient;
import com.digitalchina.webapp.umpush.android.AndroidUnicast;
import com.digitalchina.webapp.umpush.ios.IOSUnicast;
import com.digitalchina.webapp.utils.ContextConstants;
import com.digitalchina.webapp.utils.ErrorConstants;
import com.digitalchina.webapp.utils.MD5Utils;
import com.digitalchina.webapp.utils.ResponseResult;
import com.digitalchina.webapp.utils.SmsApi;
import com.google.gson.Gson;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;

@Service
@RequestMapping(value = "/user")
public class MemberService {

	Log log = LogFactory.getLog(MemberService.class);

	@Autowired
	private MemberManager memberManager;
	@Autowired
	private DynamicManager dynamicManager;
	@Autowired
	private RecommendManager recommendManager;

	@Value("#{configProperties['access_key']}")
	public String access_key;
	@Value("#{configProperties['secret_key']}")
	public String secret_key;
	@Value("#{configProperties['bucket_name']}")
	public String bucket_name;
	@Value("#{configProperties['page_size']}")
	public Integer page_size;
	@Value("#{configProperties['page_index']}")
	public Integer page_index;
	@Value("#{configProperties['work_download_url']}")
	public String work_download_url;

	/**
	 * 根据用户Id 获取用户信息
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public ResponseResult getUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseResult result = null;
		String userid = request.getParameter("userid");
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			Member m = memberManager.getMemberByUserId(userid);
			String headurlkey = m.getAvatar();
			QiNiuManager s = new QiNiuManager();
			if (!StringUtils.isNullOrEmpty(headurlkey)) {
				String headurl = s.getDownLoadUrl(access_key, secret_key,
						work_download_url + headurlkey);
				m.setAvatar(headurl);
			}
			result = new ResponseResult(m);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("====================获取用户信息异常=================");
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 获取七牛上传凭证
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getQiniuToken", method = RequestMethod.POST)
	public ResponseResult getQniuToken() {
		ResponseResult result = null;
		Auth auth = Auth.create(access_key, secret_key);
		String uptoken = auth.uploadToken(bucket_name, null);
		if (StringUtils.isNullOrEmpty(uptoken)) {
			result = new ResponseResult(ErrorConstants.QINIU_CODE,
					ErrorConstants.QINIU__MSG, "");
			return result;
		}
		result = new ResponseResult(uptoken);
		return result;
	}

	/**
	 * 获取验证码
	 * 
	 * @param phone
	 *            手机号
	 * @param type
	 *            获取验证码类型 0 获取注册验证码 1 获取修改密码验证码 2 其他
	 * @param areacode
	 *            区域对应码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	public ResponseResult verifyCode(String phone, String type, String areacode) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(phone) || StringUtils.isNullOrEmpty(type)
				|| StringUtils.isNullOrEmpty(areacode)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		if (areacode.equals(ContextConstants.AREACODE)) {
			Pattern regex = Pattern.compile(ContextConstants.regx);
			Matcher matcher = regex.matcher(phone);
			if (!matcher.matches()) {
				result = new ResponseResult(ErrorConstants.INPUT_PHONE_CODE,
						ErrorConstants.INPUT_PHONE_MSG, "");
				return result;
			}
		}
		try {
			if (type.equals(ContextConstants.USER_STATE_STOP)) {
				boolean isMember = memberManager.checkPhone(phone);
				if (isMember) {
					result = new ResponseResult(
							ErrorConstants.PHONE_EXIST_CODE,
							ErrorConstants.PHONE_EXIST_MSG, "");
					return result;
				}
			}
			// 要发送的手机号
			String mobile = areacode + phone;
			// 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
			String text = "";
			String yzcode = SmsApi.getVerifyCode();
			if (areacode.equals(ContextConstants.AREACODE)) {
				text = "【Fashine】您的验证码是:" + yzcode;
			} else {
				text = "【Fashine】your verification code is" + yzcode;
			}
			String code = SmsApi.sendSms(ContextConstants.apikey, text, mobile);
			JSONObject jsonObject = JSONObject.fromObject(code);
			String codenum = jsonObject.getString("code");
			if (Integer.parseInt(codenum) == 0) {
				VerifyCodeVo vo = new VerifyCodeVo();
				vo.setCode(String.valueOf(yzcode));
				Timestamp time = new Timestamp(System.currentTimeMillis());
				vo.setCreatetime(time);
				vo.setPhone(phone);
				vo.setType(type);
				vo.setIsuse("0");
				memberManager.doAddCode(vo);
				result = new ResponseResult(code);
			} else {
				result = new ResponseResult(
						ErrorConstants.PHONE_ERROR_VERIFICATION_CODE,
						ErrorConstants.PHONE_ERROR_VERIFICATION_CODE_MSG, "");
				return result;
			}
		} catch (Exception e) {
			log.debug("=============================获取验证码异常=========================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 验证验证码是否一致
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码数字
	 * @param type
	 *            获取验证码类型 0 获取注册验证码 1 获取修改密码验证码 2 其他
	 * @param areacode
	 *            区域对应码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCode", method = RequestMethod.POST)
	public ResponseResult checkCode(String areacode, String phone, String code,
			String type) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(phone) || StringUtils.isNullOrEmpty(code)
				|| StringUtils.isNullOrEmpty(type)
				|| StringUtils.isNullOrEmpty(areacode)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		if (areacode.equals(ContextConstants.AREACODE)) {
			Pattern regex = Pattern.compile(ContextConstants.regx);
			Matcher matcher = regex.matcher(phone);
			if (!matcher.matches()) {
				result = new ResponseResult(ErrorConstants.INPUT_PHONE_CODE,
						ErrorConstants.INPUT_PHONE_MSG, "");
				return result;
			}
		}
		try {
			// 验证验证码
			List<VerifyCodeVo> codes = memberManager.getVerifyCode(phone, type);
			if (codes.size() != 0 && codes != null) {
				VerifyCodeVo vo = codes.get(0);
				if (vo.getCode().equals(code)) {
					Timestamp ts = new Timestamp(System.currentTimeMillis());
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dateStop = format.format(ts);
					String dateStart = format.format(vo.getCreatetime());
					Long diffMin = SmsApi.getDateTime(dateStart, dateStop);
					if (vo.getIsuse().equals(ContextConstants.USER_STATE_STOP)) {
						if (diffMin <= 30L) {
							memberManager.updateCode(vo.getId(),
									ContextConstants.USER_STATE_NORMAL);
							result = new ResponseResult("");
						} else {
							result = new ResponseResult(
									ErrorConstants.INVALID_CODE,
									ErrorConstants.INVALID_MSG, "");
						}
					} else {
						result = new ResponseResult(
								ErrorConstants.INVALID_CODE,
								ErrorConstants.INVALID_MSG, "");
					}

				} else {
					result = new ResponseResult(
							ErrorConstants.ERROR_VERIFICATION_CODE,
							ErrorConstants.ERROR_VERIFICATION_CODE_MSG, "");
					return result;
				}
			} else {
				result = new ResponseResult(
						ErrorConstants.ERROR_VERIFICATION_CODE,
						ErrorConstants.ERROR_VERIFICATION_CODE_MSG, "");
				return result;
			}
		} catch (Exception e) {
			log.debug("=============================验证验证码异常=========================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 用户注册
	 * 
	 * @param areacode
	 *            区域对应码
	 * @param avatar
	 *            注册头像头像url
	 * @param usertype
	 *            用户类型 0 需求用户 1 设计用户
	 * @param username
	 *            用户名称
	 * @param phone
	 *            手机号
	 * @param password
	 *            密码
	 * @param goodfield
	 *            擅长领域
	 * @return
	 * @throws IOException
	 */

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseResult registerMember(String areacode, String avatar,
			String usertype, String username, String phone, String password,
			String goodfield) throws IOException {

		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(usertype)
				|| StringUtils.isNullOrEmpty(phone)
				|| StringUtils.isNullOrEmpty(password)
				|| StringUtils.isNullOrEmpty(areacode)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		// String pushMessage = "";
		if (areacode.equals(ContextConstants.AREACODE)) {
			Pattern regex = Pattern.compile(ContextConstants.regx);
			Matcher matcher = regex.matcher(phone);
			if (!matcher.matches()) {
				result = new ResponseResult(ErrorConstants.INPUT_PHONE_CODE,
						ErrorConstants.INPUT_PHONE_MSG, "");
				return result;
			}
			// pushMessage = ContextConstants.RONGCLOUD_CH_MESSAGE;
			// } else {
			// pushMessage = ContextConstants.RONGCLOUD_EN_MESSAGE;
		}

		Member mo = new Member();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		mo.setUsertype(usertype);
		mo.setUserid(uuid);
		mo.setUsername(username);
		mo.setPassword(MD5Utils.string2MD5(password));
		mo.setGoodfield(goodfield);
		mo.setPhone(phone);
		mo.setRegisttime(new Date());
		mo.setState(ContextConstants.USER_STATE_NORMAL);
		if (!StringUtils.isNullOrEmpty(avatar)) {
			mo.setAvatar(avatar);
		}
		try {
			SdkHttpResult resultp = ApiHttpClient.getToken(
					ContextConstants.RONGCLOUD_KEY,
					ContextConstants.RONGCLOUD_SECRET, uuid, username, avatar,
					FormatType.json);
			if (resultp.getHttpCode() == 200) {
				Gson gson = new Gson();
				TokenResult token = gson.fromJson(resultp.getResult(),
						TokenResult.class);
				mo.setRongcloud_token(token.getToken());
			}
			// 注册成功自动发送一条消息
			// List<String> userids = new ArrayList<String>();
			// userids.add(uuid);
			// ApiHttpClient.publishSystemMessage(ContextConstants.RONGCLOUD_KEY,
			// ContextConstants.RONGCLOUD_SECRET,
			// ContextConstants.RONGCLOUD_ID, userids, new TxtMessage(
			// pushMessage), "pushContent", "pushData",
			// FormatType.json);
			memberManager.doRegisterMember(mo);
			result = new ResponseResult("");
		} catch (Exception e) {
			log.debug("=============================用户注册异常=========================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 登录
	 * 
	 * @param phone
	 *            手机号
	 * @param password
	 *            密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseResult login(String phone, String password,
			String device_sys, String um_device_token, String sys_language) {
		System.out.println("phone=====================" + phone);
		System.out.println("password=====================" + password);
		System.out.println("device_sys=====================" + device_sys);
		System.out.println("um_device_token====================="
				+ um_device_token);
		System.out.println("sys_language=====================" + sys_language);
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(phone)
				|| StringUtils.isNullOrEmpty(password)
				|| StringUtils.isNullOrEmpty(sys_language)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		Member member = memberManager.getMemberByPhone(phone);
		try {
			// boolean isMember = memberManager.checkPhone(phone);
			if (member != null) {
				if (MD5Utils.string2MD5(password).equals(member.getPassword())) {
					// 生成用户token
					String uuid = UUID.randomUUID().toString().replace("-", "")
							+ System.currentTimeMillis();
					String user_token = MD5Utils.string2MD5(uuid);
					member.setUser_token(user_token);
					if (StringUtils.isNullOrEmpty(device_sys)
							|| StringUtils.isNullOrEmpty(um_device_token)) {

					} else {
						if (StringUtils.isNullOrEmpty(member
								.getUm_device_token())) {
							boolean re = pushSystemInfo(device_sys,
									um_device_token, sys_language,
									member.getUserid());
							if (re) {
								memberManager.updateUmToken(member.getUserid(),
										um_device_token);
							}
						} else {
							memberManager.updateUmToken(member.getUserid(),
									um_device_token);
						}
					}
					QiNiuManager s = new QiNiuManager();
					String headurlkey = member.getAvatar();
					if (!StringUtils.isNullOrEmpty(headurlkey)) {
						String headurl = s.getDownLoadUrl(access_key,
								secret_key, work_download_url + headurlkey);
						member.setHeadurl(headurl);
					}
					result = new ResponseResult(member);
				} else {
					result = new ResponseResult(ErrorConstants.PSW_ERROR_CODE,
							ErrorConstants.PSW_ERROR_MSG, "");
					return result;
				}
			} else {
				result = new ResponseResult(ErrorConstants.PHONE_NOTEXIST_CODE,
						ErrorConstants.PHONE_NOTEXIST_MSG, "");
				return result;
			}
		} catch (Exception e) {
			log.debug("=============================用户登陆异常=========================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	// 推送系统消息
	public boolean pushSystemInfo(String device_sys, String um_device_token,
			String sys_language, String userid) {
		boolean returnResult = false;
		String title = ContextConstants.RONGCLOUD_NAME;
		String message = "";
		try {
			if (sys_language.equals("zh")) {
				message = ContextConstants.RONGCLOUD_CH_MESSAGE;
			} else {
				message = ContextConstants.RONGCLOUD_EN_MESSAGE;
			}
			// 发送系统通知
			if (device_sys.equals("Android")) {
				AndroidUnicast unicast = new AndroidUnicast(
						ContextConstants.UM_APPKEY,
						ContextConstants.UM_APPMASTERSECRET);
				unicast.setDeviceToken(um_device_token);
				unicast.setTicker(ContextConstants.RONGCLOUD_NAME);
				unicast.setTitle(ContextConstants.RONGCLOUD_NAME);
				unicast.setText(message);
				unicast.goAppAfterOpen();
				unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
				unicast.setProductionMode();
				// Set customized fields
				unicast.setExtraField("test", "helloworld");
				returnResult = new PushClient().send(unicast);
			}
			if (device_sys.equals("ios")) {
				IOSUnicast unicast = new IOSUnicast(ContextConstants.UM_APPKEY,
						ContextConstants.UM_APPMASTERSECRET);
				// TODO Set your device token
				unicast.setDeviceToken(um_device_token);
				unicast.setAlert(message);
				unicast.setBadge(0);
				unicast.setSound("default");
				// TODO set 'production_mode' to 'true' if your app is under
				// production mode
				unicast.setTestMode();
				// Set customized fields
				unicast.setCustomizedField("test", "helloworld");
				returnResult = new PushClient().send(unicast);
			}
			if (returnResult) {
				NotifyVo vo = new NotifyVo();
				Long ltime = System.currentTimeMillis();
				vo.setTimestamp(String.valueOf(ltime));
				String uuid = UUID.randomUUID().toString().replace("-", "");
				vo.setId(uuid);
				vo.setMessage(message);
				vo.setTitle(title);
				vo.setUserid(userid);
				vo.setState(ContextConstants.USER_STATE_STOP);
				vo.setNotify_type("official");
				vo.setCreatetime(new Timestamp(ltime));
				memberManager.doInsertNotify(vo);
			}
		} catch (Exception e) {
			log.error("==========================系统推送消息失败========================");
			e.printStackTrace();
		}
		return returnResult;
	}

	/**
	 * 获取融云token
	 * 
	 * @param userid
	 *            用户userid
	 * @param username
	 *            用户名称
	 * @param headurl
	 *            用户头像url
	 */
	@ResponseBody
	@RequestMapping(value = "/getRongCloudToken", method = RequestMethod.POST)
	public ResponseResult getRongCloudToken(String userid, String username,
			String headurl) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(username)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			Member member = memberManager.getMemberByUserId(userid);
			if (member == null) {
				result = new ResponseResult(ErrorConstants.USER_NOTEXIST_CODE,
						ErrorConstants.USER_NOTEXIST_MSG, "");
				return result;
			}
			// 更新member
			SdkHttpResult resultp = null;
			if (StringUtils.isNullOrEmpty(headurl)) {
				resultp = ApiHttpClient.getToken(
						ContextConstants.RONGCLOUD_KEY,
						ContextConstants.RONGCLOUD_SECRET, userid, username,
						"", FormatType.json);
			} else {
				resultp = ApiHttpClient.getToken(
						ContextConstants.RONGCLOUD_KEY,
						ContextConstants.RONGCLOUD_SECRET, userid, username,
						headurl, FormatType.json);
			}
			if (resultp.getHttpCode() == 200) {
				Gson gson = new Gson();
				TokenResult token = gson.fromJson(resultp.getResult(),
						TokenResult.class);
				memberManager.updateMemberByUserId(userid, token.getToken());
				result = new ResponseResult(token.getToken());
			} else {
				result = new ResponseResult(ErrorConstants.RONGCLOUD_CODE,
						ErrorConstants.RONGCLOUD__MSG, "");
			}
		} catch (Exception e) {
			log.debug("=======================获取融云token失败======================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 忘记密码 --重置密码
	 * 
	 * @param phone
	 *            手机号
	 * @param npwd
	 *            新密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public ResponseResult resetPsw(String phone, String npwd) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(phone) || StringUtils.isNullOrEmpty(npwd)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		boolean isMember = memberManager.checkPhone(phone);
		if (isMember) {
			try {
				memberManager.updateUserPsw(phone, MD5Utils.string2MD5(npwd));
				result = new ResponseResult("");
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("=============================修改密码异常=========================");
				result = new ResponseResult(ErrorConstants.UPDATE_PSW_CODE,
						ErrorConstants.UPDATE_PSW_MSG, "");
			}
		} else {
			result = new ResponseResult(ErrorConstants.PHONE_NOTEXIST_CODE,
					ErrorConstants.PHONE_NOTEXIST_MSG, "");
			return result;
		}
		return result;
	}

	/**
	 * 根据userid进去个人主页
	 * 
	 * @param loginid
	 *            登录用户
	 * @param userid
	 *            其他用户Id
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/personHome", method = RequestMethod.POST)
	public ResponseResult personHome(String loginid, String userid)
			throws IOException {
		Map<String, Object> resultList = new HashMap<String, Object>();
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			Member member = memberManager.getMemberByUserId(userid);
			if (member != null) {
				QiNiuManager s = new QiNiuManager();
				AttentionMember am = new AttentionMember();
				String headurlkey = member.getAvatar();
				if (!StringUtils.isNullOrEmpty(headurlkey)) {
					String headurl = s.getDownLoadUrl(access_key, secret_key,
							work_download_url + headurlkey);
					am.setHeadurl(headurl);
				}
				am.setUserid(userid);
				am.setUsername(member.getUsername());
				am.setUserProfile(member.getUserprofile());
				// 判断是否已关注
				if (StringUtils.isNullOrEmpty(loginid)) {
					am.setIsattention("0");
				} else {
					if (loginid.equals(userid)) {
						am.setIsattention("1");
					} else {
						AttentionVo avo = memberManager.getAttention(loginid,
								userid);
						if (avo == null) {
							am.setIsattention("0");
						} else {
							if (avo.getState().equals(
									ContextConstants.USER_STATE_NORMAL)) {
								am.setIsattention("1");
							} else {
								am.setIsattention("0");
							}
						}
					}
				}
				resultList.put("memberInfo", am);
				// 获取我发布的动态
				Integer total_row = 0;// 总记录数
				Integer total_page = 0;// 总页数
				PageVo pagevo = new PageVo();
				Long dynamicTotal = dynamicManager.countDynamic(userid);
				total_row = dynamicTotal.intValue();
				pagevo.setTotalRow(total_row);
				total_page = (total_row % page_size == 0) ? (total_row / page_size)
						: (total_row / page_size) + 1;
				pagevo.setTotalPage(total_page);
				if (page_index > total_page)
					page_index = total_page;
				if (page_index < 1)
					page_index = 1;
				pagevo.setPageSize(page_size);
				pagevo.setPageIndex(page_index);
				resultList.put("dynamicpageVo", pagevo);
				List<IsDynamicVo> vos = dynamicManager.getDynamicList(
						page_index, page_size, userid);
				// 获取图片
				List<ResultDynamic> rd = getResultDynamicList(vos);
				// 1.0判断是否点
				List<ResultDynamic> list = dynamicManager.tramsRecommendVo(rd,
						loginid, access_key, secret_key, work_download_url);
				resultList.put("dynamicList", list);
				// 获取上传作品
				List<RecommendVo> works = recommendManager.getRecommendWorks(1,
						5, "", userid);
				List<Works> imgKeys = new ArrayList<Works>();
				List<Works> videoKeys = new ArrayList<Works>();
				// 获取图片
				for (int i = 0; i < works.size(); i++) {
					int index = 0;
					String imgKey = works.get(i).getWorkey();
					String videoKey = works.get(i).getVideoimgkey();
					if (!StringUtils.isNullOrEmpty(imgKey)) {
						String[] simgKey = imgKey.split(",");
						for (int j = 0; j < simgKey.length; j++) {
							Works bwork = new Works();
							String originurl = s.getDownLoadUrl(access_key,
									secret_key, work_download_url + simgKey[j]);
							bwork.setOriginalWork(originurl);
							String[] types = simgKey[j].split("\\.");
							boolean typeTrue = ContextConstants.VEDIO_TYPR
									.contains(types[1]);
							if (typeTrue) {
								bwork.setType(ContextConstants.USER_STATE_NORMAL);
								if (!StringUtils.isNullOrEmpty(videoKey)) {
									String[] videokeys = videoKey.split(",");
									String breviaryurl = s.getDownLoadUrl(
											access_key, secret_key,
											work_download_url
													+ videokeys[index]);
									bwork.setThumbnailWork(breviaryurl);
									index = index + 1;
								}
								videoKeys.add(bwork);
							} else {
								bwork.setType(ContextConstants.USER_STATE_STOP);
								String breviaryurl = s.getDownLoadUrl(
										access_key, secret_key,
										work_download_url + simgKey[j]
												+ ContextConstants.IMAGE_SIZE);
								bwork.setThumbnailWork(breviaryurl);
								imgKeys.add(bwork);
							}
						}
					}
				}
				resultList.put("imgWorks", imgKeys);
				resultList.put("videoWorks", videoKeys);
				result = new ResponseResult(resultList);
			} else {
				result = new ResponseResult(ErrorConstants.USER_NOTEXIST_CODE,
						ErrorConstants.USER_NOTEXIST_MSG, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("==========个人页异常=================");
			result = new ResponseResult(e);
		}
		return result;
	}

	public List<ResultDynamic> getResultDynamicList(List<IsDynamicVo> list)
			throws Exception {
		List<ResultDynamic> result = new ArrayList<ResultDynamic>();
		for (int i = 0; i < list.size(); i++) {
			ResultDynamic rw = new ResultDynamic();
			rw.setTotalComments(list.get(i).getTotalComments());
			rw.setContents(list.get(i).getContents());
			rw.setCreatetime(Long.valueOf(list.get(i).getCreatetime()));
			rw.setHeadurl(list.get(i).getHeadurl());
			rw.setId(list.get(i).getId());
			rw.setIsLiked(list.get(i).getIsLiked());
			rw.setLocation(list.get(i).getLocation());
			rw.setTotalLikes(list.get(i).getTotalLikes());
			rw.setPostBackground(list.get(i).getPostBackground());
			rw.setPostCategory(list.get(i).getPostCategory());
			rw.setPostDetails(list.get(i).getPostDetails());
			rw.setType(list.get(i).getType());
			rw.setState(list.get(i).getState());
			rw.setUserid(list.get(i).getUserid());
			rw.setUsername(list.get(i).getUsername());
			rw.setVideoimgurl(list.get(i).getVideoimgurl());
			rw.setPrice(list.get(i).getPrice());
			rw.setSell(list.get(i).getSell());
			if (!StringUtils.isNullOrEmpty(list.get(i).getImgurl())) {
				rw.setDynamics(dynamicManager.getBreviaryWork(access_key,
						secret_key, work_download_url, list.get(i).getImgurl(),
						list.get(i).getVideoimgurl()));
			}
			if (!StringUtils.isNullOrEmpty(list.get(i).getPhotos())) {
				rw.setDynamics(dynamicManager.getBreviaryWork(access_key,
						secret_key, work_download_url, list.get(i).getPhotos(),
						""));
			}
			if (rw.getDynamics() == null) {
				List<Dynamics> bw = new ArrayList<Dynamics>();
				rw.setDynamics(bw);
			}
			// Integer c_total_row = 0;// 总记录数
			// Integer c_total_page = 0;// 总页数
			Long commentTotal = dynamicManager.countComment(list.get(i).getId()
					.toString());
			// c_total_row = commentTotal.intValue();
			// c_total_page = (c_total_row % page_size == 0) ? (c_total_row /
			// page_size)
			// : (c_total_row / page_size) + 1;
			// rw.setCommentpageindex(1);
			// rw.setCommenttotalpage(c_total_page);
			rw.setComments(dynamicManager.getCommentList(list.get(i).getId()
					.toString(), 1, commentTotal.intValue()));
			result.add(rw);
		}
		return result;
	}

	/**
	 * 关注
	 * 
	 * @param userid
	 *            用户userid
	 * @param attention_userid
	 *            被关注userid
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public ResponseResult attention(String userid, String attention_userid)
			throws IOException {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(attention_userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			AttentionVo avo = memberManager.isAttention(userid,
					attention_userid);
			if (avo == null) {
				AttentionVo vo = new AttentionVo();
				vo.setAttention_userid(attention_userid);
				Timestamp time = new Timestamp(System.currentTimeMillis());
				vo.setAttention_time(time);
				vo.setState(ContextConstants.USER_STATE_NORMAL);
				vo.setUserid(userid);
				memberManager.doAttention(vo);
			} else {
				memberManager.cancleAttention(userid, attention_userid,
						ContextConstants.USER_STATE_NORMAL);
			}
			result = new ResponseResult("");
		} catch (Exception e) {
			log.error("============关注异常==============");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 取消关注
	 * 
	 * @param userid
	 *            用户userid
	 * @param attention_userid
	 *            被关注userid
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	public ResponseResult cancleAttention(String userid, String attention_userid)
			throws IOException {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(attention_userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}

		try {
			memberManager.cancleAttention(userid, attention_userid,
					ContextConstants.USER_STATE_STOP);
			result = new ResponseResult("");
		} catch (Exception e) {
			log.error("============取消关注异常==============");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 获取更多关注、和关注我的用户
	 * 
	 * @param loginid
	 * @param followid
	 * @param optoken
	 * @param type
	 *            0 关注我 1 我关注
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getFollowList", method = RequestMethod.POST)
	public ResponseResult getMoreAttentionList(String type, String pagesize,
			String pageindex, String userid) throws IOException {
		Map<String, Object> resultList = new HashMap<String, Object>();
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(type)
				|| StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		Integer total_row = 0;// 总记录数
		Integer total_page = 0;// 总页数
		if (!StringUtils.isNullOrEmpty(pagesize)) {
			page_size = Integer.parseInt(pagesize);
		}
		if (!StringUtils.isNullOrEmpty(pageindex)) {
			page_index = Integer.parseInt(pageindex);
		}
		PageVo pagevo = new PageVo();
		List<AttentionMember> list = new ArrayList<AttentionMember>();
		try {
			Long attentionTotal = memberManager.countAttention(type, userid);
			total_row = attentionTotal.intValue();
			pagevo.setTotalRow(total_row);
			total_page = (total_row % page_size == 0) ? (total_row / page_size)
					: (total_row / page_size) + 1;
			pagevo.setTotalPage(total_page);
			if (page_index > total_page)
				page_index = total_page;
			if (page_index < 1)
				page_index = 1;
			pagevo.setPageSize(page_size);
			pagevo.setPageIndex(page_index);
			resultList.put("pageVo", pagevo);
			list = memberManager.getAttentionList(type, userid, page_index,
					page_size, total_page, total_row);
			// 设置用户头像
			QiNiuManager s = new QiNiuManager();
			for (int i = 0; i < list.size(); i++) {
				String headurlkey = list.get(i).getHeadurl();
				if (!StringUtils.isNullOrEmpty(headurlkey)) {
					String headurl = s.getDownLoadUrl(access_key, secret_key,
							work_download_url + headurlkey);
					list.get(i).setHeadurl(headurl);
				}
			}
			resultList.put("followList", list);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			log.error("===================获取关注列表出错===================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 获取更多我发布动态
	 * 
	 * @param loginid
	 * @param userid
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMoreMyDynamic", method = RequestMethod.POST)
	public ResponseResult getMoreMyDynamic(String loginid, String userid,
			String pagesize, String pageindex) throws IOException {
		Map<String, Object> resultList = new HashMap<String, Object>();
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			// 获取我发布的动态
			Integer total_row = 0;// 总记录数
			Integer total_page = 0;// 总页数
			if (!StringUtils.isNullOrEmpty(pagesize)) {
				page_size = Integer.parseInt(pagesize);
			}
			if (!StringUtils.isNullOrEmpty(pageindex)) {
				page_index = Integer.parseInt(pageindex);
			}
			PageVo pagevo = new PageVo();
			Long dynamicTotal = dynamicManager.countDynamic(userid);
			total_row = dynamicTotal.intValue();
			pagevo.setTotalRow(total_row);
			total_page = (total_row % page_size == 0) ? (total_row / page_size)
					: (total_row / page_size) + 1;
			pagevo.setTotalPage(total_page);
			if (page_index > total_page)
				page_index = total_page;
			if (page_index < 1)
				page_index = 1;
			pagevo.setPageSize(page_size);
			pagevo.setPageIndex(page_index);
			resultList.put("pageVo", pagevo);
			List<IsDynamicVo> vos = dynamicManager.getDynamicList(page_index,
					page_size, userid);
			// 获取图片
			List<ResultDynamic> rd = getResultDynamicList(vos);
			// 1.0判断是否点赞
			List<ResultDynamic> list = dynamicManager.tramsRecommendVo(rd,
					loginid, access_key, secret_key, work_download_url);
			resultList.put("dynamicList", list);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			log.error("===================获取更多我的动态异常===================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param userid
	 *            用户Id
	 * @param newPwd
	 *            新密码
	 * @param originalPwd
	 *            原密码
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	public ResponseResult updatePswById(String userid, String newPwd,
			String originalPwd) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(newPwd)
				|| StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(originalPwd)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		Member member = memberManager.getMemberByUserId(userid);
		if (member == null) {
			result = new ResponseResult(ErrorConstants.USER_NOTEXIST_CODE,
					ErrorConstants.USER_NOTEXIST_MSG, "");
			return result;
		}
		try {
			if (MD5Utils.string2MD5(originalPwd).equals(member.getPassword())) {
				memberManager.updateUserPsw(member.getPhone(),
						MD5Utils.string2MD5(newPwd));
				result = new ResponseResult("");
			} else {
				result = new ResponseResult(ErrorConstants.ORIGIN_PSW_CODE,
						ErrorConstants.ORIGIN_PSW_MSG, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===================登录修改密码报错===================");
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userid
	 *            用户Id
	 * @param avatar
	 *            用户头像
	 * @param username
	 *            用户名称
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public ResponseResult updateUserInfo(String userid, String avatar,
			String username, String userProfile) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			Member member = memberManager.getMemberByUserId(userid);
			if (member == null) {
				result = new ResponseResult(ErrorConstants.USER_NOTEXIST_CODE,
						ErrorConstants.USER_NOTEXIST_MSG, "");
				return result;
			}
			if (StringUtils.isNullOrEmpty(avatar)) {
				avatar = member.getAvatar();
			}
			if (StringUtils.isNullOrEmpty(username)) {
				username = member.getUsername();
			}
			if (StringUtils.isNullOrEmpty(userProfile)) {
				userProfile = member.getUserprofile();
			}
			memberManager.updateUserInfo(userid, avatar, username, userProfile);
			Member resultmember = memberManager.getMemberByUserId(userid);
			QiNiuManager s = new QiNiuManager();
			String headurlkey = resultmember.getAvatar();
			if (!StringUtils.isNullOrEmpty(headurlkey)) {
				if (!StringUtils.isNullOrEmpty(headurlkey)) {
					String headurl = s.getDownLoadUrl(access_key, secret_key,
							work_download_url + headurlkey);
					resultmember.setHeadurl(headurl);
					resultmember.setAvatar(headurl);
				}
			}
			result = new ResponseResult(resultmember);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===================修改用户信息异常===================");
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 获取用户推送消息
	 * 
	 * @param userid
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPushSystemInfo", method = RequestMethod.POST)
	public ResponseResult getPushSystemInfo(String userid) {
		ResponseResult result = null;
		Map<String, Object> list = new HashMap<String, Object>();
		Member mem = memberManager.getMemberByUserId(userid);
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			List<ResultNotify> vos = memberManager.getPushSystemInfo(userid);
			for (int i = 0; i < vos.size(); i++) {
				if (vos.get(i).getNotify_type().equals("official")) {
					vos.get(i).setHost_id("");
					vos.get(i).setHost_name("");
				}
				if (vos.get(i).getNotify_type().equals("moments")) {
					// List<IsDynamicVo>
					// lists=dynamicManager.getDynamicById(dyid)
					// if(lists.size()!=0 || lists !=null){
					// vos.get(i).setHost_id(lists.get(0).getId());
					// }
					DynamicVo d=dynamicManager.getDynamicById(vos.get(i).getDynamicid());
					vos.get(i).setHost_name(d.getType());
					vos.get(i).setHost_id(vos.get(i).getDynamicid());
				}
				if (vos.get(i).getNotify_type().equals("invitation")) {
					vos.get(i).setHost_id(userid);
					vos.get(i).setHost_name(mem.getUsername());
				}
			}
			list.put("notifyList", vos);
			result = new ResponseResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===================获取系统推送信息异常===================");
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 获取tab是否有新的通知
	 * 
	 * @param userid
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getNewNotify", method = RequestMethod.POST)
	public ResponseResult getNewNotify(String userid) {
		ResponseResult result = null;
		Map<String, Object> list = new HashMap<String, Object>();
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			Long snum = memberManager.countPushInfo(userid, "official");
			Long cnum = memberManager.countPushInfo(userid, "moments");
			Long inum = memberManager.countPushInfo(userid, "invitation");
			if (snum.intValue() == 0 && cnum.intValue() == 0
					&& inum.intValue() == 0) {
				list.put("notification", ContextConstants.USER_STATE_STOP);
			} else {
				list.put("notification", ContextConstants.USER_STATE_NORMAL);
			}
			// Long cnum=memberManager.countPushInfo(userid,
			// ContextConstants.USER_STATE_NORMAL);
			// if(cnum.intValue()==0){
			// list.put("post_comment",ContextConstants.USER_STATE_STOP);
			// }else{
			// list.put("post_comment",ContextConstants.USER_STATE_NORMAL);
			// }
			result = new ResponseResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===================获取系统是否有新消息异常===================");
			result = new ResponseResult(e);
		}
		return result;
	}
	/**
	 * 更改新的通知状态
	 * 
	 * @param id
	 *            
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateNotify", method = RequestMethod.POST)
	public ResponseResult updateNotify(String id,String userid) {
		ResponseResult result = null;
		Map<String, Object> list = new HashMap<String, Object>();
		if (StringUtils.isNullOrEmpty(id)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
		memberManager.updateNotify(id);
		Long snum = memberManager.countPushInfo(userid, "official");
		Long cnum = memberManager.countPushInfo(userid, "moments");
		Long inum = memberManager.countPushInfo(userid, "invitation");
		if (snum.intValue() == 0 && cnum.intValue() == 0
				&& inum.intValue() == 0) {
			list.put("has_new", ContextConstants.USER_STATE_STOP);
		} else {
			list.put("has_new", ContextConstants.USER_STATE_NORMAL);
		}
			result = new ResponseResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===================更改新的通知状态异常===================");
			result = new ResponseResult(e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseResult test(String userid) {
		ResponseResult result = null;
		Member member = memberManager.getMemberByUserId(userid);
		result=new ResponseResult(member);
	return result;	
	}
}

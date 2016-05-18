package com.digitalchina.webapp.prog.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.digitalchina.webapp.prog.business.DynamicManager;
import com.digitalchina.webapp.prog.business.MemberManager;
import com.digitalchina.webapp.prog.business.QiNiuManager;
import com.digitalchina.webapp.prog.business.RecommendManager;
import com.digitalchina.webapp.prog.vo.DynamicVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.NotifyVo;
import com.digitalchina.webapp.prog.vo.PageVo;
import com.digitalchina.webapp.prog.vo.RecommendVo;
import com.digitalchina.webapp.prog.vo.ResultTags;
import com.digitalchina.webapp.prog.vo.ResultWork;
import com.digitalchina.webapp.prog.vo.TagsVo;
import com.digitalchina.webapp.prog.vo.WorkVo;
import com.digitalchina.webapp.prog.vo.WorkVoteVo;
import com.digitalchina.webapp.prog.vo.Works;
import com.digitalchina.webapp.umpush.AndroidNotification;
import com.digitalchina.webapp.umpush.PushClient;
import com.digitalchina.webapp.umpush.android.AndroidUnicast;
import com.digitalchina.webapp.umpush.ios.IOSCustomizedcast;
import com.digitalchina.webapp.utils.ContextConstants;
import com.digitalchina.webapp.utils.ErrorConstants;
import com.digitalchina.webapp.utils.ResponseResult;
import com.qiniu.util.StringUtils;

@Service
@RequestMapping(value = "/work")
public class RecommendWorkService {

	Log log = LogFactory.getLog(RecommendWorkService.class);

	@Autowired
	private RecommendManager recommendManager;
	@Autowired
	private DynamicManager dynamicManager;
	@Autowired
	private MemberManager memberManager;

	@Value("#{configProperties['access_key']}")
	public String access_key;
	@Value("#{configProperties['secret_key']}")
	public String secret_key;
	@Value("#{configProperties['work_download_url']}")
	public String work_download_url;
	@Value("#{configProperties['page_size']}")
	public Integer page_size;
	@Value("#{configProperties['page_index']}")
	public Integer page_index;

	/**
	 * 根据系统语言获取对应tags
	 * 
	 * @param language
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTags", method = RequestMethod.POST)
	public ResponseResult getTags(String language) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(language)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			List<TagsVo> list = recommendManager.getTags();
			List<ResultTags> re = recommendManager
					.getResultTags(list, language);
			result = new ResponseResult(re);
		} catch (Exception e) {
			log.debug("=====================获取tags异常=================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}

		return result;
	}

	/**
	 * 
	 * 上传设计作品
	 * 
	 * @param tags
	 *            作品标签
	 * @param userid
	 *            上传者id
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */

	@ResponseBody
	@RequestMapping(value = "/uploadWorks", method = RequestMethod.POST)
	public ResponseResult uploadWorks(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		ResponseResult result = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String param_work = sb.toString();
		JSONObject json_param = new JSONObject(param_work);
		String userid = json_param.get("userid").toString();
		String tags = json_param.get("tags").toString();
		JSONArray array_works = json_param.getJSONArray("works");
		String workey = "";
		for (int i = 0; i < array_works.length(); i++) {
			String one_work = array_works.get(i).toString();
			JSONObject json_one = new JSONObject(one_work);
			if (!StringUtils.isNullOrEmpty(json_one.get("work_url").toString())) {
				workey += json_one.get("work_url").toString() + ",";
			}
		}
		String videoImg = "";
		JSONArray video_img = json_param.getJSONArray("worksThumb");
		for (int j = 0; j < video_img.length(); j++) {
			String one_video = video_img.get(j).toString();
			JSONObject json_one_video = new JSONObject(one_video);
			if (!StringUtils.isNullOrEmpty(json_one_video.get("thumbnail")
					.toString())) {
				videoImg += json_one_video.get("thumbnail").toString() + ",";
			}
		}
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(tags)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			WorkVo vo = new WorkVo();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			vo.setId(uuid);
			Long ltime = System.currentTimeMillis();
			vo.setCreatetime(new Timestamp(ltime));
			vo.setState(ContextConstants.USER_STATE_NORMAL);
			vo.setUserid(userid);
			vo.setCnum(0);
			vo.setVotenum(0);
			vo.setTags(tags);
			vo.setChoiceshow(ContextConstants.USER_STATE_STOP);
			vo.setSquareshow(ContextConstants.USER_STATE_NORMAL);
			if (!StringUtils.isNullOrEmpty(workey)) {
				vo.setWorkey(workey.substring(0, workey.length() - 1));
			}
			if (!StringUtils.isNullOrEmpty(videoImg)) {
				vo.setVideoimgkey(videoImg.substring(0, videoImg.length() - 1));
			}
			recommendManager.doUploadWorks(vo);
			// 同步到生活圈
			syncDynamic(userid, tags, workey, videoImg, ltime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("========================上传作品报错======================");
			result = new ResponseResult(e);
			return result;
		}
		result = new ResponseResult("");
		return result;
	}

	public void syncDynamic(String userid, String tags, String workey,
			String videoImg, Long ltime) throws Exception {
		DynamicVo vo = new DynamicVo();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		vo.setId(uuid);
		// if (!StringUtils.isNullOrEmpty(tags)) {
		// vo.setContent(tags);
		// }
		if (!StringUtils.isNullOrEmpty(workey)) {
			vo.setImgurl(workey.substring(0, workey.length() - 1));
		}
		vo.setPraisnum(0);
		vo.setState(ContextConstants.USER_STATE_NORMAL);
		vo.setTimestamp(String.valueOf(ltime));
		Timestamp time = new Timestamp(ltime);
		vo.setCreatetime(time);
		vo.setUserid(userid);
		vo.setType("2");
		vo.setCommentnum(0);
		if (!StringUtils.isNullOrEmpty(videoImg)) {
			vo.setVideoimgurl(videoImg.substring(0, videoImg.length() - 1));
		}
		// if(StringUtils.isNullOrEmpty(isfacebook)){
		// vo.setIsfacebook(ContextConstants.USER_STATE_STOP);
		// }else{
		// vo.setIsfacebook(isfacebook);
		// }
		// if(StringUtils.isNullOrEmpty(isweixin)){
		// vo.setIsweixin(ContextConstants.USER_STATE_STOP);
		// }else{
		// vo.setIsweixin(isweixin);
		// }
		// if (!StringUtils.isNullOrEmpty(location)) {
		// vo.setLocation(location);
		// }
		dynamicManager.doReleaseDynamic(vo);
	}

	/**
	 * 投票操作
	 * 
	 * @param workid
	 *            作品id
	 * @param userid
	 *            用户id
	 */
	@ResponseBody
	@RequestMapping(value = "/workVote", method = RequestMethod.POST)
	public ResponseResult workVote(String workid, String userid) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(workid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			WorkVoteVo voteVo = recommendManager.getWorkVoteByParams(workid,
					userid);
			if (voteVo == null) {
				WorkVoteVo vo = new WorkVoteVo();
				Timestamp time = new Timestamp(System.currentTimeMillis());
				vo.setCreatetime(time);
				vo.setState(ContextConstants.USER_STATE_NORMAL);
				vo.setUserid(userid);
				// workState = ContextConstants.USER_STATE_NORMAL;
				vo.setWorkid(workid);
				recommendManager.doAddWorkVote(vo);
				// 更改被点击次数
				WorkVo wVo = recommendManager.getWorkById(workid);
				int total = wVo.getVotenum() + 1;
				recommendManager.doUpWorkNumById(workid, total);
				result = new ResponseResult("");
			} else {
				result = new ResponseResult(ErrorConstants.VOTE_CODE,
						ErrorConstants.VOTE_MSG, "");
			}

		} catch (Exception e) {
			log.debug("==============================投票异常===================================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}

		return result;
	}

	/**
	 * 获取首页数据
	 * 
	 * @param pagesize
	 *            每页显示数量
	 * @param pageindex
	 *            当前页
	 * @param type
	 *            0 广场数据 1 精选数据
	 * @param userid
	 *            用户id
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/homeWorks", method = RequestMethod.POST)
	public ResponseResult homeWorks(String userid, String pagesize,
			String pageindex, String type) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(type)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}

		Map<String, Object> resultList = new HashMap<String, Object>();
		Integer total_row = 0;// 总记录数
		Integer total_page = 0;// 总页数
		if (!StringUtils.isNullOrEmpty(pagesize)) {
			page_size = Integer.parseInt(pagesize);
		}
		if (!StringUtils.isNullOrEmpty(pageindex)) {
			page_index = Integer.parseInt(pageindex);
		}
		PageVo pagevo = new PageVo();
		try {
			Long recommendTotal = 0L;
			recommendTotal = recommendManager.countRecommendWorks(type, "");
			// if (ContextConstants.USER_STATE_NORMAL.equals(type)) {
			// if(recommendTotal > 20L){
			// recommendTotal=20L;
			// }
			// }
			total_row = recommendTotal.intValue();
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
			List<RecommendVo> vos = new ArrayList<RecommendVo>();
			vos = recommendManager.getRecommendWorks(page_index, page_size,
					type, "");
			// 获取图片
			List<ResultWork> workresult = getResultWorkList(vos);
			if (workresult.size() != 0) {
				workresult = recommendManager.tramsRecommendVo(workresult,
						userid, access_key, secret_key, work_download_url);
			}
			resultList.put("workList", workresult);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================获取首页作品异常=========================");
		}
		return result;
	}
	/**
	 * 推送邀请消息
	 * 
	 * @param userid
	 * @param content
	 * @param username
	 * @param device_sys
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/invite", method = RequestMethod.POST)
	public ResponseResult invite(String userid, String content,
			String username, String device_sys,String loginid) {
		ResponseResult result = null;
		try {
			boolean re = getPushInfo(userid, content, username,
					device_sys,loginid);
			if (re) {
				result = new ResponseResult("");
			}
		} catch (Exception e) {
			log.debug("=====================================发送邀请失败================================");
			e.printStackTrace();
		}
		return result;
	}

	//推送
	public boolean getPushInfo(String userid, String content, String username,String device_sys,String loginid) {
		boolean returnResult = false;
		try {
			Member mem=memberManager.getMemberByUserId(userid);
			Member loginm=memberManager.getMemberByUserId(loginid);
			if (device_sys.equals("Android")) {
				AndroidUnicast unicast = new AndroidUnicast(
						ContextConstants.UM_APPKEY,
						ContextConstants.UM_APPMASTERSECRET);
				unicast.setDeviceToken(mem.getUm_device_token());
				unicast.setTicker(loginm.getUsername());
				unicast.setTitle(loginm.getUsername());
				unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
				//自定义参数
				//unicast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_custom);
				unicast.setText(content);
				QiNiuManager qi=new QiNiuManager();
//				String urlKey=loginm.getAvatar();
//				if(!StringUtils.isNullOrEmpty(urlKey)){
//					String url=qi.getDownLoadUrl(access_key, secret_key, work_download_url+urlKey+"?imageView2/0/w/405/h/");
//					unicast.setImg(url);
//				}
				unicast.setCustomField("invitation");
				unicast.setExtraField("hostid", loginid);
				unicast.setExtraField("hostname", loginm.getUsername());
				unicast.goCustomAfterOpen("invitation");
				unicast.setProductionMode();
				returnResult = new PushClient().send(unicast);
			}
			if (device_sys.equals("ios")) {
				IOSCustomizedcast customizedcast = new IOSCustomizedcast(
						ContextConstants.UM_APPKEY,
						ContextConstants.UM_APPMASTERSECRET);
				customizedcast.setAlias(userid, userid);
				customizedcast.setAlert("测试");
				customizedcast.setBadge(0);
				customizedcast.setSound("default");
				// TODO set 'production_mode' to 'true' if your app is under
				// production mode
				customizedcast.setTestMode();
				returnResult = new PushClient().send(customizedcast);
			}
			if (returnResult) {
				NotifyVo vo = new NotifyVo();
				Long ltime=System.currentTimeMillis();
				vo.setTimestamp(String.valueOf(ltime));
				String uuid = UUID.randomUUID().toString().replace("-", "");
				vo.setId(uuid);
				vo.setMessage(content);
				vo.setTitle(loginm.getUsername());
				vo.setUserid(userid);
				vo.setState(ContextConstants.USER_STATE_NORMAL);
				vo.setNotify_type("invitation");
				vo.setCreatetime(new Timestamp(ltime));
				memberManager.doInsertNotify(vo);
			}
		} catch (Exception e) {
			log.error("==========================系统推送消息失败========================");
			e.printStackTrace();
		}
		return returnResult;
	}

	public List<ResultWork> getResultWorkList(List<RecommendVo> list) {
		List<ResultWork> result = new ArrayList<ResultWork>();
		for (int i = 0; i < list.size(); i++) {
			ResultWork rw = new ResultWork();
			rw.setGoodfield(list.get(i).getGoodfield());
			rw.setHeadurl(list.get(i).getHeadurl());
			rw.setTags(list.get(i).getTags());
			rw.setUserid(list.get(i).getUserid());
			rw.setUsername(list.get(i).getUsername());
			rw.setVoteNum(list.get(i).getVotenum());
			rw.setWorkid(list.get(i).getWorkid());
			rw.setChoiceshow(list.get(i).getChoiceshow());
			rw.setSquareshow(list.get(i).getSquareshow());
			if (!StringUtils.isNullOrEmpty(list.get(i).getWorkey())) {
				rw.setWorks(recommendManager.getBreviaryWork(access_key,
						secret_key, work_download_url, list.get(i).getWorkey(),
						list.get(i).getVideoimgkey()));
			} else {
				List<Works> bw = new ArrayList<Works>();
				rw.setWorks(bw);
			}
			result.add(rw);
		}
		return result;
	}

	/**
	 * 获取请求参数签名
	 * 
	 * @param service
	 * @param partner
	 * @param _input_charset
	 * @param notify_url
	 * @param out_trade_no
	 * @param subject
	 * @param payment_type
	 * @param seller_id
	 * @param total_fee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRequestSign", method = RequestMethod.POST)
	public ResponseResult getRequestSign(String service, String partner,
			String _input_charset, String notify_url, String out_trade_no,
			String subject, String payment_type, String seller_id,
			String total_fee) {
		ResponseResult result = null;
		String param = "service" + service + "&partner=" + partner
				+ "&_input_charset=" + _input_charset + "&notify_url="
				+ notify_url + "&out_trade_no=" + notify_url + "&subject="
				+ subject + "&payment_type=" + payment_type + "&seller_id="
				+ seller_id + "&total_fee=" + total_fee;
		// String requestSign=AlipaySignature.rsaSign(arg0, arg1, arg2);
		return result;

	}

	/**
	 * 手机支付结束 异步通知，记录订单交易信息
	 */
	@ResponseBody
	@RequestMapping(value = "/recordOrder", method = RequestMethod.GET)
	public void recordOrder(HttpServletResponse response,
			HttpServletRequest request) {
		String out_trade_no = request.getParameter("out_trade_no").toString();
		String subject = request.getParameter("subject").toString();
		String pay_type = request.getParameter("pay_type").toString();

		String trade_no = request.getParameter("trade_no").toString();
		String trade_status = request.getParameter("trade_status").toString();
		String seller_id = request.getParameter("seller_id").toString();

		String seller_email = request.getParameter("seller_email").toString();
		String buyer_id = request.getParameter("buyer_id").toString();
		String buyer_email = request.getParameter("buyer_email").toString();

		String total_fee = request.getParameter("total_fee").toString();
		String quantity = request.getParameter("quantity").toString();
		String price = request.getParameter("price").toString();

		String instruction = request.getParameter("instruction").toString();
		String gmt_create = request.getParameter("gmt_create").toString();
		String gmt_payment = request.getParameter("gmt_payment").toString();

		String refund_status = request.getParameter("refund_status").toString();
		String gtm_refund = request.getParameter("gtm_refund").toString();
		String userid = request.getParameter("userid").toString();
		String notify_id = request.getParameter("notify_id").toString();

		// 验证参数
		String c_out_trade_no = request.getParameter("c_out_trade_no")
				.toString();
		String c_total_fee = request.getParameter("c_total_fee").toString();
		String c_seller_id = request.getParameter("c_seller_id").toString();
		// 验证签名
		String sign = request.getParameter("sign").toString();// 支付宝返回sign
		// params 按顺序排序 拼接
		String publikey = "";
		String signString = "";
		String checkurl = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner="
				+ publikey + "&notify_id=" + notify_id;
		try {
			boolean presult = AlipaySignature.rsaCheckContent(signString, sign,
					publikey, "UTF-8");
			if (presult) {
				// 验证是否是支付宝发来的通知
				HttpClient client = new HttpClient();
				PostMethod post = new PostMethod(checkurl);
				log.debug("验证是否是支付宝发过来的通知:" + client.executeMethod(post));
				post.getResponseBodyAsString();
				if (post.getResponseBodyAsString().equals("true")) {
					response.getWriter().print("success");
					if (trade_status.equals("TRADE_SUCCESS")) {
						if (c_out_trade_no.equals(out_trade_no)
								&& c_total_fee.equals(total_fee)
								&& c_seller_id.equals(c_seller_id)) {
							// 插入数据库

						}
					}
				}
			}

		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
public void test() throws Exception{
	//Member mem=memberManager.getMemberByUserId("10cce5c4516849c9b15b8b9cf424fc8e");
	Member loginm=memberManager.getMemberByUserId("5b4f8c97d4fd48bb8e3c3a925bfcff3d");
	AndroidUnicast unicast = new AndroidUnicast(
			ContextConstants.UM_APPKEY,
			ContextConstants.UM_APPMASTERSECRET);
	unicast.setDeviceToken("Al6JXl05m16SM3oLEK5MW9nMYITqhx7oWPCbT9IIdER7");
	unicast.setTicker(loginm.getUsername());
	unicast.setTitle(loginm.getUsername());
	//自定义参数
	unicast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_custom);
	unicast.setText("tet");
	QiNiuManager qi=new QiNiuManager();
	String urlKey=loginm.getAvatar();
//	if(!StringUtils.isNullOrEmpty(urlKey)){
//		String url=qi.getDownLoadUrl(access_key, secret_key, work_download_url+urlKey+"?imageView2/0/w/40/h/40");
//		unicast.setImg(url);
//	}
	unicast.setCustomField("invitation");

	unicast.goAppAfterOpen();
	unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
	unicast.setProductionMode();
	new PushClient().send(unicast);
}
}

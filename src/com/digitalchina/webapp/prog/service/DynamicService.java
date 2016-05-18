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

import com.digitalchina.webapp.prog.business.DynamicManager;
import com.digitalchina.webapp.prog.business.MemberManager;
import com.digitalchina.webapp.prog.vo.CommentVo;
import com.digitalchina.webapp.prog.vo.DynamicVo;
import com.digitalchina.webapp.prog.vo.Dynamics;
import com.digitalchina.webapp.prog.vo.IsDynamicVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.NotifyVo;
import com.digitalchina.webapp.prog.vo.PageVo;
import com.digitalchina.webapp.prog.vo.PraiseVo;
import com.digitalchina.webapp.prog.vo.ResultDynamic;
import com.digitalchina.webapp.umpush.AndroidNotification;
import com.digitalchina.webapp.umpush.PushClient;
import com.digitalchina.webapp.umpush.android.AndroidUnicast;
import com.digitalchina.webapp.umpush.ios.IOSCustomizedcast;
import com.digitalchina.webapp.utils.ContextConstants;
import com.digitalchina.webapp.utils.ErrorConstants;
import com.digitalchina.webapp.utils.ResponseResult;
import com.qiniu.util.StringUtils;

@Service
@RequestMapping(value = "/dynamic")
public class DynamicService {

	Log log = LogFactory.getLog(DynamicService.class);

	@Autowired
	private DynamicManager dynamicManager;
	@Autowired
	private MemberManager memberManager;

	@Value("#{configProperties['page_size']}")
	public Integer page_size;
	@Value("#{configProperties['page_index']}")
	public Integer page_index;
	@Value("#{configProperties['access_key']}")
	public String access_key;
	@Value("#{configProperties['secret_key']}")
	public String secret_key;
	@Value("#{configProperties['work_download_url']}")
	public String work_download_url;

	/**
	 * 发表动态
	 * 
	 * @param content
	 *            动态内容
	 * @param userid
	 *            动态发布者
	 * @param isfacebook
	 *            是否同步到facebook朋友圈 0 否 1是
	 * @param isweixin
	 *            是否同步到微信朋友圈 0 否 1是
	 * @param location
	 *            发布所在地址
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/postDynamic", method = RequestMethod.POST)
	public ResponseResult postDynamic(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		ResponseResult result = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String param_work = sb.toString();
		JSONObject json_param = new JSONObject(param_work);
		String userid = json_param.get("userid").toString();
		String content = json_param.get("contents").toString();
		String location = json_param.get("location").toString();
		JSONArray array_works = json_param.getJSONArray("feeds");
		String workey = "";
		for (int i = 0; i < array_works.length(); i++) {
			String one_work = array_works.get(i).toString();
			JSONObject json_one = new JSONObject(one_work);
			if (!StringUtils
					.isNullOrEmpty(json_one.get("feeds_url").toString())) {
				workey += json_one.get("feeds_url").toString() + ",";
			}
		}
		String videoImg = "";
		JSONArray video_img = json_param.getJSONArray("feedsThumb");
		for (int j = 0; j < video_img.length(); j++) {
			String one_video = video_img.get(j).toString();
			JSONObject json_one_video = new JSONObject(one_video);
			if (!StringUtils.isNullOrEmpty(json_one_video.get("thumbnail")
					.toString())) {
				videoImg += json_one_video.get("thumbnail").toString() + ",";
			}
		}
		if (StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			DynamicVo vo = new DynamicVo();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			vo.setId(uuid);
			if (!StringUtils.isNullOrEmpty(content)) {
				vo.setContent(content);
			}
			if (!StringUtils.isNullOrEmpty(workey)) {
				vo.setImgurl(workey.substring(0, workey.length() - 1));
			}
			vo.setPraisnum(0);
			vo.setState(ContextConstants.USER_STATE_NORMAL);
			Long ltime = System.currentTimeMillis();
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
			if (!StringUtils.isNullOrEmpty(location)) {
				vo.setLocation(location);
			}
			dynamicManager.doReleaseDynamic(vo);
			result = new ResponseResult("");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============发表动态异常===============");
		}
		return result;
	}

	/**
	 * 获取动态
	 * 
	 * @param userid
	 *            用户id
	 * @param pagesize
	 *            每页显示数量
	 * @param pageindex
	 *            当前页
	 * @param type
	 *            获取类别 0 动态广场 1 我关注的人动态
	 */
	@ResponseBody
	@RequestMapping(value = "/getDynamicList", method = RequestMethod.POST)
	public ResponseResult getDynamicList(String userid, String pagesize,
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
			Long dynamicTotal = 0L;
			if (ContextConstants.USER_STATE_STOP.equals(type)) {
				dynamicTotal = dynamicManager.countDynamic("");
			}
			if (ContextConstants.USER_STATE_NORMAL.equals(type)) {
				if (StringUtils.isNullOrEmpty(userid)) {
					dynamicTotal = 0L;
				} else {
					dynamicTotal = dynamicManager
							.countMyAttentionDynamic(userid);
				}
			}
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
			List<IsDynamicVo> vos = new ArrayList<IsDynamicVo>();
			if (ContextConstants.USER_STATE_STOP.equals(type)) {
				vos = dynamicManager.getDynamicList(page_index, page_size, "");
			}
			if (ContextConstants.USER_STATE_NORMAL.equals(type)) {
				if (StringUtils.isNullOrEmpty(userid)) {
					vos = new ArrayList<IsDynamicVo>();
				} else {
					vos = dynamicManager.getMyAttentionDynamicList(page_index,
							page_size, userid);
				}
			}
			// 获取图片
			List<ResultDynamic> rd = getResultDynamicList(vos);
			List<ResultDynamic> list = dynamicManager.tramsRecommendVo(rd,
					userid, access_key, secret_key, work_download_url);
			resultList.put("dynamicList", list);
			// //获取发布需求需求
			// Map<String, Object>
			// rlist=getRequireMentList(type,page_size,page_index,userid,resultList);
			// //获取销售需求
			// Map<String, Object> slist=getSellList(type, page_size,
			// page_index, userid, rlist);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================获取动态内容异常=========================");
		}
		return result;
	}

	public List<ResultDynamic> getResultDynamicList(List<IsDynamicVo> list)
			throws Exception {
		List<ResultDynamic> result = new ArrayList<ResultDynamic>();
		for (int i = 0; i < list.size(); i++) {
			ResultDynamic rw = new ResultDynamic();
			if(StringUtils.isNullOrEmpty(String.valueOf(list.get(i).getTotalComments()))){
				rw.setTotalComments(0);
			}else{
				rw.setTotalComments(list.get(i).getTotalComments());
			}
		
			rw.setContents(list.get(i).getContents());
			rw.setCreatetime(Long.valueOf(list.get(i).getCreatetime()));
			rw.setHeadurl(list.get(i).getHeadurl());
			rw.setId(list.get(i).getId());
			rw.setIsLiked(list.get(i).getIsLiked());
			rw.setIsFollowed(list.get(i).getIsFollowed());
			rw.setLocation(list.get(i).getLocation());
			if(StringUtils.isNullOrEmpty(String.valueOf(list.get(i).getTotalComments()))){
				rw.setTotalLikes(0);
			}else{
				rw.setTotalLikes(list.get(i).getTotalLikes());
			}
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
	 * 加载更多评论
	 * 
	 * @param dynamicid
	 *            动态id
	 */
	@ResponseBody
	@RequestMapping(value = "/getMoreCommentList", method = RequestMethod.POST)
	public ResponseResult getMoreDynamicList(String dynamicid, String pageindex) {
		ResponseResult result = null;
		Map<String, Object> resultList = new HashMap<String, Object>();
		if (StringUtils.isNullOrEmpty(dynamicid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		if (!StringUtils.isNullOrEmpty(pageindex)) {
			page_index = Integer.parseInt(pageindex);
		}
		PageVo vo = new PageVo();
		try {
			Integer c_total_row = 0;// 总记录数
			Integer c_total_page = 0;// 总页数
			Long commentTotal = dynamicManager.countComment(dynamicid);
			c_total_row = commentTotal.intValue();
			c_total_page = (c_total_row % page_size == 0) ? (c_total_row / page_size)
					: (c_total_row / page_size) + 1;
			vo.setPageIndex(page_index);
			vo.setPageSize(page_size);
			vo.setTotalPage(c_total_page);
			vo.setTotalRow(c_total_row);
			resultList.put("pageVo", vo);
			if (page_index > c_total_page)
				page_index = c_total_page;
			if (page_index < 1)
				page_index = 1;
			List<CommentVo> commentList = dynamicManager.getCommentList(
					dynamicid, page_index, page_size);
			resultList.put("commentList", commentList);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.debug("============================加载更多评论异常==============================");
		}
		return result;
	}

	/**
	 * 发表评论
	 * 
	 * @param userid
	 *            评论者id
	 * @param commentid
	 *            评论从属（回复）
	 * @param content
	 *            评论内容
	 * @param dynamicid
	 *            动态id
	 * @param touserid
	 * @用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ResponseResult releaseComment(String userid, String content,
			String dynamicid, String commentid, String touserid,
			String device_sys) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(content)
				|| StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(dynamicid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			dynamicManager.doReleaseComment(userid, content, dynamicid,
					commentid, touserid);
			// 更新评论次数
			DynamicVo dynamicVo = dynamicManager.getDynamicById(dynamicid);
			int total = dynamicVo.getCommentnum() + 1;
			dynamicManager.doUpCommentNum(dynamicid, total);
			Long commentTotal = dynamicManager.countComment(dynamicid);
			List<CommentVo> lists = dynamicManager.getCommentList(dynamicid, 1,
					commentTotal.intValue());
			Member mem = memberManager.getMemberByUserId(userid);
			getPushInfo(dynamicVo.getUserid(), content, mem.getUsername(),
					device_sys, userid, dynamicid);
			result = new ResponseResult(lists);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("============发表评论异常=================");
			result = new ResponseResult(e);
		}
		return result;
	}

	// 推送
	public boolean getPushInfo(String userid, String content, String username,
			String device_sys, String loginid, String dynamicid) {
		boolean returnResult = false;
		try {
			Member mem = memberManager.getMemberByUserId(userid);
			Member loginm = memberManager.getMemberByUserId(loginid);
			if (device_sys.equals("Android")) {
				AndroidUnicast unicast = new AndroidUnicast(
						ContextConstants.UM_APPKEY,
						ContextConstants.UM_APPMASTERSECRET);
				unicast.setDeviceToken(mem.getUm_device_token());
				unicast.setTicker(loginm.getUsername());
				unicast.setTitle(loginm.getUsername());
				unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
				// 自定义参数
				// unicast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_custom);
				unicast.setText(content);
				unicast.setCustomField("moments");
				unicast.setExtraField("hostname", username);
				unicast.setExtraField("hostid", dynamicid);
				unicast.goCustomAfterOpen("moments");
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
				Long ltime = System.currentTimeMillis();
				vo.setTimestamp(String.valueOf(ltime));
				String uuid = UUID.randomUUID().toString().replace("-", "");
				vo.setId(uuid);
				vo.setMessage(content);
				vo.setTitle(loginm.getUsername());
				vo.setUserid(userid);
				vo.setState(ContextConstants.USER_STATE_NORMAL);
				vo.setNotify_type("moments");
				vo.setCreatetime(new Timestamp(ltime));
				vo.setDynamicid(dynamicid);
				memberManager.doInsertNotify(vo);
			}
		} catch (Exception e) {
			log.error("==========================评论推送消息失败========================");
			e.printStackTrace();
		}
		return returnResult;
	}

	/**
	 * 点赞,取消点赞功能
	 * 
	 * @param userid
	 *            用户id
	 * @param dynamicid
	 *            动态id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clickLikes", method = RequestMethod.POST)
	public ResponseResult clickLikes(String userid, String dynamicid) {
		Map<String, Object> resultList = new HashMap<String, Object>();
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(dynamicid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		String isLiked = "";
		int totalLikes = 0;
		try {
			PraiseVo pvo = dynamicManager.checkIsPoint(dynamicid, userid);
			DynamicVo dyvo = dynamicManager.getDynamicById(dynamicid);
			if (pvo == null) {
				PraiseVo vo = new PraiseVo();
				Timestamp time = new Timestamp(System.currentTimeMillis());
				vo.setCreatetime(time);
				vo.setState(ContextConstants.USER_STATE_NORMAL);
				vo.setUserid(userid);
				vo.setDynamicid(dynamicid);
				dynamicManager.doPointPraise(vo);
				int total = dyvo.getPraisnum() + 1;
				dynamicManager.doUpDynamicNumById(dynamicid, total);
				isLiked = ContextConstants.USER_STATE_NORMAL;
				totalLikes = total;
			} else {
				if (pvo.getState().equals(ContextConstants.USER_STATE_NORMAL)) {
					dynamicManager.doCanclePointPraise(userid, dynamicid,
							ContextConstants.USER_STATE_STOP);
					int total = dyvo.getPraisnum();
					if (total > 0) {
						total = total - 1;
						dynamicManager.doUpDynamicNumById(dynamicid, total);
						isLiked = ContextConstants.USER_STATE_STOP;
						totalLikes = total;
					}
				} else {
					dynamicManager.doCanclePointPraise(userid, dynamicid,
							ContextConstants.USER_STATE_NORMAL);
					int total = dyvo.getPraisnum() + 1;
					dynamicManager.doUpDynamicNumById(dynamicid, total);
					isLiked = ContextConstants.USER_STATE_NORMAL;
					totalLikes = total;
				}
			}
			resultList.put("isLiked", isLiked);
			resultList.put("totalLikes", totalLikes);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("============================点赞、取消报错==============================");
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 删除动态
	 * 
	 * @param dynamicid
	 *            动态id
	 */
	@ResponseBody
	@RequestMapping(value = "/delDynamic", method = RequestMethod.POST)
	public ResponseResult delDynamic(String dynamicid) {
		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(dynamicid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			dynamicManager.delDynamicById(dynamicid);
			result = new ResponseResult("");
		} catch (Exception e) {
			log.debug("======================删除动态失败====================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}

	/**
	 * 根据dynamicid 获取dynamicVo
	 * 
	 * @param dynamicid
	 *            动态id
	 */
	@ResponseBody
	@RequestMapping(value = "/getDynamicDetail", method = RequestMethod.POST)
	public ResponseResult getDynamicVo(String dynamicid, String userid) {
		ResponseResult result = null;
//		Map<String, Object> resultList = new HashMap<String, Object>();
		if (StringUtils.isNullOrEmpty(dynamicid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			IsDynamicVo vo = dynamicManager.getDynamicVoById(dynamicid);
			List<IsDynamicVo> list = new ArrayList<IsDynamicVo>();
			list.add(vo);
			// 获取图片
			List<ResultDynamic> rd = getResultDynamicList(list);
			List<ResultDynamic> listvo = dynamicManager.tramsRecommendVo(rd,
					userid, access_key, secret_key, work_download_url);
			//resultList.put("dynamicDetail", listvo.get(0));
			result = new ResponseResult(listvo.get(0));
		} catch (Exception e) {
			log.debug("======================获取动态异常====================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}
}

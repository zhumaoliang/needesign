package com.digitalchina.webapp.prog.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
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

import com.digitalchina.webapp.prog.business.RequirementManager;
import com.digitalchina.webapp.prog.vo.RequirementVo;
import com.digitalchina.webapp.prog.vo.SellVo;
import com.digitalchina.webapp.utils.ContextConstants;
import com.digitalchina.webapp.utils.ErrorConstants;
import com.digitalchina.webapp.utils.ResponseResult;
import com.qiniu.util.StringUtils;

@Service
@RequestMapping(value = "/require")
public class RequirementService {

	Log log = LogFactory.getLog(RequirementService.class);

	@Autowired
	private RequirementManager requirementManager;

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
	 * 发布需求
	 * 
	 * @param category
	 *            需求类别
	 * @param background
	 *            需求背景
	 * @param description
	 *            需求描述
	 * @param userid
	 *            用户id
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/postRequirement", method = RequestMethod.POST)
	public ResponseResult postRequirement(String category, String background,
			String description, String userid) throws IOException {

		ResponseResult result = null;
		if (StringUtils.isNullOrEmpty(category)
				|| StringUtils.isNullOrEmpty(background)
				|| StringUtils.isNullOrEmpty(description)
				|| StringUtils.isNullOrEmpty(userid)) {
			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
					ErrorConstants.PARAMNULL_MSG, "");
			return result;
		}
		try {
			RequirementVo vo = new RequirementVo();
			vo.setBackground(background);
			vo.setCategory(category);
			Long ltime=System.currentTimeMillis();
			vo.setTimestamp(String.valueOf(ltime));
			Timestamp time = new Timestamp(ltime);
			vo.setCreatetime(time);
			vo.setDescription(description);
			String uuid = UUID.randomUUID().toString().replace("-", "");
			vo.setId(uuid);
			vo.setState(ContextConstants.USER_STATE_NORMAL);
			vo.setUserid(userid);
			vo.setType(ContextConstants.USER_STATE_STOP);
			requirementManager.doRequirement(vo);
			result = new ResponseResult("");
		} catch (Exception e) {
			log.debug("=============================发布需求异常=========================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}
	
	

	// @ResponseBody
	// @RequestMapping(value = "/getRequirementList", method =
	// RequestMethod.POST)
	// public ResponseResult getRequirementList(String pagesize, String
	// pageindex,String userid) {
	// ResponseResult result = null;
	// Map<String, Object> resultList = new HashMap<String, Object>();
	// Integer total_row = 0;// 总记录数
	// Integer total_page = 0;// 总页数
	// if (!StringUtils.isNullOrEmpty(pagesize)) {
	// page_size = Integer.parseInt(pagesize);
	// }
	// if (!StringUtils.isNullOrEmpty(pageindex)) {
	// page_index = Integer.parseInt(pageindex);
	// }
	// PageVo pagevo = new PageVo();
	// try {
	// Long total = 0L;
	// total = requirementManager.countRequirement(userid);
	// total_row = total.intValue();
	// pagevo.setTotalRow(total_row);
	// total_page = (total_row % page_size == 0) ? (total_row / page_size)
	// : (total_row / page_size) + 1;
	// pagevo.setTotalPage(total_page);
	// if (page_index > total_page)
	// page_index = total_page;
	// if (page_index < 1)
	// page_index = 1;
	// pagevo.setPageSize(page_size);
	// pagevo.setPageIndex(page_index);
	// resultList.put("pageVo", pagevo);
	// List<RequirementListVo> res = requirementManager.getRequireMentList(
	// page_index, page_size,userid);
	// //转化
	// List<RequirementListVo> rd=getResultRequirementList(res);
	// resultList.put("requirementList", rd);
	// result = new ResponseResult(resultList);
	// } catch (Exception e) {
	// e.printStackTrace();
	// result = new ResponseResult(e);
	// log.error("============================获取需求列表异常=========================");
	// }
	// return result;
	// }
	
	/**
	 * 发布销售需求
	 * 
	 * @param sell
	 *            卖什么
	 * @param price
	 *            价格
	 * @param description
	 *            描述
	 * @param userid
	 *            用户id
	 * @param photos
	 *           图片key
	 * @return
	 * @throws IOException
	 * @throws JSONException 
	 */
	@ResponseBody
	@RequestMapping(value = "/postSell", method = RequestMethod.POST)
	public ResponseResult postSell(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {

		ResponseResult result = null;
//		if (StringUtils.isNullOrEmpty(category)
//				|| StringUtils.isNullOrEmpty(background)
//				|| StringUtils.isNullOrEmpty(description)
//				|| StringUtils.isNullOrEmpty(userid)) {
//			result = new ResponseResult(ErrorConstants.PARAMNULL_CODE,
//					ErrorConstants.PARAMNULL_MSG, "");
//			return result;
//		}
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
		String sell = json_param.get("sell").toString();
		String price = json_param.get("price").toString();
		String description = json_param.get("description").toString();
		JSONArray array_works = json_param.getJSONArray("feeds");
		String photokey = "";
		for (int i = 0; i < array_works.length(); i++) {
			String one_work = array_works.get(i).toString();
			JSONObject json_one = new JSONObject(one_work);
			if (!StringUtils
					.isNullOrEmpty(json_one.get("feeds_url").toString())) {
				photokey += json_one.get("feeds_url").toString() + ",";
			}
		}
		try {
			SellVo vo = new SellVo();
			vo.setDescription(description);
			vo.setPrice(price);
			Long ltime=System.currentTimeMillis();
			vo.setTimestamp(String.valueOf(ltime));
			Timestamp time = new Timestamp(ltime);
			vo.setCreatetime(time);
			String uuid = UUID.randomUUID().toString().replace("-", "");
			vo.setId(uuid);
			vo.setState(ContextConstants.USER_STATE_NORMAL);
			vo.setUserid(userid);
			if(!StringUtils.isNullOrEmpty(photokey)){
				vo.setPhotos(photokey.substring(0, photokey.length() - 1));
			}
			vo.setSell(sell);
			vo.setType(ContextConstants.USER_STATE_NORMAL);
			requirementManager.doSell(vo);
			result = new ResponseResult("");
		} catch (Exception e) {
			log.debug("=============================发布销售需求异常=========================");
			e.printStackTrace();
			result = new ResponseResult(e);
		}
		return result;
	}
	
	
//	@ResponseBody
//	@RequestMapping(value = "/getSellList", method = RequestMethod.POST)
//	public ResponseResult getSellList(String pagesize, String pageindex,String userid) {
//		ResponseResult result = null;
//		Map<String, Object> resultList = new HashMap<String, Object>();
//		Integer total_row = 0;// 总记录数
//		Integer total_page = 0;// 总页数
//		if (!StringUtils.isNullOrEmpty(pagesize)) {
//			page_size = Integer.parseInt(pagesize);
//		}
//		if (!StringUtils.isNullOrEmpty(pageindex)) {
//			page_index = Integer.parseInt(pageindex);
//		}
//		PageVo pagevo = new PageVo();
//		try {
//			Long total = 0L;
//			total = requirementManager.countSell(userid);
//			total_row = total.intValue();
//			pagevo.setTotalRow(total_row);
//			total_page = (total_row % page_size == 0) ? (total_row / page_size)
//					: (total_row / page_size) + 1;
//			pagevo.setTotalPage(total_page);
//			if (page_index > total_page)
//				page_index = total_page;
//			if (page_index < 1)
//				page_index = 1;
//			pagevo.setPageSize(page_size);
//			pagevo.setPageIndex(page_index);
//			resultList.put("pageVo", pagevo);
//			List<SellListVo> res = requirementManager.getSellList(
//					page_index, page_size,userid);
//			//获取图片
//			List<SellListVo> rd = getResultSellList(res);
//			resultList.put("sellList", rd);
//			result = new ResponseResult(resultList);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = new ResponseResult(e);
//			log.error("============================获取需求列表异常=========================");
//		}
//		return result;
//	}
	

}

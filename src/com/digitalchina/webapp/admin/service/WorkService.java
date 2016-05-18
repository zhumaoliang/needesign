package com.digitalchina.webapp.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.webapp.prog.business.DynamicManager;
import com.digitalchina.webapp.prog.business.QiNiuManager;
import com.digitalchina.webapp.prog.business.RecommendManager;
import com.digitalchina.webapp.prog.vo.PageVo;
import com.digitalchina.webapp.prog.vo.RecommendVo;
import com.digitalchina.webapp.prog.vo.ResultWork;
import com.digitalchina.webapp.prog.vo.WorkVo;
import com.digitalchina.webapp.prog.vo.Works;
import com.digitalchina.webapp.utils.ContextConstants;
import com.digitalchina.webapp.utils.ResponseResult;
import com.qiniu.util.StringUtils;

/**
 * 广告service
 * 
 * @author tank
 * 
 */

@Service
@RequestMapping(value = "/workList")
public class WorkService {

	Log log = LogFactory.getLog(WorkService.class);

	@Autowired
	private RecommendManager recommendManager;
	@Autowired
	private DynamicManager dynamicManager;

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
	 * 获取作品列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWorkList", method = RequestMethod.POST)
	public ResponseResult getAdvertyList(String pageindex, String pagesize,
			String name, String show, String choice) {
		ResponseResult result = null;
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
			Long total = 0L;
			total = recommendManager.countWorkByCondition(name, show, choice);
			total_row = total.intValue();
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
			List<RecommendVo> res = recommendManager.getWorksByCondition(
					page_index, page_size, name, show, choice);
			// 获取图片
			List<ResultWork> rd = getResultWorkList(res);
			resultList.put("workList", rd);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================获取作品列表异常=========================");
		}
		return result;
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
	 * 显示作品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showWork", method = RequestMethod.POST)
	public ResponseResult showWork(String id, String type) {
		ResponseResult result = null;
		try {
			recommendManager.showWork(id, type);
			result = new ResponseResult("");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================设置作品显示异常=========================");
		}
		return result;
	}

	/**
	 * 显示作品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showChoice", method = RequestMethod.POST)
	public ResponseResult showChoice(String id, String type) {
		ResponseResult result = null;
		try {
			recommendManager.showChoise(id, type);
			result = new ResponseResult("");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================设置作品精选显示异常=========================");
		}
		return result;
	}

	/**
	 * 根据id 获取作品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWorkById", method = RequestMethod.POST)
	public ResponseResult getWorkById(String id) {
		ResponseResult result = null;
		try {
			WorkVo w = recommendManager.getWorkById(id);
			ResultWork r = getResultWork(w);
			result = new ResponseResult(r);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================根据id获取作品显示异常=========================");
		}
		return result;
	}

	public ResultWork getResultWork(WorkVo w) throws Exception {
		ResultWork rw = new ResultWork();
		String workey = w.getWorkey();
		String videokey = w.getVideoimgkey();
		if (StringUtils.isNullOrEmpty(workey)) {

		} else {
			int index = 0;
			List<Works> bw = new ArrayList<Works>();
			String[] keys = workey.split(",");
			QiNiuManager s = new QiNiuManager();
			for (int i = 0; i < keys.length; i++) {
				String originurl = s.getDownLoadUrl(access_key, secret_key,
						work_download_url + keys[i]);
				Works bwork = new Works();
				bwork.setOriginalWork(originurl);
				String[] types = keys[i].split("\\.");
				boolean typeTrue = ContextConstants.VEDIO_TYPR
						.contains(types[1]);
				if (typeTrue) {
					bwork.setType(ContextConstants.USER_STATE_NORMAL);
					if (!StringUtils.isNullOrEmpty(videokey)) {
						String[] videokeys = videokey.split(",");
						String breviaryurl = s.getDownLoadUrl(access_key,
								secret_key, work_download_url
										+ videokeys[index]);
						// String breviaryurl=videokeys[index];
						bwork.setThumbnailWork(breviaryurl);
						index = index + 1;
					}
				} else {
					bwork.setType(ContextConstants.USER_STATE_STOP);
					String breviaryurl = s.getDownLoadUrl(access_key,
							secret_key, work_download_url + keys[i]
									+ ContextConstants.IMAGE_SIZE);
					bwork.setThumbnailWork(breviaryurl);
				}

				bw.add(bwork);
			}
			rw.setWorks(bw);

		}
		return rw;
	}

	/**
	 * 获取生活圈列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDynamicList", method = RequestMethod.POST)
	public ResponseResult getDynamicList(String pageindex, String pagesize,
			String name,String content) {
		ResponseResult result = null;
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
			Long total = 0L;
		//	total = recommendManager.countWorkByCondition(name, show, choice);
			total_row = total.intValue();
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
//			List<RecommendVo> res = recommendManager.getWorksByCondition(
//					page_index, page_size, name, show, choice);
			// 获取图片
//			List<ResultWork> rd = getResultWorkList(res);
//			resultList.put("workList", rd);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================获取作品列表异常=========================");
		}
		return result;
	}
}

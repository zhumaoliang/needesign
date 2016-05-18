package com.digitalchina.webapp.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.webapp.prog.business.AdvertisementManager;
import com.digitalchina.webapp.prog.business.QiNiuManager;
import com.digitalchina.webapp.prog.vo.PageVo;
import com.digitalchina.webapp.prog.vo.ResultAdverisement;
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
@RequestMapping(value = "/adverty")
public class AdvertyService {

	Log log = LogFactory.getLog(AdvertyService.class);

	@Autowired
	private AdvertisementManager advertisementManager;

	
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
 * 获取广告列表
 * @return
 */
	@ResponseBody
	@RequestMapping(value = "/getAdvertyList", method = RequestMethod.POST)
	public ResponseResult getAdvertyList(String pageindex,String pagesize,
			String name) {
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
			total=advertisementManager.countAdvertyByCondition(name);
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
			List<ResultAdverisement> res=advertisementManager.getAdvertysByCondition(page_index, page_size,name);
			// 获取图片
			List<ResultAdverisement> rd = getResultAdList(res);
			resultList.put("adList", rd);
			result = new ResponseResult(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============================获取广告列表异常=========================");
		}
		return result;
	}
	public List<ResultAdverisement> getResultAdList(List<ResultAdverisement> list)
	throws Exception {
QiNiuManager qi=new QiNiuManager();
for (int i = 0; i < list.size(); i++) {
	String url=list.get(i).getAdurl();
	if(url.contains("http")){
		
	}else{
	if (!StringUtils.isNullOrEmpty(url)) {
		String adurl=qi.getDownLoadUrl(access_key, secret_key, work_download_url+url+ ContextConstants.IMAGE_SIZE);
		list.get(i).setAdurl(adurl);
	} 
	}
}
return list;
}
	/**
	 * 删除广告
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delAd", method = RequestMethod.POST)
	public ResponseResult delAd(String id) {
		ResponseResult result = null;
	try {
		advertisementManager.delAd(id);
		result = new ResponseResult("");
	} catch (Exception e) {
		result = new ResponseResult(e);
		log.error("============================删除广告异常=========================");
		e.printStackTrace();
	}
		return result;
	}
	/**
	 * 新增广告
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAd", method = RequestMethod.POST)
	public ResponseResult addAd(String companyname,String companylocation,String companyintroduce,String companyurl,String adbig,String adsmall) {
		ResponseResult result = null;
	try {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		advertisementManager.doAddAd(uuid, companyname, companylocation, companyintroduce, companyurl, adbig, adsmall, "");
		result = new ResponseResult("");
	} catch (Exception e) {
		result = new ResponseResult(e);
		log.error("============================添加广告异常=========================");
		e.printStackTrace();
	}
		return result;
	}
	/**
	 * 获取广告
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdById", method = RequestMethod.POST)
	public ResponseResult getAdById(String id) {
		ResponseResult result = null;
	try {
		ResultAdverisement re=advertisementManager.getAdById(id);
		result = new ResponseResult(re);
	} catch (Exception e) {
		result = new ResponseResult(e);
		log.error("============================根据id获取广告异常=========================");
		e.printStackTrace();
	}
		return result;
	}
	/**
	 * 编辑广告
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editAd", method = RequestMethod.POST)
	public ResponseResult editAd(String id,String companyname,String companylocation,String companyintroduce,String companyurl,String adbig,String adsmall,String isuse) {
		ResponseResult result = null;
	try {
		ResultAdverisement re=advertisementManager.getAdUsing();
		if(isuse.equals(ContextConstants.USER_STATE_NORMAL)){
			if(re==null){
				
			}else{
				if(re.getId().equals(id)){
					//advertisementManager.editAd(re.getId(), re.getCompanyname(), re.getCompanylocation(), re.getCompanylocation(), re.getLinkurl(), "", "", "", re.getIsusead());
				}else{
				advertisementManager.editAd(re.getId(), re.getCompanyname(), re.getCompanylocation(), re.getCompanylocation(), re.getLinkurl(), "", "", "", ContextConstants.USER_STATE_STOP);
				}
			}
			advertisementManager.editAd(id, companyname, companylocation, companyintroduce, companyurl, adbig, adsmall, "", ContextConstants.USER_STATE_NORMAL);
		}else{
			advertisementManager.editAd(id, companyname, companylocation, companyintroduce, companyurl, adbig, adsmall, "", ContextConstants.USER_STATE_STOP);
		}
		result = new ResponseResult("");
	} catch (Exception e) {
		result = new ResponseResult(e);
		log.error("============================编辑广告异常=========================");
		e.printStackTrace();
	}
		return result;
	}
}

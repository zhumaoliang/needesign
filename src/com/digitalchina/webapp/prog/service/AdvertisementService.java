package com.digitalchina.webapp.prog.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
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
import com.digitalchina.webapp.utils.ResponseResult;
import com.qiniu.util.StringUtils;

@Service
@RequestMapping(value = "/advert")
public class AdvertisementService {

	Log log = LogFactory.getLog(AdvertisementService.class);

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
	 * 获取广告
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdvertisement", method = RequestMethod.POST)
	public ResponseResult getAdvertisement() throws IOException, JSONException {
		ResponseResult result = null;
		try {
			ResultAdverisement re=advertisementManager.getAdverty();
			if(re!=null){
				if(!StringUtils.isNullOrEmpty(re.getAdurl())){
					if(re.getAdurl().contains("http")){
						
					}else{
					QiNiuManager qi=new QiNiuManager();
					String url=qi.getDownLoadUrl(access_key, secret_key, work_download_url+re.getAdurl());
					re.setAdurl(url);
					}
				}
				result = new ResponseResult(re);
			}else{
				ResultAdverisement r=new ResultAdverisement();
				r.setAdurl("");
				r.setLinkurl("");
				result = new ResponseResult(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseResult(e);
			log.error("============获取广告异常===============");
		}
		return result;
	}

	/**
	 * 获取广告列表
	 * 
	 * @param pagesize
	 *            每页显示数量
	 * @param pageindex
	 *            当前页
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdvertisementList", method = RequestMethod.POST)
	public ResponseResult getAdvertisementList(String pagesize,
			String pageindex) {
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
			total=advertisementManager.countAdverty();
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
			List<ResultAdverisement> res=advertisementManager.getAdvertys(page_index, page_size);
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
				String adurl=qi.getDownLoadUrl(access_key, secret_key, work_download_url+url);
				list.get(i).setAdurl(adurl);
			} 
			}
		}
		return list;
	}

}

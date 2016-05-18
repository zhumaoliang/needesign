package com.digitalchina.webapp.prog.business;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.prog.api.IAdvertisement;
import com.digitalchina.webapp.prog.dao.AdvertisementDao;
import com.digitalchina.webapp.prog.vo.ResultAdverisement;
import com.digitalchina.webapp.utils.ContextConstants;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.qiniu.util.StringUtils;



@Repository
public class AdvertisementManager implements IAdvertisement {

	@Autowired
	private  AdvertisementDao advertisementDao;

	@Override
	@Cacheable(value="advertyCache")
	public ResultAdverisement getAdverty() {
		return advertisementDao.getAdverty();
	}

	@Override
	@Cacheable(value="advertyCache")
	public Long countAdverty() {
		return advertisementDao.countAdverty();
	}

	@Override
	@Cacheable(value="advertyCache")
	public List<ResultAdverisement> getAdvertys(Integer page_index, Integer page_size) {
		Map<String, Object> params=new HashMap<String, Object>();
		int startIndex = (page_index - 1) * page_size;
//		int endIndex = 0;
//		if (page_index == total_page)
//			endIndex = total_row;
//		else
//			endIndex = page_index * page_size;
		params.put("start", startIndex);
		params.put("end", page_size);
		return advertisementDao.getAdvertys(params);
	}

	@Override
	@Cacheable(value="advertyCache")
	public List<ResultAdverisement> getAdvertysByCondition(Integer page_index,
			Integer page_size, String name) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		int startIndex = (page_index - 1) * page_size;
//		int endIndex = 0;
//		if (page_index == total_page)
//			endIndex = total_row;
//		else
//			endIndex = page_index * page_size;
		params.put("start", startIndex);
		params.put("end", page_size);
		if(StringUtils.isNullOrEmpty(name)|| "noname".equals(name)){
			params.put("name", "");
		}else{
			params.put("name", name);
		}
		return advertisementDao.getAdvertysByCondition(params);
	}

	@Override
	@Cacheable(value="advertyCache")
	public Long countAdvertyByCondition(String name) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		if(StringUtils.isNullOrEmpty(name)|| "noname".equals(name)){
			params.put("name", "");
		}else{
			params.put("name", name);
		}
		return advertisementDao.countAdvertyByCondition(params);
	}

	@Override
	@TriggersRemove(cacheName="advertyCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void delAd(String id) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		advertisementDao.delAd(params);
	}

	@Override
	@TriggersRemove(cacheName="advertyCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doAddAd(String id, String companyname, String companylocation,
			String companyintroduction, String companyurl, String adkey,
			String discoveryadkey, String userid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("companyname", companyname);
		params.put("companylocation", companylocation);
		params.put("companyintroduction", companyintroduction);
		params.put("companyurl", companyurl);
		params.put("adkey", adkey);
		params.put("status", ContextConstants.USER_STATE_NORMAL);
		params.put("isusead", ContextConstants.USER_STATE_STOP);
		params.put("discoveryadkey", discoveryadkey);
		params.put("createtime", new Timestamp(System.currentTimeMillis()));
		params.put("userid", userid);
		advertisementDao.addAd(params);
	}

	@Override
	@Cacheable(value="advertyCache")
	public ResultAdverisement getAdById(String id) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return advertisementDao.getAdById(params);
	}

	@Override
	@Cacheable(value="advertyCache")
	public ResultAdverisement getAdUsing() throws Exception {
		return advertisementDao.getAdUsing();
	}

	@Override
	@TriggersRemove(cacheName="advertyCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void editAd(String id,String companyname,String companylocation,String companyintroduction,String companyurl,String adkey,String discoveryadkey,String userid,String isuse) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("companyname", companyname);
		params.put("companylocation", companylocation);
		params.put("companyintroduction", companyintroduction);
		params.put("companyurl", companyurl);
		if(StringUtils.isNullOrEmpty(adkey)){
			params.put("adkey", "");
		}else{
			params.put("adkey", adkey);
		}
		if(StringUtils.isNullOrEmpty(discoveryadkey)){
			params.put("discoveryadkey", "");
		}else{
			params.put("discoveryadkey", discoveryadkey);
		}
		params.put("isusead", isuse);
		params.put("updatetime", new Timestamp(System.currentTimeMillis()));
		params.put("userid", userid);
		advertisementDao.editAd(params);
	}
	

}

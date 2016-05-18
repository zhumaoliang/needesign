package com.digitalchina.webapp.prog.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.prog.api.IRecommend;
import com.digitalchina.webapp.prog.dao.MemberDao;
import com.digitalchina.webapp.prog.dao.RecommendDao;
import com.digitalchina.webapp.prog.vo.AttentionVo;
import com.digitalchina.webapp.prog.vo.RecommendVo;
import com.digitalchina.webapp.prog.vo.ResultTags;
import com.digitalchina.webapp.prog.vo.ResultWork;
import com.digitalchina.webapp.prog.vo.TagsVo;
import com.digitalchina.webapp.prog.vo.WorkVo;
import com.digitalchina.webapp.prog.vo.WorkVoteVo;
import com.digitalchina.webapp.prog.vo.Works;
import com.digitalchina.webapp.utils.ContextConstants;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.qiniu.util.StringUtils;

@Repository
public class RecommendManager implements IRecommend {

	@Autowired
	private RecommendDao recommendDao;
	@Autowired
	private MemberDao memberDao;

	
	
	@Override
	@Cacheable(value="workCache")
	public List<RecommendVo> getRecommendWorks(Integer page_index,
			Integer page_size,
			String type,String userid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		int startIndex = (page_index - 1) * page_size;
		// int endIndex = 0;
		// if (page_index == total_page)
		// endIndex = total_row;
		// else
		// endIndex = page_index * page_size;
		params.put("start", startIndex);
		params.put("end", page_size);
		params.put("type", type);
		if(StringUtils.isNullOrEmpty(userid)){
			params.put("userid", "");
		}else{
			params.put("userid", userid);
		}
		List<RecommendVo> result = recommendDao.getRecommendWorks(params);
		return result;
	}

	@Override
	@Cacheable(value="workCache")
	public Long countRecommendWorks(String type,String userid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("userid", userid);
		Long total = recommendDao.countRecommendWorks(params);
		return total;
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doUploadWorks(WorkVo work) {
		recommendDao.uploadWorks(work);
	}

	@Override
	public WorkVo getWorkById(String workid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workid", workid);
		return recommendDao.getWorkById(params);
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doUpWorkNumById(String workid, int total) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("total", total);
		params.put("workid", workid);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		params.put("updatetime", time);
		recommendDao.updateWorkNumById(params);
	}

	public List<ResultWork> tramsRecommendVo(List<ResultWork> workresult,
			String userid,String access_key,String secret_key,String work_download_url ) throws Exception {
		QiNiuManager s=new QiNiuManager();
		for (int i = 0; i < workresult.size(); i++) {
			String headurlkey=workresult.get(i).getHeadurl();
			if(!StringUtils.isNullOrEmpty(headurlkey)){
				String headurl = s.getDownLoadUrl(access_key, secret_key,
						work_download_url + headurlkey);
				workresult.get(i).setHeadurl(headurl);
			}
			// 是否被投票or是否关注
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userid", userid);
			params.put("workid", workresult.get(i).getWorkid().toString());
//			if (StringUtils.isNullOrEmpty(userid)) {
//				workresult.get(i)
//						.setIsVoted(ContextConstants.USER_STATE_NORMAL);
//				workresult.get(i).setIsFollowed(
//						ContextConstants.USER_STATE_NORMAL);
//			} else {
				WorkVoteVo vo = recommendDao.checkIsVote(params);
				if (vo != null) {
					workresult.get(i).setIsVoted(
							ContextConstants.USER_STATE_NORMAL);
				} else {
					workresult.get(i).setIsVoted(
							ContextConstants.USER_STATE_STOP);
				}
				// 是否关注
				Map<String, Object> attentionparams = new HashMap<String, Object>();
				attentionparams.put("userid", userid);
				attentionparams.put("attention_userid", workresult.get(i).getUserid());
				AttentionVo avo = memberDao.isAttention(attentionparams);
				if (avo != null) {
					if(avo.getState().equals(ContextConstants.USER_STATE_NORMAL)){
						workresult.get(i).setIsFollowed(
								ContextConstants.USER_STATE_NORMAL);
					}else{
						workresult.get(i).setIsFollowed(
								ContextConstants.USER_STATE_STOP);
					}
				} else {
					workresult.get(i).setIsFollowed(
							ContextConstants.USER_STATE_STOP);
				}
			}
//		}
		return workresult;
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public WorkVoteVo getWorkVoteByParams(String workid, String userid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workid", workid);
		params.put("userid", userid);
		return recommendDao.checkIsVote(params);
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doAddWorkVote(WorkVoteVo vo) {
		recommendDao.addWorkVote(vo);
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doCancleVote(String userid, String workid, String state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("workid", workid);
		params.put("state", state);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		params.put("updatetime", time);
		recommendDao.cancleVote(params);
	}

	// 获取等比缩放图片
	public List<Works> getBreviaryWork(String access_key, String secret_key,
			String work_download_url, String workey, String videokey) {
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
			boolean typeTrue = ContextConstants.VEDIO_TYPR.contains(types[1]);
			if (typeTrue) {
				bwork.setType(ContextConstants.USER_STATE_NORMAL);
				if (!StringUtils.isNullOrEmpty(videokey)) {
					String[] videokeys = videokey.split(",");
					String breviaryurl = s.getDownLoadUrl(access_key,
							secret_key, work_download_url + videokeys[index]);
//					String breviaryurl=videokeys[index];
					bwork.setThumbnailWork(breviaryurl);
					index = index + 1;
				}
			} else {
				bwork.setType(ContextConstants.USER_STATE_STOP);
				String breviaryurl = s.getDownLoadUrl(access_key, secret_key,
						work_download_url + keys[i]
								+ ContextConstants.IMAGE_SIZE);
				bwork.setThumbnailWork(breviaryurl);
			}

			bw.add(bwork);
		}
		return bw;
	}

	@Override
	@Cacheable(value="workCache")
	public List<TagsVo> getTags() throws Exception {
		return recommendDao.getTags();
	}

	/**
	 * 获取tags
	 * 
	 * @param list
	 * @param language
	 * @return
	 */
	public List<ResultTags> getResultTags(List<TagsVo> list, String language) {
		List<ResultTags> rts = new ArrayList<ResultTags>();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				ResultTags rt = new ResultTags();
				List<String> childtype = new ArrayList<String>();
				List<String> child = new ArrayList<String>();
				if (language.toLowerCase().equals("cn")) {
					childtype.add(list.get(i).getParentchtype());
					String[] childs = list.get(i).getChirldchtype().split(",");
					for (int j = 0; j < childs.length; j++) {
						child.add(childs[j]);
					}
					rt.setChirldtype(child);
					rt.setParenttype(childtype);
				} else {
					childtype.add(list.get(i).getParententype());
					String[] childs = list.get(i).getChirldentype().split(",");
					for (int j = 0; j < childs.length; j++) {
						child.add(childs[j]);
					}
					rt.setChirldtype(child);
					rt.setParenttype(childtype);
				}
				rts.add(rt);
			}
		}
		return rts;
	}

	@Override
	@Cacheable(value="workCache")
	public List<RecommendVo> getWorksByCondition(Integer page_index,
			Integer page_size, String name,String show,String choice) throws Exception {
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
		if(show.equals("2")){
			params.put("show", "");
		}else{
			params.put("show", show);
		}
		if(choice.equals("2")){
			params.put("choice", "");
		}else{
			params.put("choice", choice);
		}
		return recommendDao.getWorksByCondition(params);
	}

	@Override
	@Cacheable(value="workCache")
	public Long countWorkByCondition(String name,String show,String choice) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		if(StringUtils.isNullOrEmpty(name)|| "noname".equals(name)){
			params.put("name", "");
		}else{
			params.put("name", name);
		}
		if(show.equals("2")){
			params.put("show", "");
		}else{
			params.put("show", show);
		}
		if(choice.equals("2")){
			params.put("choice", "");
		}else{
			params.put("choice", choice);
		}
		return recommendDao.countWorkByCondition(params);
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void showWork(String id, String type) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("squareshow", type);
		params.put("workid", id);
		params.put("updatetime", new Timestamp(System.currentTimeMillis()));
		recommendDao.showWork(params);
	}

	@Override
	@TriggersRemove(cacheName="workCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void showChoise(String id, String type) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("workid", id);
		params.put("choiceshow", type);
		params.put("updatetime", new Timestamp(System.currentTimeMillis()));
		recommendDao.showChoise(params);
	}
}

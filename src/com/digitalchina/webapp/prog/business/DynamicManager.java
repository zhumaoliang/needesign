package com.digitalchina.webapp.prog.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.prog.api.IDynamic;
import com.digitalchina.webapp.prog.dao.DynamicDao;
import com.digitalchina.webapp.prog.dao.MemberDao;
import com.digitalchina.webapp.prog.vo.AttentionVo;
import com.digitalchina.webapp.prog.vo.CommentVo;
import com.digitalchina.webapp.prog.vo.DynamicVo;
import com.digitalchina.webapp.prog.vo.Dynamics;
import com.digitalchina.webapp.prog.vo.IsDynamicVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.PraiseVo;
import com.digitalchina.webapp.prog.vo.ResultDynamic;
import com.digitalchina.webapp.utils.ContextConstants;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.qiniu.util.StringUtils;



@Repository
public class DynamicManager implements IDynamic {

	@Autowired
	private DynamicDao  dynamicDao;
	@Autowired
	private MemberDao memberDao;

	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doReleaseDynamic(DynamicVo vo) throws Exception {
		dynamicDao.releaseDynamic(vo);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public Long countDynamic(String userid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userid", userid);
	return dynamicDao.countDynamic(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public List<IsDynamicVo> getDynamicList(Integer page_index,
			Integer page_size,String userid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		int startIndex = (page_index - 1) * page_size;
//		int endIndex = 0;
//		if (page_index == total_page)
//			endIndex = total_row;
//		else
//			endIndex = page_index * page_size;
		params.put("start", startIndex);
		params.put("end", page_size);
		params.put("userid", userid);
		List<IsDynamicVo> result=dynamicDao.getDynamicList(params);
		return result;
	}
	public List<ResultDynamic> tramsRecommendVo(List<ResultDynamic> list,String userid,String access_key,String secret_key,String work_download_url ) throws Exception{
		QiNiuManager s=new QiNiuManager();
		for(int i=0;i<list.size();i++){
			String headurlkey=list.get(i).getHeadurl();
			if(!StringUtils.isNullOrEmpty(headurlkey)){
				String headurl = s.getDownLoadUrl(access_key, secret_key,
						work_download_url + headurlkey);
				list.get(i).setHeadurl(headurl);
			}
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("userid", userid);
			params.put("dynamicid", list.get(i).getId().toString());
//			if(StringUtils.isNullOrEmpty(userid)){
//				list.get(i).setIsLiked(ContextConstants.USER_STATE_NORMAL);
//			}else{
				PraiseVo vo=dynamicDao.checkIsPoint(params);
				if(vo !=null){
					if(vo.getState().equals(ContextConstants.USER_STATE_NORMAL)){
						list.get(i).setIsLiked(ContextConstants.USER_STATE_NORMAL);
					}else{
						list.get(i).setIsLiked(ContextConstants.USER_STATE_STOP);
					}
				}else{
					list.get(i).setIsLiked(ContextConstants.USER_STATE_STOP);
				}
//			}
			// 是否关注
			Map<String, Object> attentionparams = new HashMap<String, Object>();
			attentionparams.put("userid", userid);
			attentionparams.put("attention_userid", list.get(i).getUserid());
			AttentionVo avo = memberDao.isAttention(attentionparams);
			if (avo != null) {
				if(avo.getState().equals(ContextConstants.USER_STATE_NORMAL)){
					list.get(i).setIsFollowed(
							ContextConstants.USER_STATE_NORMAL);
				}else{
					list.get(i).setIsFollowed(
							ContextConstants.USER_STATE_STOP);
				}
			} else {
				list.get(i).setIsFollowed(
						ContextConstants.USER_STATE_STOP);
			}
		}
		return list;
	}

	@Override
	@Cacheable(value="dynamicCache")
	public DynamicVo getDynamicById(String dyid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("dyid", dyid);
		return dynamicDao.getDynamicById(params);
	}

	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doUpDynamicNumById(String dynamicid, int total) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("dyid", dynamicid);
		params.put("total", total);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		params.put("updatetime", time);
		dynamicDao.upDynamicNumById(params);
	}

	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doReleaseComment(String userid, String content,
			String dynamicid,String commentid,String touserid) {
		Map<String, Object> params=new HashMap<String, Object>();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		params.put("id", uuid);
		params.put("userid", userid);
		params.put("content", content);
		if(!StringUtils.isNullOrEmpty(commentid)){
			params.put("commentid", commentid);
		}else{
			params.put("commentid", "");
		}
		if(!StringUtils.isNullOrEmpty(touserid)){
			params.put("touserid", touserid);
		}else{
			params.put("touserid", "");
		}
		params.put("dynamicid", dynamicid);
		params.put("createtime", String.valueOf(System.currentTimeMillis()));
		dynamicDao.releaseComment(params);
	}

	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doUpCommentNum(String dynamicid, int total) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("dynamicid", dynamicid);
		params.put("total", total);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		params.put("updatetime", time);
		dynamicDao.doUpCommentNum(params);
	}


	@Override
	@Cacheable(value="dynamicCache")
	public List<CommentVo> getCommentList(
			String dynamicid,int pageindex,int pagesize) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		int startIndex = (pageindex - 1) * pagesize;
		params.put("start", startIndex);
		params.put("end", pagesize);
		params.put("dynamicid", dynamicid);
		List<CommentVo> result=dynamicDao.getCommentList(params);
		for(int i=0;i<result.size();i++){
			if(!StringUtils.isNullOrEmpty(result.get(i).getTouserid())){
				Map<String, Object> mparams=new HashMap<String, Object>();
				mparams.put("user_id", result.get(i).getTouserid());
				Member m=memberDao.getMemberByPhone(mparams);
				result.get(i).setTouser(m.getUsername());
			}
			result.get(i).setCreatetime(Long.valueOf(result.get(i).getCreatedate()));
		}
		
		return result;
	}
	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public PraiseVo checkIsPoint(String dynamicid, String userid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("dynamicid", dynamicid);
		return dynamicDao.checkIsPoint(params);
		
	}
	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doPointPraise(PraiseVo vo) throws Exception {
		dynamicDao.pointPraise(vo);
	}
	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doCanclePointPraise(String userid, String dynamicid,String state) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("dynamicid", dynamicid);
		params.put("state", state);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		params.put("updatetime", time);
		dynamicDao.canclePointPraise(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public Long countMyAttentionDynamic(String userid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userid", userid);
		return dynamicDao.countMyAttentionDynamic(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public List<IsDynamicVo> getMyAttentionDynamicList(Integer page_index,
			Integer page_size,
			String userid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		int startIndex = (page_index - 1) * page_size ;
//		int endIndex = 0;
//		if (page_index == total_page)
//			endIndex = total_row;
//		else
//			endIndex = page_index * page_size;
		params.put("start", startIndex);
		params.put("end", page_size);
		params.put("userid", userid);
		List<IsDynamicVo> result=dynamicDao.getMyAttentionDynamicList(params);
		return result;
	}
	//获取等比缩放图片
	public  List<Dynamics> getBreviaryWork(String access_key,String  secret_key,String work_download_url,String workey,String videokey) {
		int index = 0;
		List<Dynamics> bw = new ArrayList<Dynamics>();
		String[] keys = workey.split(",");
		QiNiuManager s = new QiNiuManager();
		for (int i = 0; i < keys.length; i++) {
			String originurl = s.getDownLoadUrl(access_key, secret_key,
					work_download_url+keys[i]);
			Dynamics bwork = new Dynamics();
			bwork.setOriginalUrl(originurl);
			String[] types = keys[i].split("\\.");
			boolean typeTrue = ContextConstants.VEDIO_TYPR.contains(types[1]);
			if (typeTrue) {
				bwork.setType(ContextConstants.USER_STATE_NORMAL);
				if (!StringUtils.isNullOrEmpty(videokey)) {
					String[] videokeys = videokey.split(",");
					String breviaryurl = s.getDownLoadUrl(access_key,
							secret_key,  work_download_url + videokeys[index]);
//					String breviaryurl=videokeys[index];
					bwork.setThumbnailUrl(breviaryurl);
					index = index + 1;
				}
			} else {
				bwork.setType(ContextConstants.USER_STATE_STOP);
				String breviaryurl =s.getDownLoadUrl(access_key, secret_key,
						work_download_url + keys[i]+ ContextConstants.IMAGE_SIZE); 
				bwork.setThumbnailUrl(breviaryurl);
			}
			
			bw.add(bwork);
		}
		return bw;
	}

	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void delDynamicById(String dynamicid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("dynamicid", dynamicid);
		dynamicDao.delDynamicById(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public Long countComment(String dynamicid) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("dynamicid", dynamicid);
		return dynamicDao.countComment(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public Long countDynamicByContion(String name, String content)
			throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		//int startIndex = (page_index - 1) * page_size;
//		int endIndex = 0;
//		if (page_index == total_page)
//			endIndex = total_row;
//		else
//			endIndex = page_index * page_size;
//		params.put("start", startIndex);
//		params.put("end", page_size);
		if(StringUtils.isNullOrEmpty(name)|| "noname".equals(name)){
			params.put("name", "");
		}else{
			params.put("name", name);
		}
		if(StringUtils.isNullOrEmpty(content)|| "nocontent".equals(content)){
			params.put("nocontent", "");
		}else{
			params.put("nocontent", content);
		}
		return dynamicDao.countDynamicByContion(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public List<IsDynamicVo> getDynamicListByContion(Integer page_index,
			Integer page_size, String name, String content) throws Exception {
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
		if(StringUtils.isNullOrEmpty(content)|| "nocontent".equals(content)){
			params.put("nocontent", "");
		}else{
			params.put("nocontent", content);
		}
		return dynamicDao.getDynamicListByContion(params);
	}

	@Override
	@Cacheable(value="dynamicCache")
	public IsDynamicVo getDynamicVoById(String id) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return dynamicDao.getDynamicVoById(params);
	}

}

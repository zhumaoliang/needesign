package com.digitalchina.webapp.prog.business;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.prog.api.IMember;
import com.digitalchina.webapp.prog.dao.MemberDao;
import com.digitalchina.webapp.prog.vo.AttentionMember;
import com.digitalchina.webapp.prog.vo.AttentionVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.NotifyVo;
import com.digitalchina.webapp.prog.vo.ResultNotify;
import com.digitalchina.webapp.prog.vo.VerifyCodeVo;
import com.digitalchina.webapp.utils.ContextConstants;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;


@Repository
public class MemberManager implements IMember {

	@Autowired
	private MemberDao memberDao;

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doRegisterMember(Member member) {
		memberDao.registerMember(member);
		
	}

	@Override
	@Cacheable(value="memberCache")
	public boolean checkPhone(String phone) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		Member member=memberDao.getMemberByPhone(params);
		if(member !=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	@Cacheable(value="memberCache")
	public Member getMemberByPhone(String phone) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		Member member=memberDao.getMemberByPhone(params);
		return member;
	}

	@Override
	@Cacheable(value="memberCache")
	public Member getMemberByUserId(String userid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", userid);
		Member member=memberDao.getMemberByPhone(params);
		return member;
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void updateMemberByUserId(String userid,String rongToken ) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("rongToken", rongToken);
		memberDao.upMemberByUserId(params);
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void updateUserPsw(String phone, String npsw) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("password", npsw);
		memberDao.upUserPsw(params);
	}

	

	@Override
	@Cacheable(value="memberCache")
	public AttentionVo getAttention(String loginid, String userid)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", loginid);
		params.put("attention_userid", userid);
		return memberDao.isAttention(params);
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doAttention(AttentionVo vo) throws Exception {
		memberDao.attention(vo);
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void cancleAttention(String userid, String attention_userid,String type)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("attention_userid", attention_userid);
		if(type.equals(ContextConstants.USER_STATE_STOP)){
			params.put("state", ContextConstants.USER_STATE_STOP);
		}else{
			params.put("state", ContextConstants.USER_STATE_NORMAL);
		}
		Timestamp time=new Timestamp(System.currentTimeMillis());
		params.put("updatetime", time);
		memberDao.cancleAttention(params);
	}

	@Override
	@Cacheable(value="memberCache")
	public List<AttentionMember> getAttentionList(String type,String userid,Integer page_index,
			Integer page_size, Integer total_page, Integer total_row)
			throws Exception {
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
		params.put("type", type);
		return memberDao.getAttentionList(params);
	}

	@Override
	@Cacheable(value="memberCache")
	public Long countAttention(String type, String userid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("type", type);
		return memberDao.countAttention(params);
	}

	@Override
	public List<VerifyCodeVo> getVerifyCode(String phone,String type)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("type", type);
		List<VerifyCodeVo> list=memberDao.getVerifyCode(params);
		return list;
		
	}

	@Override
	public void doAddCode(VerifyCodeVo vo) throws Exception {
		memberDao.addCode(vo);
	}

	@Override
	public void updateCode(int id,String isuse) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("isuse", isuse);
		memberDao.updateCode(params);
		
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void updateUserInfo(String userid, String avatar, String username,String userProfile) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("avatar", avatar);
		params.put("username", username);
		params.put("userprofile", userProfile);
		memberDao.updateUserInfo(params);
	}

	@Override
	@Cacheable(value="memberCache")
	public AttentionVo isAttention(String userid, String attention_userid)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("attention_userid", attention_userid);
		return memberDao.isAttention(params);
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void updateUmToken(String userid, String umtoken) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("umtoken", umtoken);
		memberDao.updateUmToken(params);
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doInsertNotify(NotifyVo notifyvo) {
		memberDao.insertNotify(notifyvo);
	}

	@Override
	@Cacheable(value="memberCache")
	public List<ResultNotify> getPushSystemInfo(String userid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		return memberDao.getPushSystemInfo(params);
	}

	@Override
	@Cacheable(value="memberCache")
	public Long countPushInfo(String userid,String notify_type) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("notify_type", notify_type);
		return memberDao.countPushInfo(params);
	}

	@Override
	@TriggersRemove(cacheName="memberCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void updateNotify(String id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		memberDao.updateNotify(params);
	}

	
	

}

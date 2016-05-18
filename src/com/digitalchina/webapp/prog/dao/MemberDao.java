package com.digitalchina.webapp.prog.dao;

import java.util.List;
import java.util.Map;

import com.digitalchina.webapp.prog.vo.AttentionMember;
import com.digitalchina.webapp.prog.vo.AttentionVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.NotifyVo;
import com.digitalchina.webapp.prog.vo.ResultNotify;
import com.digitalchina.webapp.prog.vo.VerifyCodeVo;



public interface MemberDao  {

	/**
	 * 用户注册
	 * @param member
	 */
	public void registerMember(Member member);
	/**
	 * 手机号认证 or获取用户信息
	 * @param params
	 * @return
	 */
	public Member getMemberByPhone(Map<String, Object> params); 
	/**
	 * 根据用户id,更改用户信息
	 * @param params
	 * @return
	 */
	public void upMemberByUserId(Map<String, Object> params); 
	/**
	 * 根据id,更改用户密码
	 * @param params
	 * @return
	 */
	public void upUserPsw(Map<String, Object> params); 
	/**
	 * 获取关注对象
	 * @param loginid
	 * @param followid
	 * @return
	 * @throws Exception
	 */
	public AttentionVo isAttention(Map<String, Object> params) throws Exception;
	/**
	 * 关注
	 * @param member
	 */
	public void attention(AttentionVo vo) throws Exception;
	/**
	 * 取消关注
	 * @param works
	 */
	public void cancleAttention(Map<String, Object> params);
	/**
	 * 获取关注列表
	 * @param type
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<AttentionMember> getAttentionList(Map<String, Object> params) throws Exception;
	/**
	 * 统计关注数量
	 * @param type
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public Long countAttention(Map<String, Object> params) throws Exception;
	/**
	 * 获取手机验证码
	 * @param type
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<VerifyCodeVo> getVerifyCode(Map<String, Object> params) throws Exception;
	/**
	 * 插入手机验证码
	 * @param member
	 */
	public void addCode(VerifyCodeVo vo) throws Exception;
	/**
	 * 修改code
	 * @param type
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public void updateCode(Map<String, Object> params) throws Exception;
	/**
	 * 根据id,修改用户信息
	 * @param email
	 * @param avatar
	 * @param username
	 * @return
	 */
	public void updateUserInfo(Map<String, Object> params) throws Exception; 
	/**
	 * 根据用户id，修改用户友盟token
	 * @param userid
	 * @param umtoken
	 */
	public void updateUmToken(Map<String, Object> params) throws Exception;
	/**
	 * 插入推送消息
	 * @param notifyvo
	 */
	public void insertNotify(NotifyVo notifyvo);
	/**
	 * 获取系统推送消息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<ResultNotify> getPushSystemInfo(Map<String, Object> params) throws Exception;
	/**
	 * 判断是否有新通知
	 */
	public Long countPushInfo(Map<String, Object> params) throws Exception;
	/**
	 * 更改通知状态
	 */
	public void updateNotify(Map<String, Object> params) throws Exception;
}

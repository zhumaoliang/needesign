package com.digitalchina.webapp.prog.api;

import java.util.List;

import com.digitalchina.webapp.prog.vo.AttentionMember;
import com.digitalchina.webapp.prog.vo.AttentionVo;
import com.digitalchina.webapp.prog.vo.Member;
import com.digitalchina.webapp.prog.vo.NotifyVo;
import com.digitalchina.webapp.prog.vo.ResultNotify;
import com.digitalchina.webapp.prog.vo.VerifyCodeVo;

/**
 * 用户接口
 * @author tank
 *
 */

public interface IMember {
	/**
	 * 用户注册
	 * @param member
	 */
	public void doRegisterMember(Member member);
	/**
	 * 电话认证
	 * @param email
	 * @return
	 */
	public boolean checkPhone(String phone); 
	/**
	 * 根据电话号码
	 * @param phone
	 * @return
	 */
	public Member getMemberByPhone(String phone); 
	/**
	 * 根据用户id
	 * @param userid
	 * @return
	 */
	public Member getMemberByUserId(String userid); 
	/**
	 * 根据用户id,更改用户信息
	 * @param userid
	 * @param rongToken
	 * @return
	 */
	public void updateMemberByUserId(String userid,String rongToken); 
	/**
	 * 根据id,更改用户密码
	 * @param phone
	 * @param npsw
	 * @return
	 */
	public void updateUserPsw(String phone,String npsw); 
	/**
	 * 根据id,修改用户信息
	 * @param email
	 * @param avatar
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	public void updateUserInfo(String userid,String avatar,String username,String userProfile) throws Exception; 
	/**
	 * 获取关注对象
	 * @param loginid
	 * @param followid
	 * @return
	 * @throws Exception
	 */
	public AttentionVo getAttention(String loginid,String followid) throws Exception;
	/**
	 * 关注
	 * @param member
	 */
	public void doAttention(AttentionVo vo) throws Exception;
	/**
	 * 取消关注
	 * @param loginid
	 * @param followid
	 */
	public void cancleAttention(String loginid,String followid,String type) throws Exception;
	/**
	 * 获取关注列表
	 * @param type
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<AttentionMember> getAttentionList(String type,String userid,Integer page_index,
			Integer page_size, Integer total_page, Integer total_row) throws Exception;
	/**
	 * 统计关注数量
	 * @param type
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public Long countAttention(String type,String userid) throws Exception;
	/**
	 * 获取手机验证码
	 * @param type
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<VerifyCodeVo> getVerifyCode(String phone,String type) throws Exception;
	/**
	 * 插入手机验证码
	 * @param member
	 */
	public void doAddCode(VerifyCodeVo vo) throws Exception;
	/**
	 * 根据手机号和type 修改code
	 * @param phone
	 * @param type
	 */
	public void updateCode(int id,String isuse) throws Exception;
	/**
	 * 根据用户id，修改用户友盟token
	 * @param userid
	 * @param umtoken
	 */
	public void updateUmToken(String userid,String umtoken) throws Exception;
	/**
	 * 获取关注对象
	 * @param loginid
	 * @param followid
	 * @return
	 * @throws Exception
	 */
	public AttentionVo isAttention(String userid,String attention_userid) throws Exception;
	/**
	 * 插入推送消息
	 * @param notifyvo
	 */
	public void doInsertNotify(NotifyVo notifyvo);
	/**
	 * 获取系统推送消息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<ResultNotify> getPushSystemInfo(String userid) throws Exception;
	/**
	 * 判断是否有新通知
	 */
	public Long countPushInfo(String userid,String notify_type) throws Exception;
	/**
	 * 更改通知状态
	 */
	public void updateNotify(String id) throws Exception;
}

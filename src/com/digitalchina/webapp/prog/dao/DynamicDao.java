package com.digitalchina.webapp.prog.dao;

import java.util.List;
import java.util.Map;

import com.digitalchina.webapp.prog.vo.CommentVo;
import com.digitalchina.webapp.prog.vo.DynamicVo;
import com.digitalchina.webapp.prog.vo.IsDynamicVo;
import com.digitalchina.webapp.prog.vo.PraiseVo;





public interface DynamicDao  {

	/**
	 * 发表动态
	 * @param vo
	 * @throws Exception
	 */
	public void releaseDynamic(DynamicVo vo) throws Exception;
	/**
	 * 根据参数 统计发布动态数量
	 * @throws Exception
	 */
	public Long countDynamic(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取广场动态
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IsDynamicVo> getDynamicList(Map<String, Object> params) throws Exception;
	/**
	 * 根据id获取动态信息
	 * @param id
	 * @return
	 */
	public IsDynamicVo getDynamicVoById(Map<String, Object> params);
	/**
	 * 获取我关注的用户动态
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IsDynamicVo> getMyAttentionDynamicList(Map<String, Object> params) throws Exception;
	/**
	 * 根据id获取动态
	 * @param dyid
	 */
	public DynamicVo getDynamicById(Map<String, Object> params) throws Exception;
	/**
	 * 根据id修改被点赞次数
	 * @param workid
	 */
	public void upDynamicNumById(Map<String, Object> params) throws Exception;
	/**
	 * 发表评论
	 * @param userid
	 * @param content
	 * @param imgurl
	 * @param dynamicid
	 */
	public void releaseComment(Map<String, Object> params);
	/**
	 * 根据id修改评论总数
	 * @param workid
	 */
	public void doUpCommentNum(Map<String, Object> params) throws Exception;
	/**
	 * 发表评论
	 * @param userid
	 * @param content
	 * @param imgurl
	 * @param commentid
	 */
	public void replayComment(Map<String, Object> params);
	/**
	 * 根据参数 统计所属id评论数量
	 * @return
	 * @throws Exception
	 */
	public Long countComment(Map<String, Object> params) throws Exception;
	/**
	 * 统计我关注的动态数量
	 * @return
	 * @throws Exception
	 */
	public Long countMyAttentionDynamic(Map<String, Object> params) throws Exception;
	/**
	 * 获取评论列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CommentVo> getCommentList(Map<String, Object> params) throws Exception;
	/**
	 * 判断用户是否投票
	 * @param userid
	 * @param workid
	 * @return
	 */
	public PraiseVo checkIsPoint(Map<String, Object> params);
	/**
	 * 作品点赞
	 * 
	 * @param vo
	 */
	public void pointPraise(PraiseVo vo)throws Exception;
	/**
	 * 取消点赞
	 * 
	 * @param params
	 */
	public void canclePointPraise(Map<String, Object> params)throws Exception;
	/**
	 * 根据动态id,删除动态
	 * @param dynamicid
	 */
	public void delDynamicById(Map<String, Object> params);
	/**
	 * 获取动态详细信息
	 * @param dynamicid
	 * @return
	 */
	public IsDynamicVo getDynamicDetail(Map<String, Object> params);
	/**
	 * 根据条件统计生活圈
	 * @param name
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public Long countDynamicByContion(Map<String, Object> params)throws Exception;
	/**
	 * 根据条件查询列表
	 * @param page_index
	 * @param page_size
	 * @param name
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public List<IsDynamicVo> getDynamicListByContion(Map<String, Object> params) throws Exception;
}

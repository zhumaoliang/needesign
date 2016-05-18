package com.digitalchina.webapp.prog.dao;

import java.util.List;
import java.util.Map;

import com.digitalchina.webapp.prog.vo.RecommendVo;
import com.digitalchina.webapp.prog.vo.TagsVo;
import com.digitalchina.webapp.prog.vo.WorkVo;
import com.digitalchina.webapp.prog.vo.WorkVoteVo;




public interface RecommendDao  {
	/**
	 * 作品上传
	 * @param work
	 */
	public void uploadWorks(WorkVo work);
	/**
	 * 判断用户是否投票
	 * @param userid
	 * @param workid
	 * @return
	 */
	public WorkVoteVo checkIsVote(Map<String, Object> params);
	/**
	 * 插入投票数据
	 */
	public void addWorkVote(WorkVoteVo vo);
	/**
	 * 取消投票
	 */
	public void cancleVote(Map<String, Object> params);

	/**
	 * 获取推荐作品
	 * @return
	 * @throws Exception
	 */
	public List<RecommendVo> getRecommendWorks(Map<String, Object> params);
	/**
	 * 统计推荐作品数量
	 * @return
	 * @throws Exception
	 */
	public Long countRecommendWorks(Map<String, Object> params) throws Exception;
	
	
	/**
	 * 根据id获取作品
	 * @param workid
	 */
	public WorkVo getWorkById(Map<String, Object> params) throws Exception;
	/**
	 * 根据id修改被点赞次数
	 * @param workid
	 */
	public void updateWorkNumById(Map<String, Object> params) throws Exception;
	/**
	 * 根据语言获取tags
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public List<TagsVo>  getTags() throws Exception; 
	/**
	 * 根据条件查询获取作品列表
	 */
	public List<RecommendVo> getWorksByCondition(Map<String, Object> params) throws Exception;
	/**
	 * 根据条件统计作品数量
	 */
	public Long countWorkByCondition(Map<String, Object> params) throws Exception;
	/**
	 * 根据id修改作品显示状态
	 * @param id
	 * @param type
	 */
	public void showWork(Map<String, Object> params) throws Exception;
	/**
	 * 根据id修改作品精选状态
	 * @param id
	 * @param type
	 */
	public void showChoise(Map<String, Object> params) throws Exception;
	
}

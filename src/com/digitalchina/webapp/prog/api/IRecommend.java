package com.digitalchina.webapp.prog.api;

import java.util.List;

import com.digitalchina.webapp.prog.vo.RecommendVo;
import com.digitalchina.webapp.prog.vo.TagsVo;
import com.digitalchina.webapp.prog.vo.WorkVo;
import com.digitalchina.webapp.prog.vo.WorkVoteVo;

/**
 * 作品操接口
 * 
 * @author tank
 * 
 */

public interface IRecommend {
	/**
	 * 作品上传
	 * @param works
	 */
	public void doUploadWorks(WorkVo work);
	/**
	 * 根据workid,userid 判断是否已投票
	 */
	public WorkVoteVo getWorkVoteByParams(String workid,String userid);
	/**
	 * 插入投票数据
	 */
	public void doAddWorkVote(WorkVoteVo vo);
	/**
	 * 取消投票
	 */
	public void doCancleVote(String userid, String workid,String state);
	/**
	 * 统计推荐作品数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long countRecommendWorks(String type,String userid) throws Exception;

	/**
	 * 获取推荐作品
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RecommendVo> getRecommendWorks(Integer page_index,
			Integer page_size,String type,String userid) throws Exception;

	
	/**
	 * 根据id获取作品
	 * @param workid
	 */
	public WorkVo getWorkById(String workid) throws Exception;
	/**
	 * 根据id修改被点赞次数
	 * @param workid
	 */
	public void doUpWorkNumById(String workid,int total) throws Exception;
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
	public List<RecommendVo> getWorksByCondition(Integer page_index,
			Integer page_size,String name,String show,String choice) throws Exception;
	/**
	 * 根据条件统计作品数量
	 */
	public Long countWorkByCondition(String name,String show,String choice) throws Exception;
	/**
	 * 根据id修改作品显示状态
	 * @param id
	 * @param type
	 */
	public void showWork(String id,String type) throws Exception;
	/**
	 * 根据id修改作品精选状态
	 * @param id
	 * @param type
	 */
	public void showChoise(String id,String type) throws Exception;
	
   
}

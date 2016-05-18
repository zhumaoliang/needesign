package com.digitalchina.webapp.prog.api;

import java.util.List;

import com.digitalchina.webapp.prog.vo.CommentVo;
import com.digitalchina.webapp.prog.vo.DynamicVo;
import com.digitalchina.webapp.prog.vo.IsDynamicVo;
import com.digitalchina.webapp.prog.vo.PraiseVo;


/**
 * 动态接口
 * 
 * @author tank
 * 
 */

public interface IDynamic {
	/**
	 * 动态点赞
	 * 
	 * @param vo
	 */
	public void doPointPraise(PraiseVo vo)throws Exception;
	/**
	 * 发表动态
	 * @param vo
	 * @throws Exception
	 */
	public void doReleaseDynamic(DynamicVo vo) throws Exception;
	/**
	 * 根据参数 统计发布动态数量
	 * @throws Exception
	 */
	public Long countDynamic(String userid) throws Exception;
	/**
	 * 根据参数 统计评论数量
	 * @throws Exception
	 */
	public Long countComment(String dynamicid) throws Exception;
	/**
	 * 根据参数 统计发布动态数量
	 * @param userid
	 * @throws Exception
	 */
	public Long countMyAttentionDynamic(String userid) throws Exception;
	/**
	 * 获取发布动态
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IsDynamicVo> getDynamicList(Integer page_index,
			Integer page_size,String userid) throws Exception;
	/**
	 * 获取我关注的用户动态
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<IsDynamicVo> getMyAttentionDynamicList(Integer page_index,
			Integer page_size,String userid) throws Exception;

	/**
	 * 根据id获取动态
	 * @param dyid
	 */
	public DynamicVo getDynamicById(String dyid) throws Exception;
	/**
	 * 根据动态id修改被点赞次数
	 * @param dynamicid
	 *        动态id
	 * @param total
	 *        次数
	 */
	public void doUpDynamicNumById(String dynamicid,int total) throws Exception;
	/**
	 * 发表评论
	 * 
	 * @param userid
	 *        评论者
	 * @param commentid
	 *        评论从属（回复）
	 * @param content
	 *        评论内容
	 * @param dynamicid 
	 *        动态id
	 * @param touserid 
	 *         @用户id
	 * @return
	 */
	public void doReleaseComment(String userid,String content,String dynamicid,String commentid,String touserid) throws Exception;
	/**
	 * 根据动态id修改被评论次数
	 * @param dynamicid
	 *        动态id
	 * @param total
	 *        次数
	 */
	public void doUpCommentNum(String dynamicid,int total) throws Exception;
	/**
	 * 根据id获取动态信息
	 * @param id
	 * @return
	 */
	public IsDynamicVo getDynamicVoById(String id);
	/**
	 * 回复评论
	 * @param userid
	 * @param content
	 * @param imgurl
	 * @param commentid
	 */
	//public void doReplayComment(String userid,String content,String imgurl,String commentid) throws Exception;
	/**
	 * 获取评论
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CommentVo> getCommentList(String dynamicid,int pageindex,int pagesize) throws Exception;
	/**
	 * 判断是否已被点赞
	 * @param dynamicid
	 *        动态id
	 * @param userid
	 *        用户id
	 */
	public PraiseVo checkIsPoint(String dynamicid,String  userid) throws Exception;
	/**
	 * 取消点赞
	 * @param userid
	 *        用户id
	 * @param dynamicid
	 *        动态id
	 * @param state
	 *        状态 0 取消 1 点赞
	 * @throws Exception
	 */
	public void doCanclePointPraise(String userid,String dynamicid,String state) throws Exception;
	/**
	 * 根据动态Id 删除动态
	 * @param dynamicid
	 */
	public void delDynamicById(String dynamicid)throws Exception;
	/**
	 * 根据条件统计生活圈
	 * @param name
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public Long countDynamicByContion(String name,String content)throws Exception;
	/**
	 * 根据条件查询列表
	 * @param page_index
	 * @param page_size
	 * @param name
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public List<IsDynamicVo> getDynamicListByContion(Integer page_index,
			Integer page_size,String name,String content) throws Exception;
	

}

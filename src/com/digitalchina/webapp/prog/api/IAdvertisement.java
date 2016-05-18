package com.digitalchina.webapp.prog.api;

import java.util.List;

import com.digitalchina.webapp.prog.vo.ResultAdverisement;


/**
 * 广告接口
 * 
 * @author tank
 * 
 */

public interface IAdvertisement {
	/**
	 * 获取广告
	 */
	public ResultAdverisement getAdverty() throws Exception;
	/**
	 * 统计广告数量
	 */
	public Long countAdverty() throws Exception;
	/**
	 * 统计广告数量
	 */
	public Long countAdvertyByCondition(String name) throws Exception;
	/**
	 * tab广告栏
	 */
	public List<ResultAdverisement> getAdvertys(Integer page_index,
			Integer page_size) throws Exception;
	/**
	 * 根据条件查询获取广告列表
	 */
	public List<ResultAdverisement> getAdvertysByCondition(Integer page_index,
			Integer page_size,String name) throws Exception;
	/**
	 * 删除广告
	 * id
	 * @throws Exception
	 */
	public void delAd(String id) throws Exception;
	/**
	 * 插入广告
	 * @param id
	 * @param companyname
	 * @param companylocation
	 * @param companyintroduction
	 * @param companyurl
	 * @param adkey
	 * @param discoveryadkey
	 * @param userid
	 * @throws Exception
	 */
	public void doAddAd(String id,String companyname,String companylocation,String companyintroduction,String companyurl,String adkey,String discoveryadkey,String userid)throws Exception;
/**
 * 根据id获取广告信息
 * @param id
 */
	public ResultAdverisement getAdById(String id) throws Exception;
	/**
	 * 获取开机正在显示的广告
	 * @param id
	 */
		public ResultAdverisement getAdUsing() throws Exception;
		/**
		 * 编辑广告
		 * @param id
		 */
			public void editAd(String id,String companyname,String companylocation,String companyintroduction,String companyurl,String adkey,String discoveryadkey,String userid,String isuse) throws Exception;
}

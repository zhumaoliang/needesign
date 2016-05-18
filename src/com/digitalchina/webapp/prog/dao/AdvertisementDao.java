package com.digitalchina.webapp.prog.dao;

import java.util.List;
import java.util.Map;

import com.digitalchina.webapp.prog.vo.ResultAdverisement;





public interface AdvertisementDao  {

	
	/**
	 * 获取广告
	 * @return
	 */
	public ResultAdverisement getAdverty();
	/**
	 * 统计广告数量
	 */
	public Long countAdverty();
	/**
	 * tab广告栏
	 */
	public List<ResultAdverisement> getAdvertys(Map<String, Object> param);
	/**
	 * 根据条件查询获取广告列表
	 */
	public List<ResultAdverisement> getAdvertysByCondition(Map<String, Object> param) throws Exception;
	/**
	 * 根据条件统计广告数量
	 */
	public Long countAdvertyByCondition(Map<String, Object> param);
	/**
	 * 删除广告
	 * id
	 * @throws Exception
	 */
	public void delAd(Map<String, Object> param) throws Exception;
	/**
	 * 插入广告
	 * map
	 * @throws Exception
	 */
	public void addAd(Map<String, Object> param)throws Exception;
	/**
	 * 根据id获取广告信息
	 * @param id
	 */
		public ResultAdverisement getAdById(Map<String, Object> param) throws Exception;
		/**
		 * 获取开机正在显示的广告
		 * @param id
		 */
			public ResultAdverisement getAdUsing() throws Exception;
			/**
			 * 编辑广告
			 * @param id
			 */
				public void editAd(Map<String, Object> param) throws Exception;
}

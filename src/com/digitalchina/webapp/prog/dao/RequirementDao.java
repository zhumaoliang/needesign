package com.digitalchina.webapp.prog.dao;

import com.digitalchina.webapp.prog.vo.RequirementVo;
import com.digitalchina.webapp.prog.vo.SellVo;





public interface RequirementDao  {

	/**
	 * 发布需求
	 * @param vo
	 */
	public void requirement(RequirementVo vo);

	
	/**
	 * 发布销售需求
	 * @param vo
	 * @throws Exception
	 */
	public void sell(SellVo vo) throws Exception;

	
	
}

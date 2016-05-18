package com.digitalchina.webapp.prog.api;

import com.digitalchina.webapp.prog.vo.RequirementVo;
import com.digitalchina.webapp.prog.vo.SellVo;


/**
 * 发布需求和销售接口
 * 
 * @author tank
 * 
 */

public interface IRequirement {
	/**
	 * 发布需求
	 * @param vo
	 * @throws Exception
	 */
	public void doRequirement(RequirementVo vo) throws Exception;
	
	/**
	 * 发布销售需求
	 * @param vo
	 * @throws Exception
	 */
	public void doSell(SellVo vo) throws Exception;

}

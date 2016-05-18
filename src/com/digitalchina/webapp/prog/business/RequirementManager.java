package com.digitalchina.webapp.prog.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.prog.api.IRequirement;
import com.digitalchina.webapp.prog.dao.RequirementDao;
import com.digitalchina.webapp.prog.vo.RequirementVo;
import com.digitalchina.webapp.prog.vo.SellVo;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;



@Repository
public class RequirementManager implements IRequirement {

	@Autowired
	private  RequirementDao requirementDao;

	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doRequirement(RequirementVo vo) throws Exception {
		requirementDao.requirement(vo);
	}
	@Override
	@TriggersRemove(cacheName="dynamicCache", when=When.AFTER_METHOD_INVOCATION, removeAll=true) 
	public void doSell(SellVo vo) throws Exception {
		requirementDao.sell(vo);
	}
}

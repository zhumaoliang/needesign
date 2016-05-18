package com.digitalchina.webapp.admin.business;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.admin.api.AdminApi;
import com.digitalchina.webapp.admin.dao.AdminDao;
import com.digitalchina.webapp.admin.vo.AdminVo;


@Repository
public class AdminManager implements AdminApi {

	@Autowired
	private AdminDao adminDao;

	@Override
	public AdminVo getAdminVo(String lgoin_name) throws Exception {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("name", lgoin_name);
         return adminDao.getAdminVo(params);
	}
	
	
}

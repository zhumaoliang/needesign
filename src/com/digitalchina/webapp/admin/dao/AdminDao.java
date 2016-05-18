package com.digitalchina.webapp.admin.dao;

import java.util.Map;

import com.digitalchina.webapp.admin.vo.AdminVo;

/**
 * 管理员操作dao
 * @author tank
 *
 */
public interface AdminDao {

	/**
     * 根据登陆用户名    获取 管理员
     * @param lgoin_name
     * @return
     */
	public AdminVo getAdminVo(Map<String, Object> params) throws Exception;
}

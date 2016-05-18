package com.digitalchina.webapp.admin.api;

import com.digitalchina.webapp.admin.vo.AdminVo;

/**
 *  admin操作api
 * @author tank
 *
 */
public interface AdminApi {
    /**
     * 根据登陆用户名    获取 管理员
     * @param lgoin_name
     * @return
     */
	public AdminVo getAdminVo(String lgoin_name) throws Exception;
	
}

package com.digitalchina.webapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.digitalchina.webapp.RongCloud.ApiHttpClient;
import com.digitalchina.webapp.RongCloud.models.FormatType;
import com.digitalchina.webapp.RongCloud.models.SdkHttpResult;
import com.digitalchina.webapp.utils.ContextConstants;

public class ApplicationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//初始化系统角色获取融云token

		try {
			SdkHttpResult resultp = ApiHttpClient.getToken(ContextConstants.RONGCLOUD_KEY,
					ContextConstants.RONGCLOUD_SECRET, ContextConstants.RONGCLOUD_ID,ContextConstants.RONGCLOUD_NAME,
					ContextConstants.RONGCLOUD_HEADURL, FormatType.json);
//			if (resultp.getHttpCode() == 200) {
//				Gson gson = new Gson();
//				TokenResult token = gson.fromJson(
//						resultp.getResult(), TokenResult.class);
//				memberManager.updateMemberByUserId(
//						member.getUser_id(), token.getToken());
//				member.setRongcloud_token(token.getToken());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

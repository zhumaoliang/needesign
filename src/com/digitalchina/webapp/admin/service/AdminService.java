package com.digitalchina.webapp.admin.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.webapp.admin.business.AdminManager;
import com.digitalchina.webapp.admin.utils.ContextConstants;
import com.digitalchina.webapp.admin.utils.ErrorConstants;
import com.digitalchina.webapp.admin.vo.AdminVo;
import com.digitalchina.webapp.utils.MD5Utils;
import com.digitalchina.webapp.utils.ResponseResult;

/**
 * admin操作service
 * 
 * @author tank
 * 
 */

@Service
@RequestMapping(value = "/admin")
public class AdminService {

	Log log = LogFactory.getLog(AdminService.class);

	@Autowired
	private AdminManager adminManager;

	/**
	 * 管理员登录
	 * @param name
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String login_name, String login_pwd,HttpServletRequest request,Model model) {
		 HttpSession session = request.getSession(true); 
		AdminVo vo = null;
		try {
			vo = adminManager.getAdminVo(login_name);
			model.addAttribute("login_name",login_name);
			model.addAttribute("login_pwd",login_pwd);
			if(vo == null){
				model.addAttribute("errorMsg",ErrorConstants.LOGINNAME_MSG);
				 return "login"; 
			}
			if(!MD5Utils.string2MD5(login_pwd).equals(vo.getPwd())){
				model.addAttribute("errorMsg",ErrorConstants.PWD_MSG);
				 return "login"; 
			}
			 session.setAttribute(ContextConstants.ADMIN_SESSION, vo.getName());
			 session.setMaxInactiveInterval(1800);
		} catch (Exception e) {
			log.error("=========================后台登陆操作异常=======================");
			e.printStackTrace();
		}
		  return "index";  
	}
	/**
	 * 注销、退出
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseResult logout(HttpServletRequest request) {
		ResponseResult result = null;
		 HttpSession session = request.getSession(true); 
		  Object obj = session.getAttribute(ContextConstants.ADMIN_SESSION);
		  if (obj == null || "".equals(obj.toString())) {  
	        	
	        } else{
	        	//移除session
	        	session.removeAttribute(ContextConstants.ADMIN_SESSION);
	        } 
		  result=new ResponseResult("");
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseResult test(String name, String pwd) {
		ResponseResult result = null;
		System.out.println("ppppp");
		return result;
	}
}

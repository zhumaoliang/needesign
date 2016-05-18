package com.digitalchina.webapp.prog.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.webapp.utils.ResponseResult;
import com.pili.Hub;
import com.pili.PiliException;
import com.pili.Stream;
import com.qiniu.Credentials;

@Service
@RequestMapping(value = "/plive")
public class PliveService {

	Log log = LogFactory.getLog(PliveService.class);

	@Value("#{configProperties['access_key']}")
	public  String access_key;
	@Value("#{configProperties['secret_key']}")
	public  String secret_key;
	@Value("#{configProperties['plive_hub_name']}")
	public  String plive_hub_name;
	
	/**
	 * 生成带授权凭证的 RTMP 推流地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRtmpUrl", method = RequestMethod.POST)
	public ResponseResult getRtmpUrl() {
		ResponseResult result = null;
		//初始化证书授权
        Credentials cre=new Credentials(access_key, secret_key);
        Hub hub = new Hub(cre, plive_hub_name);
        
        String title           = null;     //一般对应房间号
        String publishKey      = null;     // 流密钥，用于生成推流鉴权凭证
        String publishSecurity = null;     //  推流鉴权策略, 一般为"static", 针对安全要求较高的UGC推流建议用"dynamic"
        Stream stream = null;
        try {
			stream = hub.createStream(title, publishKey, publishSecurity);
			 System.out.println("hub.createStream:"+stream.toJsonString());
			 //获取rtmp推送地址
			 String publishUrl = stream.rtmpPublishUrl();
	         System.out.println("Stream rtmpPublishUrl()"+publishUrl);
	            result=new ResponseResult("");
		} catch (PiliException e) {
			log.debug("=======================视屏直播获取rtmp 报错===========================");
			e.printStackTrace();
			result=new ResponseResult(e);
		}
		return result;
	}
	/**
	 * 生成直播播放地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRtmpLiveUrl", method = RequestMethod.POST)
	public ResponseResult getRtmpLiveUrl() {
		ResponseResult result = null;
		//初始化证书授权
        Credentials cre=new Credentials(access_key, secret_key);
        Hub hub = new Hub(cre, plive_hub_name);
        
        String title           = null;     //一般对应房间号
        String publishKey      = null;     // 流密钥，用于生成推流鉴权凭证
        String publishSecurity = null;     //  推流鉴权策略, 一般为"static", 针对安全要求较高的UGC推流建议用"dynamic"
        Stream stream = null;
        try {
			stream = hub.createStream(title, publishKey, publishSecurity);
			 System.out.println("hub.createStream:"+stream.toJsonString());
			 //播放地址
			 String originUrl = stream.rtmpLiveUrls().get(Stream.ORIGIN);
	         System.out.println("Stream rtmpLiveUrls()"+originUrl);
	            result=new ResponseResult("");
		} catch (PiliException e) {
			log.debug("=======================视屏直播获取rtmp 报错===========================");
			e.printStackTrace();
			result=new ResponseResult(e);
		}
		return result;
	}
}

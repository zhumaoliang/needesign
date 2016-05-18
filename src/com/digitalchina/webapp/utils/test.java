package com.digitalchina.webapp.utils;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import com.digitalchina.webapp.RongCloud.ApiHttpClient;
import com.digitalchina.webapp.RongCloud.models.FormatType;
import com.digitalchina.webapp.RongCloud.models.SdkHttpResult;
import com.digitalchina.webapp.prog.business.QiNiuManager;
import com.digitalchina.webapp.umpush.AndroidNotification;
import com.digitalchina.webapp.umpush.PushClient;
import com.digitalchina.webapp.umpush.android.AndroidUnicast;



public class test {
	public static String getSix(){  
        Random rad=new Random();  
          
        String result  = rad.nextInt(100000000) +"";  
          
        if(result.length()!=6){  
            return getSix();  
        }  
        return result;  
    } 

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		JSONObject json_param = new JSONObject();
//		try {
//			json_param.put("userid", "111");
//			json_param.put("tags", "222");
//			JSONArray works=new JSONArray();
//			JSONObject j = new JSONObject();
//			j.put("work_url", "1112222.jpg");
//			j.put("work_url", "111222222222.jpg");
//			works.put(j);
//			json_param.put("works", works);
//			System.out.println(json_param.toString());
//		} catch (JSONException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		
		
		String uuid = UUID.randomUUID().toString().replace("-", "");
		//System.out.println(uuid);
//		String test="[100]";
//		System.out.println(Double.parseDouble(test.substring(1, test.length()-1).trim()));
//		String phone="17751682760";
//		Pattern regex = Pattern.compile(ContextConstants.regx);
//		Matcher matcher = regex.matcher(phone);
//		if (!matcher.matches()) {
//		System.out.println("1");
//		}else{
//			System.out.println(matcher.matches());
//		}
//		String yzcode = SmsApi.getVerifyCode();
//		String text="【Fashine】您的验证码是:"+yzcode;
//		String code = SmsApi.sendSms(ContextConstants.apikey, text, "+8618761535275");
//		JSONObject jsonObject = JSONObject.fromObject(code);
//		String codenum = jsonObject.getString("code");
//		System.out.println(codenum);
//		String s="{'rtnCode':'000000','rtnMsg':'本次请求成功!','responseData':{'totalLikes':2,'isLiked':'1'}}";
//		JSONObject j;
try {
//			j = new JSONObject(s);
//			String p=j.getString("rtnCode").toString();
//			System.out.println(p);
//			JSONObject pp=new JSONObject(j.get("responseData").toString());
//			System.out.println(pp.get("totalLikes"));
//		} catch (JSONException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		
//		String uuid = UUID.randomUUID().toString().replace("-", "")+System.currentTimeMillis();
//		String nonce = uuid;
//		System.out.println(nonce);
//		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date t=new Date(Long.valueOf("1458899290203"));
//		
//			System.out.println(f.format(t));
//	Map<String, Object> params=new HashMap<String, Object>();
//	params.put("xx", "");
		
		
//		System.out.println(System.currentTimeMillis());
//	String	key = "bmdehs6pdwnis";
//	String	secret = "jMiAMFwZ0N";
	
//	String key = "bmdehs6pdwnis";//替换成您的appkey
//	String secret = "jMiAMFwZ0N";//替换成匹配上面key的secret
		try {
//			String token="l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y:43M2WkyUiy4_0Xfs2sFAqYq7t7g=:eyJzY29wZSI6IndvcmtpbWciLCJkZWFkbGluZSI6MTQ2Mjg3MjU2M30=";
//			 String token1="l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y:FHR86rfF2bTn4lfK7h2Rh2ASRKk=:eyJzY29wZSI6IndvcmtpbWc6MzYwMCIsImRlYWRsaW5lIjoxNDYyODcyNjAzfQ==";
//
//						QiNiuManager s=new QiNiuManager();
//			String access_key="l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y";
//			String secret_key="pTHmBMY53iVmCXK2T1-4qb3lru5ZapbISRadsmaG";
////			Auth auth = Auth.create(access_key, secret_key);
////			String uptoken = auth.uploadToken("workimg", null);
////			System.out.println(uptoken);
//			String load="http://7xrx94.com1.z0.glb.clouddn.com/";
//			String headurl=s.getDownLoadUrl(access_key, secret_key, load+"b97899cf1460019967350.jpg");
//			System.out.println(headurl);
//			SdkHttpResult resultp = ApiHttpClient.getToken(
//					ContextConstants.RONGCLOUD_KEY,
//					ContextConstants.RONGCLOUD_SECRET, "bcf0bada991b47129b783573dffa66bb", "", "",
//					FormatType.json);
//			System.out.println(resultp.toString());
//			List<String> userids = new ArrayList<String>();
//			userids.add("4087ae921bd34c718a40c35602ff136c");	
//			ApiHttpClient.publishSystemMessage(ContextConstants.RONGCLOUD_KEY,
//					ContextConstants.RONGCLOUD_SECRET, "b2374c3d2af54032a58c699549b3d5f8", userids,
//					new TxtMessage("你好"), "pushContent",
//					"pushData", FormatType.json);
			AndroidUnicast unicast = new AndroidUnicast(
					ContextConstants.UM_APPKEY,
					ContextConstants.UM_APPMASTERSECRET);
			unicast.setDeviceToken("AgAssR7XTUZ6qkO6FlA2Ch4BYU03lW_rO-ZK-6ZQGpBm");
			unicast.setTicker(ContextConstants.RONGCLOUD_NAME);
			unicast.setTitle(ContextConstants.RONGCLOUD_NAME);
			//自定义参数
			unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			unicast.setImg("http://7xrx94.com1.z0.glb.clouddn.com/b97899cf1460019967350.jpg?imageView2/0/w/150/h/150&e=1747255297&token=l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y:PezbWksk_vKVqKQwbzp1XcUoxTE=");
			unicast.setText("测试");
			unicast.setCustomField("invitation");
			unicast.setExtraField("userid", "123");
			unicast.setExtraField("username", "123");
			unicast.goAppAfterOpen();

			unicast.setProductionMode();
			// Set customized fields
			unicast.setExtraField("test", "helloworld");
			boolean re=new PushClient().send(unicast);
			System.out.println(re+"==================re=========re");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		String uuid = UUID.randomUUID().toString().replace("-", "")+System.currentTimeMillis();
//		System.out.println(uuid);
//		System.out.println(MD5Utils.string2MD5(uuid));
//		String keys="dsdsa";
//		String[] types =keys.split(",");
//		System.out.println(types[0]);
//		boolean typeTrue = ContextConstants.VEDIO_TYPR.contains(types[1]);
//		String cc="cn";
//		System.out.println(cc.toLowerCase());
//		HttpClient client=new HttpClient();
//		 String url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=2088002396712354&notify_id=RqPnCoPT3K9%252Fvwbh3I%252BFioE227%252BPfNMl8jwyZqMIiXQWxhOCmQ5MQO%2B%252BaiGg";  
//	       PostMethod postMethod = new PostMethod(url);  
	       // 填入各个表单域的值  
//	       NameValuePair[] data = {  
//	               new NameValuePair("account", "yijianfeng_vip@163.com"),  
//	               new NameValuePair("nextUrl", ""),  
//	               new NameValuePair("lcallback", ""),  
//	               new NameValuePair("password    ", "******"),  
//	               new NameValuePair("persistent", "1"), };  
//	       // 将表单的值放入postMethod中  
//	       postMethod.setRequestBody(data);  
	       // 执行postMethod  
	      // int statusCode = 0;  
	     //  try {  
//	           statusCode = client.executeMethod(postMethod);  
//	    	   System.out.println("nihao");
//	    	   System.out.println("error："+client.executeMethod(postMethod));
//	    	// byte[]t=  postMethod.getResponseBody();
//	    	   
//	    	 System.out.println(postMethod.getResponseBodyAsString());
//	    	 if(postMethod.getResponseBodyAsString().equals("false")){
//	    		 System.out.println("nihao");
//	    	 }
//	       } catch (HttpException e) {  
//	           e.printStackTrace();  
//	       } catch (IOException e) {  
//	           e.printStackTrace();  
//	       }  
//		 String code="";
//		String [] b=code.split(",");
//		for(int i=0;i<b.length;i++){
//			System.out.println(b[i]);
//		}
//		 System.out.println(code);
//		String access_key="l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y";
//		String secret_key="pTHmBMY53iVmCXK2T1-4qb3lru5ZapbISRadsmaG";
//		String bucket_name="workimg";
	//QiNiuManager s = new QiNiuManager();
//		Auth auth = Auth.create(access_key, secret_key);
//		String uptoken = auth
//		.uploadToken(
//				bucket_name,null);
//	System.out.println(uptoken);
//String p="http://7xpv7y.com1.z0.glb.clouddn.com/82f4116e1458802907277.jpg";
//		String pr=s.getDownLoadUrl("l9g6EIYt9xR-QSx5FzrisZFwdth8cHHrqxDyhf1Y", "pTHmBMY53iVmCXK2T1-4qb3lru5ZapbISRadsmaG","http://7xrx94.com1.z0.glb.clouddn.com/dcef75ff1458735908415.jpg?imageView2/0/w/100/h/140");
//System.out.println(pr);
		//		String pr="020feb7b07934c3394b6ad35b6df08d6.jpg";
//		String []t=pr.split("\\.");
//		
//		System.out.println(t.length);
//		UploadManager upload = new UploadManager();
//		Auth auth=Auth.create("PczQNv44KzBjnf_BSyouwxETLlZsrxFscgUuIFNL", "Nq8Bxffa65CHTGC8lnHW7tXl68nN1DgKIG4CPQRY");
//		String uptoken = auth.uploadToken("kanling");
//		File f=new File("E:\111.jpg");
//	try {
//		upload.put(f, "test.jpg", uptoken);
//	} catch (QiniuException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
		// TODO Auto-generated method stub
//		String s = new String("20b75697d8bf931a6730662ae117c3bf"+new Date());
//		System.out.println("原始：" + s);
//		System.out.println("MD5后：" + MD5Utils.string2MD5(s));
//		System.out.println("解密的：" +MD5Utils.convertMD5(MD5Utils.convertMD5(s)));
//		System.out.println("uuid：" +UUID.randomUUID().toString().replace("-", ""));
//		Auth auth=Auth.create("PczQNv44KzBjnf_BSyouwxETLlZsrxFscgUuIFNL", "Nq8Bxffa65CHTGC8lnHW7tXl68nN1DgKIG4CPQRY");
	/*	String digitalSignature="dasasdas";
		String key="keyss";
		  String resetPassHref =  "http://localhost/checkLink?sid="
          + digitalSignature +"&userName=aa";
		String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href="
            + resetPassHref + " target='_BLANK'>" + resetPassHref
            + "</a>  或者    <a href=" + resetPassHref
            + " target='_BLANK'>点击我重新设置密码</a>"
            + "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'" + key
            + "\t" + digitalSignature;
		MailVo m=new MailVo();
		m.setContent(emailContent);
		m.setSubject("ni zhoahui de mima:");
		m.setTo("714515682@qq.com");
		SendMail  s=new SendMail("zhumaoliang@126.com", "zhumaoliang123");
		try {
			s.send(m);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/* Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
		 long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的
		 System.out.println(date);*/
//		int a=0;
//		  int b=10;
//		  int c=0;
//		  try{
//		   c=b/a;
//		   System.out.println("pp");
//		  }catch(Exception e){
//		   System.out.println("xx");
//		   return ;
//		  }
//		  System.out.println("kk");
	
//		String imagename="test.jpg";
//		String name = (imagename.substring(imagename.lastIndexOf(".")+1)).toLowerCase();
//		
//	System.out.println(name);
	

//        String dateStart = "2016-04-12 23:07:54";
        //String dateStop = String.valueOf(System.currentTimeMillis());
//        Timestamp ts = new Timestamp(System.currentTimeMillis());   
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//        Date dateStop=format.parse(dateStart);
//        System.out.println(dateStop.getTime());
       

//        Date d1 = null;
//        Date d2 = null;
//
//        try {
//            d1 = format.parse(dateStart);
//            d2 = format.parse(dateStop);
//
//            //毫秒ms
//            long diff = d2.getTime() - d1.getTime();
//
//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);
//
//           if(diffMinutes>=30L){
//        	   System.out.print(diffMinutes + " 分钟, ");
//           }
           
 

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
		

}

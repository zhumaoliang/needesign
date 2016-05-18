package com.digitalchina.webapp.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * 七牛服务上传
 * @author tank
 *
 */
public class QiNiuUtils  {
	@Value("#{configProperties['bucket_name']}")
	public   String bucket_name;
	

	/*  //ak
	 private static final String ACCESS_KEY = "PczQNv44KzBjnf_BSyouwxETLlZsrxFscgUuIFNL";    
	 //sk
	 private static final String SECRET_KEY = "Nq8Bxffa65CHTGC8lnHW7tXl68nN1DgKIG4CPQRY";  
	 //空间
	 private static final String BUCKET_NAME = "kanling";
	 
	 public static void uploadFile(String filePath, String fileName) {
		 File f=new File(filePath);
			UploadManager upload=new UploadManager();
			Auth auth=Auth.create(ACCESS_KEY, SECRET_KEY);
			String uptoken = null;
			String privatedownload="";
			uptoken = auth.uploadToken(BUCKET_NAME); 
			String baseUrl="http://kanling.qiniudn.com/test.jpg";
			privatedownload=auth.privateDownloadUrl(baseUrl);
			System.out.println("下载token:"+privatedownload);
			System.out.println(uptoken);   //输出上传凭证
			
		Response res;
		try {
			res = upload.put(f, fileName, uptoken);
			MyRet ret = res.jsonToObject(MyRet.class);
			System.out.println("Key:"+ret.getKey());
			System.out.println("Hash:"+ret.getHash());
			System.out.println("Height:"+ret.getHeight());
			System.out.println("Width:"+ret.getWidth());
			System.out.println("Fsize:"+ret.getFsize());
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
			
		}*/

		public static void main(String[] args) {
			try {
				//uploadFile("E:/111.jpg","test.jpg");  //第一个参数是本地文件路径，第二个参数是上传到七牛云之后的文件名称，由你来设定。
			QiNiuUtils q=new QiNiuUtils();
				System.out.println("空间:"+q.bucket_name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

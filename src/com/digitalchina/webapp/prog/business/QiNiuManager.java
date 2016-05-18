package com.digitalchina.webapp.prog.business;

/**
 * 七牛业务类上传图片
 */
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.digitalchina.webapp.prog.vo.QiNiuVo;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Repository
public class QiNiuManager {
	Logger log = org.slf4j.LoggerFactory.getLogger(QiNiuManager.class);

	/**
	 * 上传图片
	 * 
	 * @param filePath
	 * @param fileName
	 * @param accessKey
	 * @param secretKey
	 * @param bucketName
	 * @return
	 */
	public QiNiuVo uploadFile(byte[] data, String fileName, String accessKey,
			String secretKey, String bucketName) {
		UploadManager upload = new UploadManager();
		Auth auth = Auth.create(accessKey, secretKey);
		String uptoken = null;
		uptoken = auth.uploadToken(bucketName);
		Response res = null;
		QiNiuVo ret = null;
		try {
			res = upload.put(data, fileName, uptoken);
			ret = res.jsonToObject(QiNiuVo.class);
		} catch (QiniuException e) {
			e.printStackTrace();
			log.error("======================七牛上传图片报错位于QiNiuManager========================");
		}
		return ret;
	}
	/*// 设置预处理、去除非限定的策略字段
	@SuppressWarnings("unused")
	private String getUpToken3(String access_key,String secret_key,String bucket_name,String uuid ){
		Auth auth = Auth.create(access_key, secret_key);
		String uptoken = auth
				.uploadToken(
						bucket_name,
						uuid,
						3600,
						new StringMap()
						.putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
			           , true);
	   return uptoken;
	}*/
	/**
	 * 下载私有空间七牛文件
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @param downloadurl
	 * @param key
	 * @return
	 */
	public String getDownLoadUrl(String accessKey, String secretKey,
			String downloadurl) {
		//String baseUrl = downloadurl + key;
		Auth auth = Auth.create(accessKey, secretKey);
		String privatedownloadUrl = auth.privateDownloadUrl(downloadurl,311040000);
		return privatedownloadUrl;
	}
	/**
	 * 删除七牛上文件
	 * @param accessKey
	 * @param secretKey
	 * @param bucketName
	 * @param key
	 */
	public void delFile(String accessKey, String secretKey,
			String bucketName, String key){
		Auth auth = Auth.create(accessKey,secretKey);
		BucketManager bucketManager = new BucketManager(auth);
		try {
			bucketManager.delete(bucketName, key);
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("======================七牛删除失败位于QiNiuManager===========================");
		}
	}
}

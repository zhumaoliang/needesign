package com.digitalchina.webapp.utils;

import java.io.Serializable;



@SuppressWarnings("serial")
public class ResponseResult
implements Serializable
{
private String rtnCode;
private String rtnMsg;
private Object responseData;

public ResponseResult(String rtnCode, String rtnMsg, Object responseData)
{
  this.rtnCode = rtnCode;
  
  this.rtnMsg = rtnMsg;
  
  this.responseData = responseData;
}

public ResponseResult(Object responseData)
{
  if (responseData instanceof Exception)
  {
    this.rtnCode = "999999";
    this.rtnMsg = "本次请求失败!";
    this.responseData="";
  }
  else
  {
    this.rtnCode = "000000";
    this.rtnMsg = "本次请求成功!";
    this.responseData = responseData;
  }
}

public String getRtnCode()
{
  return this.rtnCode;
}

public String getRtnMsg()
{
  return this.rtnMsg;
}

public Object getResponseData()
{
  return this.responseData;
}

public void setRtnMsg(String rtnMsg)
{
  this.rtnMsg = rtnMsg;
}

public void setRtnCode(String rtnCode)
{
  this.rtnCode = rtnCode;
}
}

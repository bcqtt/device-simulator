package com.eg.egsc.scp.simulator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @Probject Name: usercenter-sig
 * @Path: UrlGenerate.java
 * @Create By 031440970
 * @Create In 2019年1月30日 下午4:21:13
 * TODO
 */

/**
 * @Class Name UrlGenerate
 * @Author 031440970
 * @Create In 2019年1月30日
 */
public class UrlGenerate {
  
  private static String appid = "100002";
  
  private static String appkey = "!CDappkey#";   //用户中心UAT
//  private static String appkey = "@cDappkey#";     //用户中心PRD
  
  private static String sign(Map<String, Object> param) {
    param.remove("appkey");
    TreeMap<String, Object> sortedParam = new TreeMap<>(param);
    StringBuilder paramBuilder = new StringBuilder();
    if (sortedParam.size() > 0) {
      sortedParam.forEach((k, v) -> {
        if (v != null) {
          paramBuilder.append(k).append("=").append(v).append("&");
        }
        });
      paramBuilder.append("appkey=").append(appkey);
    } else {
      paramBuilder.append("appkey=").append(appkey);
    }
    //生成md5，再转换成大写
    System.out.println("明文：" + paramBuilder.toString());
    return DigestUtils.md5Hex(paramBuilder.toString()).toUpperCase();
  }
  
  public static String generateSigUrl(String unionId, String accessToken, String body) {
    Map<String, Object> urlParams = new HashMap<>();
    urlParams.put("appid", appid);
    String timestamp = System.currentTimeMillis() + "";
    urlParams.put("timestamp", timestamp);
    String reqId = System.currentTimeMillis() + "";
    urlParams.put("req_id", System.currentTimeMillis() + "");
    if (unionId != null) {
      urlParams.put("unionId", unionId);
    }
    if (accessToken != null) {
      urlParams.put("accessToken", accessToken);
    }
    if (body != null) {
      urlParams.put("body", body);
    }
    
    
    String sig = sign(urlParams);
//    String sigUrl = String.format("appid=%s&timestamp=%s&req_id=%s&sig=%s", appid, timestamp, reqId, sig);
    String sigUrl = String.format("appid=%s&timestamp=%s&req_id=%s&sig=%s&access_token=%s&union_id=%s", appid, timestamp, reqId, sig, accessToken,unionId);
    return sigUrl;
  }

  @Test
  public void testGetAuthCode() {  //1 获取验证码
	String unionId = null;
    String accessToken = null;
    String body = "{\"phone\":\"18589074983\",\"type\":1,\"country_code\":\"+086\",\"code_length\":6}";  //注册用
    body = "{\"phone\":\"18218089328\",\"type\":2,\"country_code\":\"+086\",\"code_length\":6}";         //登录用
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl);//appid=100002&timestamp=1552444782580&req_id=1552444782580&sig=D17D7B535145119CD4887E4C8FEC7193

  }
  
  @Test
  public void testCheckAuthCode() { //2 校验验证码
	String unionId = null;
    String accessToken = null;
    //{"phone":"18589074983","type":1,"code":"111111","country_code":"+086"}
    String body = "{\"phone\":\"18218089328\",\"type\":1,\"code\":\"400482\",\"country_code\":\"+086\"}";
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl);//appid=100002&timestamp=1552445086566&req_id=1552445086566&sig=1EBB6D0FD3478B18B349D0791FC60ECD

  }
  
  @Test
  public void testRegister() { //3 注册
	String unionId = null;
    String accessToken = null;
    //{"pwd":"c924218e42775f01dfb928a7d2083bb8","secret":"572473f99c34494c021d4a76564266e4","phone":"18589074983","country_code":"+086","name":"laizhiwen","gender":1,"avatar":"http://ip:port/pic/xxx.jpg","birthday":"1991-09-01"}
    String body = "{\"pwd\":\"c924218e42775f01dfb928a7d2083bb8\",\"secret\":\"195742b5b8b241e9288df5a7f5a10697\",\"phone\":\"18589074983\",\"country_code\":\"+086\",\"name\":\"laizhiwen\",\"gender\":1,\"avatar\":\"http://ip:port/pic/xxx.jpg\",\"birthday\":\"1991-09-01\"}";
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl);//appid=100002&timestamp=1552445086566&req_id=1552445086566&sig=1EBB6D0FD3478B18B349D0791FC60ECD

  }
  
  @Test
  public void testLoginUsePhoneNumberAndPassword() { //4 手机号+密码登录
	String unionId = null;
    String accessToken = null;
    //{"phone":"18589074983","country_code":"+086","pwd":"c924218e42775f01dfb928a7d2083bb8","os_type":"Android","app_version":"v0.5","os_version":"android4.3","hardware_version":"Huawei","app_uuid":"12345678909876543210123456789000"}
    String body = "{\"phone\":\"18589074983\",\"country_code\":\"+086\",\"pwd\":\"c924218e42775f01dfb928a7d2083bb8\",\"os_type\":\"Android\",\"app_version\":\"v0.5\",\"os_version\":\"android4.3\",\"hardware_version\":\"Huawei\",\"app_uuid\":\"12345678909876543210123456789000\"}";
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl); //appid=100002&timestamp=1552445929754&req_id=1552445929754&sig=0873B7248F982CE4C7532837172B6980

  }
  
  @Test
  public void testLoginUsePhoneNumberAndAuthCode() { //4 手机号+验证按登录
	String unionId = null;
    String accessToken = null;
    //{"phone":"18589074983","country_code":"+086","code":"111111","os_type":"Android","app_version":"v0.5","os_version":"android4.3","hardware_version":"Huawei","app_uuid":"1234567899876543210012345678900"}
    String body = "{\"phone\":\"18218089328\",\"country_code\":\"+086\",\"code\":\"657821\",\"os_type\":\"Android\",\"app_version\":\"v0.5\",\"os_version\":\"android4.3\",\"hardware_version\":\"Huawei\",\"app_uuid\":\"1234567899876543210012345678900\"}";
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl); //appid=100002&timestamp=1552446756309&req_id=1552446756309&sig=451255C36778D881C12D457BC3B4BECD

  }

  @Test
  public void testLoginUsePhoneNumberAndAuthCode2() { //4 手机号+验证按登录
    String unionId = null;
    String accessToken = null;
    //{"phone":"18589074983","country_code":"+086","code":"111111","os_type":"Android","app_version":"v0.5","os_version":"android4.3","hardware_version":"Huawei","app_uuid":"1234567899876543210012345678900"}
    String body = "{\"phone\":\"18218089328\"}";
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl); //appid=100002&timestamp=1552446756309&req_id=1552446756309&sig=451255C36778D881C12D457BC3B4BECD

  }

  @Test
  public void testLogin() { //
    String unionId = "B3E86F87A4D25132652E733E59BC9DF8";
    String accessToken = "xMYGcMeCCH_WG8OCIPdZxQKXwbCAt5so4JXFzaK0YECFnglOKtnroNXw714grVg9RSOO4XyTNjLbpAZSX8sQrP03S7IeyK_9H492fcTPn263tuAR8W7dYOZM2pUA1E_BaCgnudnj3OM8ra3btHVK65aEo0lHvz4FRNz9vFccbep5X6gkPGILzdApLFeD5t5n_3maWfdvllW893PUtix03pr0hHaEMYjOpJy7khh_N0udLJtVUqKuaAqV8ut5Gt_-";
    //{"phone":"18589074983","country_code":"+086","code":"111111","os_type":"Android","app_version":"v0.5","os_version":"android4.3","hardware_version":"Huawei","app_uuid":"1234567899876543210012345678900"}
    String body = "{\"secret\":\"949202f75556f21f5595ae11bb1779b6\"}";
    String sigUrl = generateSigUrl(unionId, accessToken, body);
    System.out.println(sigUrl); //appid=100002&timestamp=1552446756309&req_id=1552446756309&sig=451255C36778D881C12D457BC3B4BECD

  }
  
//  public static void main(String[] args) {
//    
//    //2、获取手机验证码url生成
//    body = "{\"phone\":\"18589074983\",\"type\":1,\"country_code\":\"+086\",\"code_length\":6}";
//    sigUrl = generateSigUrl(unionId, accessToken, body);
//    System.out.println(sigUrl);
//    //3、注册url生成
//    body = "{\"pwd\":\"c924218e42775f01dfb928a7d2083bb8\",\"secret\":\"5cb12bd30d7f944efb6f35b3938119ca\",\"phone\":\"18218089328\",\"country_code\":\"+086\",\"name\":\"laizhiwen\",\"gender\":1,\"avatar\":\"http://ip:port/pic/xxx.jpg\",\"birthday\":\"1991-09-01\"}";
//    sigUrl = generateSigUrl(unionId, accessToken, body);
//    System.out.println(sigUrl);
//    String body = "{\"union_id\": [\"C4CB657F3169D0BA6F0577EDAC9F23A4\",\"49DEB536C24932D00280F795FD59AF4E\"]}";
//    String sigUrl = generateSigUrl(body);
//    System.out.println(sigUrl);
//    String filename = String.format("%s.jpg", "fdsfdhsifdsf");
//    System.out.println(filename);
//    String filename = String.format("%s_%s.jpg", "test", System.currentTimeMillis());
//    System.out.println(filename);
//      System.out.println(Integer.parseInt("00000"));
//    String[] strs = filename.split("[.]");
//    System.out.println(strs[1]);
//
//  }

}

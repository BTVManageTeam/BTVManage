package com.cdvcloud.rochecloud.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdvcloud.rochecloud.util.HttpUtil;
import com.cdvcloud.rochecloud.util.HttpUtilHelper;
import com.cdvcloud.wechat.util.wechat.FileUtil;
import com.cdvcloud.wechat.util.wechat.UUIDUtil;

/**
 * @ClassName: WechatInterfaceUtil
 * @Description: 微信接口工具类
 * @author: Dong
 * @date: 2018年7月11日       上午11:24:27
 */
public class WechatInterfaceUtil {
	
    private static final Logger logger = Logger.getLogger("ValidateCommonParam");

    /**
     * @Title:           getTags
     * @author:			 Dong
     * @Description:     获取分组标签
     * @param:           @param token
     * @param:           @return   
     * @return:          String   
     * @throws
     */
    public static String getTags(String token) {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
        url = url.replaceAll("ACCESS_TOKEN", token);
        Map<String, String> header = new HashMap<String, String>();
        String str = HttpUtilHelper.httpGet(url, header);
        return str;
    }

    /**
     * 消息回复
     *
     * @param accessToken      公众号授权
     * @param commonParameters 公共参数
     * @param jsonData         请求参数
     * @return
     * @throws Exception
     */
    public static String sendCustomMessage(String accessToken, String jsonData) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", accessToken);
        String jsonObject = HttpUtil.sendPost(url, jsonData);
        return jsonObject;
    }


    /**
     * 获取素材总数
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static String getMaterialcount(String accessToken) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", accessToken);
        Map<String, String> header = new HashMap<String, String>();
        String str = HttpUtilHelper.httpGet(url, header);
        return str;
    }



    public static String saveMediaToTemp(String httpUrl, String tempSavePath) {
        String tempPath = "";
        httpUrl = httpUrl.replaceFirst("https", "http");
        String newName = UUIDUtil.randomUUID() + "." + FileUtil.getSuffix(httpUrl);
        String savePath = tempSavePath + newName;
        if (FileUtil.httpDownload(httpUrl, savePath)) {
            tempPath = savePath;
        } else {
            logger.warn("将素材[" + httpUrl + "]保存到[" + savePath + "]失败!");
        }

        return tempPath;
    }


    public static String uploadMediaV2(String access_token, String mediaType, File media) {
        String result = null;
        // 拼装请求地址
        BufferedReader reader = null;
        String uploadMediaUrl = String.format(
                "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%1s&type=%2s",
                access_token,
                mediaType);
        try {
            URL url = new URL(uploadMediaUrl);
            if (!media.exists() || !media.isFile()) {
                logger.info("上传的文件不存在");
            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            con.setConnectTimeout(30000);
            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            String filename = media.getName();
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + filename + "\" \r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");

            // 获得输出流
            OutputStream out = new DataOutputStream(con.getOutputStream());
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(media));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();

            StringBuffer buffer = new StringBuffer();

            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
            reader.close();
            con.disconnect();
        } catch (IOException e) {
            logger.info("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    } 
    
    /**
     * @Title:           getH5AuthorAccessToken
     * @author:			 获取网页授权的accesstoken
     * @Description:     TODO
     * @param:           @param appid
     * @param:           @param code
     * @param:           @param componentAppId
     * @param:           @param componentAccessToken
     * @param:           @return   
     * @return:          String   
     * @throws
     */
    public static String getH5AuthorAccessToken(String appId, String code,String componentAppId,String componentAccessToken) {
    	String oauth_access_token = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=WECHATAPPID&code=CODE&grant_type=authorization_code&component_appid=COMPONENTAPPID&component_access_token=COMPONENT_ACCESS_TOKEN";
 		String url = oauth_access_token.replace("WECHATAPPID", appId).replace("CODE", code)
					.replace("COMPONENTAPPID", componentAppId).replace("COMPONENT_ACCESS_TOKEN", componentAccessToken);
        Map<String, String> header = new HashMap<String, String>();
        String reuslt = HttpUtilHelper.httpGet(url, header);
        return reuslt;
    }
    
    /**
     * @throws UnsupportedEncodingException 
     * @Title:           getH5AuthUserInfo
     * @author:			 Dong
     * @Description:     获取网页授权用户的基本信息
     * @param:           @param appid
     * 						公众号的appid	
     * @param:           @param code
     * 						授权code
     * @param:           @param componentAppId
     * 						服务开发方的appid
     * @param:           @param componentAccessToken
     * 						服务开发方的access_token
     * @param:           @return   
     * @return:          String   
     * @throws
     */
    public static String getH5AuthUserInfo(String openId,String accessToken) throws UnsupportedEncodingException {
    	String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
        Map<String, String> header = new HashMap<String, String>();
        String reuslt = HttpUtilHelper.httpGet(url, header);
        reuslt = new String(reuslt.getBytes("ISO-8859-1"), "UTF-8");
        return reuslt;
    }
}

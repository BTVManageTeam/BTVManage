package com.cdvcloud.rochecloud.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.cdvcloud.rochecloud.common.Constants;
/**
 * 
 *
 * @className HttpUtilHelper
 * @Description
 * @author wanglingfeng
 * @date 2018年6月2日下午8:40:38
 *
 */
public class HttpUtilHelper {
	private static final Logger logger = Logger.getLogger("ValidateCommonParam");
	/** 总连接数 */
	private static final int MAX_TOTAL = 100;
	/** 同路由的并发数 */
	private static final int MAX_PER_ROUTE = 100;
	/** 连接等待时长 */
	private static final int CONNECT_REQUEST_TIME_OUT = 1000;
	/** 连接超时 */
	private static final int CONNECT_TIME_OUT = 60000;
	/** 重试次数 */
	private static final int RETRY_COUNT = 5;

	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static SSLContextBuilder builder = null;
	private static SSLConnectionSocketFactory sslSocketFactory = null;
	private static Registry<ConnectionSocketFactory> registry = null;
	private static PoolingHttpClientConnectionManager pollingConnectionManager = null;
	private static CloseableHttpClient httpClient = null;
	static {
		System.out.println("http连接池开始初始化...");
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// SSL配置开始
		try {
			builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			});
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			String[] arr = new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" };
			sslSocketFactory = new SSLConnectionSocketFactory(builder.build(), arr, null, hostnameVerifier);
			PlainConnectionSocketFactory plainConnectionSocketFactory = new PlainConnectionSocketFactory();
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
			registry = registryBuilder.register(HTTP, plainConnectionSocketFactory).register(HTTPS, sslSocketFactory).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		// SSL配置结束
		// 创建池
		RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT).build();
		pollingConnectionManager = new PoolingHttpClientConnectionManager(registry);
		// 总连接数
		pollingConnectionManager.setMaxTotal(MAX_TOTAL);
		// 同路由的并发数
		pollingConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		httpClientBuilder.setSSLSocketFactory(sslSocketFactory);
		httpClientBuilder.setConnectionManager(pollingConnectionManager);
		httpClientBuilder.setDefaultRequestConfig(config);
		httpClientBuilder.setConnectionManagerShared(true);
		// 重试次数
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, true));
		httpClient = httpClientBuilder.build();
		System.out.println("http连接池初始化成功！");
	}

	/**
	 * http,https的post请求方法
	 * 
	 * @param url
	 *            访问地址
	 * @param header
	 *            http头信息
	 * @param param
	 *            提交的参数
	 * @param entity
	 *            设置实体 优先级高
	 * @param config
	 *            设置连接超时时间和获取数据超时时间,setConnectTimeout：设置连接超时时间，单位毫秒
	 *            setConnectionRequestTimeout：设置从connect Manager获取Connection
	 *            超时时间，单位毫秒 setSocketTimeout：请求获取数据的超时时间，单位毫秒
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> header, Map<String, Object> param, HttpEntity entity, RequestConfig config) {
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			// 设置头信息
			if (null != header && !header.isEmpty()) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置请求参数
			if (null != param && !param.isEmpty()) {
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entry : param.entrySet()) {
					// 给参数赋值
					formparams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
				httpPost.setEntity(urlEncodedFormEntity);
			}
			// 设置实体 优先级高
			if (entity != null) {
				httpPost.setEntity(entity);
			}
			if (config != null) {
				httpPost.setConfig(config);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resEntity);
			} else {
				result = readHttpResponse(httpResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * http,https的post请求方法(json请求方式)
	 * 
	 * @param url
	 *            访问地址
	 * @param header
	 *            http头信息
	 * @param param
	 *            提交的参数
	 * @param entity
	 *            设置实体 优先级高
	 * @param config
	 *            设置连接超时时间和获取数据超时时间,setConnectTimeout：设置连接超时时间，单位毫秒
	 *            setConnectionRequestTimeout：设置从connect Manager获取Connection
	 *            超时时间，单位毫秒 setSocketTimeout：请求获取数据的超时时间，单位毫秒
	 * @return
	 */
	public static String post(String url, Map<String, String> header, String json) {
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			// 设置头信息
			if (null != header && !header.isEmpty()) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置请求参数
			HttpEntity entity = getEntity(json, DEFAULT_CHARSET);
			if (entity != null) {
				httpPost.setEntity(entity);
			}
			RequestConfig config = getConfig(null);
			if (config != null) {
				httpPost.setConfig(config);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resEntity);
			} else {
				result = readHttpResponse(httpResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * http,https的put请求方法(json请求方式)
	 * 
	 * @param url
	 *            访问地址
	 * @param header
	 *            http头信息
	 * @param param
	 *            提交的参数
	 * @param entity
	 *            设置实体 优先级高
	 * @param config
	 *            设置连接超时时间和获取数据超时时间,setConnectTimeout：设置连接超时时间，单位毫秒
	 *            setConnectionRequestTimeout：设置从connect Manager获取Connection
	 *            超时时间，单位毫秒 setSocketTimeout：请求获取数据的超时时间，单位毫秒
	 * @return
	 * @throws Exception
	 */
	public static String put(String url, Map<String, String> header, String json) {
		String result = "";
		try {
			HttpPut httpPut = new HttpPut(url);
			// 设置头信息
			if (null != header && !header.isEmpty()) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpPut.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置请求参数
			HttpEntity entity = getEntity(json, DEFAULT_CHARSET);
			if (entity != null) {
				httpPut.setEntity(entity);
			}
			RequestConfig config = getConfig(null);
			if (config != null) {
				httpPut.setConfig(config);
			}
			HttpResponse httpResponse = httpClient.execute(httpPut);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resEntity);
			} else {
				result = readHttpResponse(httpResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * http,https的post请求方法,带超时设置
	 * 
	 * @param url
	 *            访问地址
	 * @param header
	 *            http头信息
	 * @param param
	 *            提交的参数
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> header, Map<String, Object> param) {
		RequestConfig config = RequestConfig.custom().setSocketTimeout(CONNECT_TIME_OUT).build();
		return post(url, header, param, null, config);
	}

	/**
	 * http,https的post请求方法，带超时和header设置
	 * 
	 * @param url
	 *            访问地址
	 * @param param
	 *            提交的参数
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, Object> param) {
		RequestConfig config = RequestConfig.custom().setSocketTimeout(CONNECT_TIME_OUT).build();
		Map<String, String> header = new HashMap<String, String>(16);
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36");
		header.put("Accept-Language", "zh-cn,zh;q=0.5");
		header.put("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.7");
		header.put("Connection", "keep-alive");
		return post(url, header, param, null, config);
	}

	/**
	 * 执行HttpPost请求
	 * 
	 * @param httpClient
	 *            HttpClient客户端实例，传入null会自动创建一个
	 * @param url
	 *            请求的远程地址
	 * @param paramsObj
	 *            提交的参数信息，目前支持Map,和String(JSON\xml)
	 * @param reffer
	 *            reffer信息，可传null
	 * @param cookie
	 *            cookies信息，可传null
	 * @param charset
	 *            请求编码，默认UTF8
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClientProtocolException
	 */
	public static String executePost(String url, Object paramsObj, String reffer, String cookie, String charset) throws IOException, ParseException {
		CloseableHttpResponse httpResponse = null;
		try {
			HttpPost post = new HttpPost(url);
			if (cookie != null && !"".equals(cookie)) {
				post.setHeader("Cookie", cookie);
			}
			if (reffer != null && !"".equals(reffer)) {
				post.setHeader("Reffer", reffer);
			}
			charset = getCharset(charset);
			// 设置参数
			HttpEntity httpEntity = getEntity(paramsObj, charset);
			if (httpEntity != null) {
				post.setEntity(httpEntity);
			}
			httpResponse = httpClient.execute(post);
			return getResult(httpResponse, charset);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	/**
	 * 执行HttpPut请求
	 * 
	 * @param httpClient
	 *            HttpClient客户端实例，传入null会自动创建一个
	 * @param url
	 *            请求的远程地址
	 * @param paramsObj
	 *            提交的参数信息，目前支持Map,和String(JSON\xml)
	 * @param reffer
	 *            reffer信息，可传null
	 * @param cookie
	 *            cookies信息，可传null
	 * @param charset
	 *            请求编码，默认UTF8
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClientProtocolException
	 */
	public static String executePut(String url, Object paramsObj, String reffer, String cookie, String charset) throws IOException, ParseException {
		CloseableHttpResponse httpResponse = null;
		try {
			HttpPut put = new HttpPut(url);
			if (cookie != null && !"".equals(cookie)) {
				put.setHeader("Cookie", cookie);
			}
			if (reffer != null && !"".equals(reffer)) {
				put.setHeader("Reffer", reffer);
			}
			charset = getCharset(charset);
			// 设置参数
			HttpEntity httpEntity = getEntity(paramsObj, charset);
			if (httpEntity != null) {
				put.setEntity(httpEntity);
			}
			httpResponse = httpClient.execute(put);
			return getResult(httpResponse, charset);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行HttpGet请求
	 * 
	 * @param httpClient
	 *            HttpClient客户端实例，传入null会自动创建一个
	 * @param url
	 *            请求的远程地址
	 * @param reffer
	 *            reffer信息，可传null
	 * @param cookie
	 *            cookies信息，可传null
	 * @param charset
	 *            请求编码，默认UTF8
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String executeGet(String url, String reffer, String cookie, String charset) throws IOException, ParseException {
		CloseableHttpResponse httpResponse = null;
		try {
			HttpGet get = new HttpGet(url);
			if (cookie != null && !"".equals(cookie)) {
				get.setHeader("Cookie", cookie);
			}
			if (reffer != null && !"".equals(reffer)) {
				get.setHeader("Reffer", reffer);
			}
			charset = getCharset(charset);
			httpResponse = httpClient.execute(get);
			return getResult(httpResponse, charset);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成请求体
	 * 
	 * @param paramsObj
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static HttpEntity getEntity(Object paramsObj, String charset) throws UnsupportedEncodingException {
		if(charset == null){
			charset = DEFAULT_CHARSET;
		}
		if (paramsObj == null) {
			System.out.println("当前未传入参数信息，无法生成HttpEntity");
			return null;
		}
		// 当前是map数据
		if (Map.class.isInstance(paramsObj)) {
			@SuppressWarnings("unchecked")
			Map<String, String> paramsMap = (Map<String, String>) paramsObj;
			List<NameValuePair> list = getNameValuePairs(paramsMap);
			UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list, charset);
			httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
			return httpEntity;
			// 当前是string对象，可能是
		} else if (String.class.isInstance(paramsObj)) {
			String paramsStr = paramsObj.toString();
			StringEntity httpEntity = new StringEntity(paramsStr, charset);
			if (paramsStr.startsWith(Constants.ZHONG_LEFT)) {
				httpEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
			} else if (paramsStr.startsWith(Constants.BRACE_LEFT)) {
				httpEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
			} else if (paramsStr.startsWith(Constants.ANGLE_BRACKETS_LEFT)) {
				httpEntity.setContentType(ContentType.APPLICATION_XML.getMimeType());
			} else {
				httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
			}
			return httpEntity;
		} else {
			System.out.println("当前传入参数不能识别类型，无法生成HttpEntity");
		}
		return null;
	}
	/**
	 * 获取连接超时设置
	 * @param connectTimeOut
	 * @return
	 */
	public static RequestConfig getConfig(Integer connectTimeOut){
		RequestConfig config = null;
		if(connectTimeOut == null || !(connectTimeOut > 0)){
			config = RequestConfig.custom().setSocketTimeout(CONNECT_TIME_OUT).build();
		}else{
			config = RequestConfig.custom().setSocketTimeout(connectTimeOut).build();
		}
		return config;
	}
	/**
	 * 将map类型参数转化为NameValuePair集合方式
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static List<NameValuePair> getNameValuePairs(Map<String, String> paramsMap) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (paramsMap == null || paramsMap.isEmpty()) {
			return list;
		}
		for (Entry<String, String> entry : paramsMap.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	/**
	 * 转化请求编码
	 * 
	 * @param charset
	 * @return
	 */
	private static String getCharset(String charset) {
		return charset == null ? DEFAULT_CHARSET : charset;
	}

	/**
	 * 从结果中获取出String数据
	 * 
	 * @param httpResponse
	 * @param charset
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	private static String getResult(CloseableHttpResponse httpResponse, String charset) throws ParseException, IOException {
		String result = null;
		if (httpResponse == null) {
			return result;
		}
		HttpEntity entity = httpResponse.getEntity();
		if (entity == null) {
			return result;
		}
		result = EntityUtils.toString(entity, charset);
		// 关闭应该关闭的资源，适当的释放资源 ;也可以把底层的流给关闭了
		EntityUtils.consume(entity);
		return result;
	}

	private static String readHttpResponse(HttpResponse httpResponse) throws ParseException, IOException {
		StringBuilder builder = new StringBuilder();
		// 获取响应消息实体
		org.apache.http.HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		builder.append("status:" + httpResponse.getStatusLine());
		builder.append("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			builder.append("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			builder.append("response length:" + responseString.length());
			builder.append("response content:" + responseString.replace("\r\n", ""));
		}
		return builder.toString();
	}

	/**
	 *
	 * @Title: httpGet
	 * @Description:get请求
	 * @param url
	 * @param header
	 * @param param
	 * @param entity
	 * @param config
	 * @return
	 * @return String
	 * @throws
	 */
	public static String httpGet(String url, Map<String, String> header) {
		String result = "";
		try {
			HttpGet httpGet = new HttpGet(url);
			// 设置头信息
			if (null != header && !header.isEmpty()) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpGet.addHeader(entry.getKey(), entry.getValue());
				}
			}
			RequestConfig config = getConfig(null);
			if (config != null) {
				httpGet.setConfig(config);
			}
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resEntity);
			} else {
				result = readHttpResponse(httpResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 向指定 URL 发送表单形式的POST方法的请求 application/x-www-form-urlencoded
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendFormPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CHARSET));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			logger.info(url + ";post请求状态：" + conn.getResponseCode());
		} catch (Exception e) {
			logger.error("发送 POST 请求出现异常！" + e.getMessage());
		} finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (null != out) {
					out.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("发送 POST 请求关闭输出流、输入流异常！" + ex.getMessage());
			}
		}
		return result.toString();
	}
}

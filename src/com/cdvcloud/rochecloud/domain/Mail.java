package com.cdvcloud.rochecloud.domain;

import java.io.InputStream;
import java.util.Properties;

/**
 * mail实体
 * @author mk
 *
 */
public class Mail {

	public static final String ENCODEING = "UTF-8";
	
	private static String host;	//服务器地址
	private static String sender;	//发件人的邮箱
	private String receiver;//收件人的邮箱
	private static String name;	//发件人昵称
	private static String username;//账号
	private static String password;//密码
	private static String subject;	//主题
	private String message;	//详情
	private static String message1;	//详情1
	private static String message4;	//详情4
	private static String message3;	//详情3
	private static String message5;	//详情5
	private static String message7;	//详情7
	
	
	//将下面内容放到配置文件mail.properties中，方便修改
	static{
		InputStream in = Mail.class.getResourceAsStream("/mail1.properties");
		  
        Properties p = new Properties();
  		try {
			p.load(in);
			host =p.getProperty("Host");
			sender =p.getProperty("sender");
			name =p.getProperty("name");
			username =p.getProperty("username");
			password =p.getProperty("password");
			subject =p.getProperty("subject");
			message1 =p.getProperty("message1");
			message3 =p.getProperty("message3");
			message4 =p.getProperty("message4");
			message5 =p.getProperty("message5");
			message7 =p.getProperty("message7");
		}catch (Exception e){
  			e.printStackTrace();
		}
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		Mail.host = host;
	}

	public static String getSender() {
		return sender;
	}

	public static void setSender(String sender) {
		Mail.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Mail.name = name;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Mail.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Mail.password = password;
	}

	public static String getSubject() {
		return subject;
	}

	public static void setSubject(String subject) {
		Mail.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getMessage1() {
		return message1;
	}

	public static void setMessage1(String message1) {
		Mail.message1 = message1;
	}

	public static String getMessage3() {
		return message3;
	}

	public static void setMessage3(String message3) {
		Mail.message3 = message3;
	}

	public static String getMessage4() {
		return message4;
	}

	public static void setMessage4(String message4) {
		Mail.message4 = message4;
	}

	public static String getMessage5() {
		return message5;
	}

	public static void setMessage5(String message5) {
		Mail.message5 = message5;
	}

	public static String getMessage7() {
		return message7;
	}

	public static void setMessage7(String message7) {
		Mail.message7 = message7;
	}
	
}

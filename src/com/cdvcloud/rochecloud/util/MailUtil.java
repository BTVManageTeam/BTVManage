package com.cdvcloud.rochecloud.util;

import com.cdvcloud.rochecloud.domain.Mail;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.IOException;
import java.sql.Date;

/**
 * 邮件发送工具实现类
 * @author mk
 *
 */
public class MailUtil {

	public boolean send(Mail mail){
		//发送email
		HtmlEmail email = new HtmlEmail();
		try{
			//System.out.println(mail.getHost()+"---"+mail.getSender()+"---"+mail.getName()+"---"+mail.getUsername()+"---"+mail.getPassword()+"---"+mail.getSubject());
			//这里是SMTP发送服务器的名字：163的如下：“smtp.163.com”
			email.setHostName(mail.getHost());
			//字符编码集的设置
			email.setCharset(Mail.ENCODEING);
			//收件人的邮箱
			email.addTo(mail.getReceiver());
			//发送人的邮箱
			email.setFrom(mail.getSender(),mail.getName());
			//如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			email.setAuthentication(mail.getUsername(), mail.getPassword());
			//要发送的邮件主题
			email.setSubject(mail.getSubject());
			//要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
			email.setMsg(mail.getMessage());
			//发送
			email.send();
//			if (logger.isDebugEnabled()) {
//				logger.debug(mail.getSender() + "发送邮件到"+ mail.getReceiver());
//			}
			return true;
		}catch(EmailException e){
			return false;
		}
	}
	
	public Boolean setMail(String usermail, String message2){
		Mail mail = new Mail();
		mail.setReceiver(usermail); //接收人
		//放入配置文件中的添加部分
		StringBuffer message = new StringBuffer();
		message.append(mail.getMessage1());
		message.append(message2);
		message.append(mail.getMessage3());
		message.append(mail.getMessage4());
		message.append(mail.getMessage5());
		Date datatime = new java.sql.Date(new java.util.Date().getTime());
		message.append(datatime.toString());
		message.append(mail.getMessage7());
		
		mail.setMessage(message.toString());
		MailUtil mailUtil =  new MailUtil();
		boolean aaa = mailUtil.send(mail);
		return aaa;
		
	}
	
	
	
	//public static void main(String[] args) throws IOException {
		//getProperties();
		
		
		//Mail mail = new Mail();
		//mail.setHost("smtp.163.com"); 
		//mail.setSender("mk740474106@163.com"); //发件人
		//mail.setReceiver("mk740474106@163.com"); //接收人
		
		//mail.setUsername("mk740474106@163.com"); 
		//mail.setPassword("makang456");
		//mail.setHost("mail.bj-tct.com");
		//mail.setReceiver("mk740474106@163.com"); //接收人
		//mail.setReceiver("dongliang.hao@bj-tct.com"); //接收人
		//mail.setReceiver("mengxu.zhang@bj-tct.com"); //接收人
		//mail.setSender("happywork@bj-tct.com"); //发件人
		//mail.setUsername("happywork@bj-tct.com"); 
		//mail.setPassword("abc@123456");
		//mail.setSubject("邮件测试");
		
		/*StringBuffer message = new StringBuffer();
		 * message.append("<table style='font-size:20'>");
		message.append("<tr><td width='70%'><span>XXX，您好! </span></td></tr>");
		message.append("<tr><td>&nbsp</td></tr>");
		message.append("<tr><td><span>&nbsp&nbsp您好XXX已经建立XXX项目,请您登陆处理</span></td></tr>");
		message.append("<tr><td><span>&nbsp&nbsp您好XXX已经建立XXX项目并且已经上传相应用例,请您登陆处理</span></td></tr>");
		message.append("<tr><td><span>&nbsp&nbsp您好XXX已经建立XXX项目并且已经上传相应数据,请您登陆处理</span></td></tr>");
		message.append("<tr><td><span>&nbsp&nbsp您好XXX已经下发XXX项目的XXX测试任务,请您登陆处理</span></td></tr>");
		message.append("<tr><td><span>&nbsp&nbsp您好XXX已经审核XXX项目的XXX测试任务,请您登陆ipad进行领取</span></td></tr>");
		message.append("<tr><td><span>&nbsp&nbsp登陆处理请单击:<a href='http://10.2.1.117:8080'>现场测试管理平台</a></span></td></tr>");
		message.append("<tr><td>&nbsp</td></tr><tr><td>&nbsp</td></tr>");
		message.append("<tr><td><span>&nbsp顺祝安琪</span></td></tr>");
		message.append("<tr><td><span>礼！</span></td></tr>");
		message.append("<tr><td></td><td><span>&nbsp&nbsp现场测试管理平台</span></td></tr>");
		message.append("<tr><td></td><td><span>2017年1月12日10:32:07</span></td></tr>");
		message.append("</table>");*/
		
		/*StringBuffer message = new StringBuffer();
		 * message.append("<table style='font-size:20'><tr><td width='70%'><span>");
		message.append("XXX");
		message.append("，您好! </span></td></tr><tr><td>&nbsp</td></tr><tr><td><span>&nbsp&nbsp");
		message.append("您好XXX已经建立XXX项目,请您登陆处理");
		//message.append("<tr><td><span>&nbsp&nbsp您好XXX已经建立XXX项目并且已经上传相应用例,请您登陆处理</span></td></tr>");
		//message.append("<tr><td><span>&nbsp&nbsp您好XXX已经建立XXX项目并且已经上传相应数据,请您登陆处理</span></td></tr>");
		//message.append("<tr><td><span>&nbsp&nbsp您好XXX已经下发XXX项目的XXX测试任务,请您登陆处理</span></td></tr>");
		//message.append("<tr><td><span>&nbsp&nbsp您好XXX已经审核XXX项目的XXX测试任务,请您登陆ipad进行领取</span></td></tr>");
		message.append("</span></td></tr><tr><td><span>&nbsp&nbsp登陆处理请单击:<a href='http://10.2.1.117:8080'>现场测试管理平台</a></span></td></tr>");
		message.append("<tr><td>&nbsp</td></tr><tr><td>&nbsp</td></tr>");
		message.append("<tr><td><span>&nbsp顺祝安琪</span></td></tr>");
		message.append("<tr><td><span>礼！</span></td></tr>");
		message.append("<tr><td>&nbsp</td></tr><tr><td></td><td><span>&nbsp&nbsp现场测试管理平台</span></td></tr><tr><td></td><td><span>");
		message.append("2017年1月12日10:32:07");
		message.append("</span></td></tr></table>");*/
		/*Mail mail = new Mail();
		mail.setReceiver("740474106@qq.com"); //接收人
		//放入配置文件中的添加部分
		StringBuffer message = new StringBuffer();
		message.append(mail.getMessage1());
		message.append("XXX");
		message.append(mail.getMessage3());
		message.append("您好XXX已经建立XXX项目,请您登陆处理");
		message.append(mail.getMessage5());
		message.append("2017年1月12日10:32:07");
		message.append(mail.getMessage7());
		
		mail.setMessage(message.toString());
		MailUtil mailUtil =  new MailUtil();
		ResultInfo aaa = mailUtil.send(mail);*/
	public static void main(String[] args) throws IOException {
		MailUtil mailUtil =  new MailUtil();
		Boolean aaa = mailUtil.setMail("mk740474106@163.com", "收件人姓名XXX");
		if (aaa) {
			System.out.println("发送成功");
		}else{
			System.out.println("发送失败");
		}
		
	}
}

package com.cn.store.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

import org.junit.Test;

public class MailUtils {
	
	public static void sendMail(String email, String emailMsg) throws AddressException, MessagingException {
		
		Properties props = new Properties();
		props.setProperty("mail.host", "smtp.163.com");//第一个参数不动，第二个参数（需要查资料）具体情况 若借助163服务器发送邮件--smtp.163.com  若借助126服务器发送，则smtp.126.com
		props.setProperty("mail.smtp.auth", "true");//用的是smtp 协议
		
		//得到 验证对象
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("chm96521@163.com", "chm96321");//第一个参数是发件人的账户  第二个发件人的账户的授权码（原本是 密码，但是我们这个程序 是第三方软件  这个授权码是可以使用 163邮箱服务器的钥匙）
			}
		};
		
//		将上面的两个对象 传到session中
		Session session = Session.getInstance(props, auth);
		
		/*
		 * 2. 创建邮件 MimeMessage
		 */
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("chm96521@163.com"));//设置发件人
		msg.setRecipients(RecipientType.TO, email);//设置收件人
 	 	msg.setRecipients(RecipientType.CC, "chm96521@163.com");//设置抄送  一定要给自己抄送一份
//		msg.setRecipients(RecipientType.CC, "it_cxf@sina.com");
	//	msg.setRecipients(RecipientType.BCC, "it_cxf@sina.com");//设置暗送
		
		msg.setSubject("注册验证123");//邮箱的标题
		msg.setContent(emailMsg, "text/html;charset=utf-8");//邮箱的内容
		
		/*
		 * 3. 发送
		 */
		Transport.send(msg);
		
	}
	
	@Test
	public void test05() throws Exception{
		
		Properties props = new Properties();
		props.setProperty("mail.host", "smtp.163.com");//第一个参数不动，第二个参数（需要查资料）具体情况 若借助163服务器发送邮件--smtp.163.com  若借助126服务器发送，则smtp.126.com
		props.setProperty("mail.smtp.auth", "true");//用的是smtp 协议
		
		//得到 验证对象
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("chm96521@163.com", "chm96321");//第一个参数是发件人的账户  第二个发件人的账户的授权码（原本是 密码，但是我们这个程序 是第三方软件  这个授权码是可以使用 163邮箱服务器的钥匙）
			}
		};
		
//		将上面的两个对象 传到session中
		Session session = Session.getInstance(props, auth);
		
		/*
		 * 2. 创建邮件 MimeMessage
		 */
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("chm96521@163.com"));//设置发件人
		msg.setRecipients(RecipientType.TO, "1263818027@qq.com");//设置收件人
 	 	msg.setRecipients(RecipientType.CC, "chm96521@163.com");//设置抄送  一定要给自己抄送一份
//		msg.setRecipients(RecipientType.CC, "it_cxf@sina.com");
	//	msg.setRecipients(RecipientType.BCC, "it_cxf@sina.com");//设置暗送
		
		msg.setSubject("注册验证123");//邮箱的标题
		msg.setContent("请点击这里激活账号，<a href='http://www.888888baidu.com'>激活</a>", "text/html;charset=utf-8");//邮箱的内容
		
		/*
		 * 3. 发送
		 */
		Transport.send(msg);
		
	}
	
}

package com.cn.store.mail;

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

public class Demo1 {
	@Test
	public void fun1() throws AddressException, MessagingException {
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.163.com");
		prop.setProperty("mail.smtp.auth", "true");
		
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("邮箱名", "授权码");
			}
		};
		
		Session session = Session.getDefaultInstance(prop, auth);
		
		session.setDebug(true);
		
		MimeMessage msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress("it_cxf@163.com"));
		msg.addRecipients(RecipientType.TO, "it_cxf@126.com, it_cxf@qq.com");
		msg.addRecipients(RecipientType.CC, "it_cxf@sina.com");
		msg.addRecipients(RecipientType.BCC, "it_cxf@sohu.com");
		msg.setSubject("这是一封测试邮件！");
		msg.setText("这是一封测试邮件，请不要在意！", "utf-8");
		
		Transport.send(msg);
	}
	
	@Test
	public void fun2() throws AddressException, MessagingException, IOException {
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.163.com");
		prop.setProperty("mail.smtp.auth", "true");
		
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("邮箱名", "授权码");
			}
		};
		
		Session session = Session.getDefaultInstance(prop, auth);
		
		session.setDebug(false);
		
		MimeMessage msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress("it_cxf@163.com"));
		msg.addRecipients(RecipientType.TO, "it_cxf@qq.com");
		msg.setSubject("这是一封带有附件的邮件！");
		
		MimeMultipart parts = new MimeMultipart();
		
		MimeBodyPart part1 = new MimeBodyPart();
		part1.setContent("<h1><font color='red'>这是一封带有附件的邮件，用来做测试的，请不要在意！</font></h1>", "text/html;charset=utf-8");
		parts.addBodyPart(part1);
		
//		MimeBodyPart part1 = new MimeBodyPart();
//		part1.attachFile("F:\\maze-game.swf");
//		part1.setFileName("maze-game.swf");
//		parts.addBodyPart(part1);
		
		MimeBodyPart part2 = new MimeBodyPart();
		part2.attachFile("F:\\白冰.jpg");
		part2.setFileName(MimeUtility.encodeText("白冰.jpg"));
		parts.addBodyPart(part2);
		
//		MimeBodyPart part3 = new MimeBodyPart();
//		part3.attachFile("F:\\没离开过.mp3");
//		part3.setFileName("没离开过.mp3");
//		parts.addBodyPart(part3);
		
		msg.setContent(parts);
		
		Transport.send(msg);
	}
	
	@Test
	public void fun3() throws MessagingException, IOException {
		Mail mail = new Mail("itcast_cxf@163.com", "it_cxf@126.com");
		mail.setSubject("丹东");
		mail.setContent("这是一封测试邮件！不要太在意！");
		
		Session session = MailUtils.createSession("smtp.163.com", "it_cxf", "itcast");
		MailUtils.send(session, mail);
	}
}

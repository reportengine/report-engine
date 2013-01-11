package com.redhat.reportengine.server.email;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class SendEmail {
	private static Logger _logger = Logger.getLogger(SendEmail.class);

	public static void sendEmail(String emailTo, String emailCc, String emailFrom, String mailSub, String bodyMsg, String serverName, String port)throws Exception {
		InternetAddress[] emailToAdd = null; 
		InternetAddress[] emailCcAdd = null; 

		if(emailTo != null)	{
			if(emailTo.length() > 0){
				String[] tmpTo= emailTo.split(",");
				emailTo = "";
				for(int i=0; i<tmpTo.length;i++){
					if(tmpTo[i].length()>1){
						if(i==0){
							emailTo += tmpTo[i].trim();
						}else {
							emailTo += ","+tmpTo[i].trim();
						}
					}else {
						_logger.debug("Short Email Id: "+tmpTo[i]);
					}
				}

				tmpTo = emailTo.split(",");
				emailToAdd = new InternetAddress[tmpTo.length];
				for(int i=0; i<tmpTo.length;i++){
					emailToAdd[i] = new InternetAddress(tmpTo[i].trim());
				}

			}
		}


		if(emailCc != null)	{
			if(emailCc.length() > 0){
				String[] tmpCc= emailCc.split(",");
				emailCc = "";
				for(int i=0; i<tmpCc.length;i++) {
					if(tmpCc[i].length()>1) {
						if(i==0){
							emailCc += tmpCc[i].trim();
						}else{
							emailCc += ","+tmpCc[i].trim();
						}
					}else{
						_logger.debug("Short Email Id: "+tmpCc[i]);
					}
				}

				tmpCc = emailCc.split(",");
				emailCcAdd = new InternetAddress[tmpCc.length];
				for(int i=0; i<tmpCc.length;i++) {
					emailCcAdd[i] = new InternetAddress(tmpCc[i].trim());
				}
			}
		}
		emailFrom = emailFrom.trim();
		_logger.debug("Mail Server: "+serverName+"\nEMail From: "+emailFrom+"\nEMail To: "+emailTo+"\nEMail Cc: "+emailCc+"Subject: "+mailSub+"\nMessage: "+bodyMsg);
		java.util.Properties props = new java.util.Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", serverName);
		if(port != null){
			props.setProperty("mail.smtp.port", port);
		}



		Session mailSession = Session.getDefaultInstance(props, null);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		message.setSubject(mailSub);
		message.setFrom(new InternetAddress(emailFrom));
		message.setContent(bodyMsg, "text/html"); 

		if(emailTo != null)
		{
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.addRecipients(Message.RecipientType.TO, emailToAdd);
		}
		if(emailCc != null)
		{
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(emailCc));
			message.addRecipients(Message.RecipientType.CC, emailCcAdd);
		}

		if((emailCc == null) && (emailTo == null))
		{
			_logger.error("Unable to send the email. Reason - To and Cc are null");
		}
		else
		{
			transport.connect();
			Transport.send(message);
			//transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			_logger.debug("Email Sent successfully");
		}
	}
}

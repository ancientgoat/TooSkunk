package com.premierinc.rule.action;

import com.premierinc.rule.action.enums.SkActionType;
import com.premierinc.rule.run.SkRuleRunner;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 */
public class SkActionEmail extends SkAction {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(final String inMessage) {
		message = inMessage;
	}

	@Override
	public SkActionType getActionType() {
		return SkActionType.PRINT;
	}

	@Override
	public void execute(SkRuleRunner inRunner) {

		System.out.println(this.message);

		// Recipient's email ID needs to be mentioned.
		String to = "ekamradt@premierinc.com";

		// Sender's email ID needs to be mentioned
		String from = "ekamradt@premierinc.com";

		// Assuming you are sending email from localhost
		String host = "email.premierinc.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (Exception mex) {
			mex.printStackTrace();
		}
		/*************************************************************
		 *************************************************************/
	}
}

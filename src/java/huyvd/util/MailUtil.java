/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author BlankSpace
 */
public class MailUtil {

    private static final String HOST = "smtp.gmail.com"; //using smtp host provide by google
    private static final int TLS_PORT = 587; //Port for Transport Layer Security (TSL)
    private static final String APP_EMAIL = "huyvd.activation@gmail.com"; //email use to send
    private static final String APP_PASSWORD = "huyvd!@#";

    public static void sendActivationCode(String code, String receiveEmail)
	    throws AddressException, MessagingException {
	//getPropertise object to config email
	Properties properties = new Properties();
	properties.put("mail.smtp.host", HOST);
	properties.put("mail.smtp.port", TLS_PORT);
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.starttls.enable", "true"); //TLS

	//get email session
	Session session = Session.getInstance(properties,
		new javax.mail.Authenticator() {
	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
	    }
	});

	//create and compose mime message
	MimeMessage msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(APP_EMAIL));
	msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));
	msg.setSubject("Active Car Rental Account");
	msg.setText("The activation code for your account is: " + code + "\n\nBest regards!");
	
	//send message
	Transport.send(msg);
    }
}

package Utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public  class Helper {

	public static String getIntFromText(String str){
		return  str.replaceAll("[^0-9]", "");
	}

	public static String getFirstLine(String str){
		return str.substring(0, str.lastIndexOf("\n"));
	}

	public static String getTextasOneLine(String str){
		return str.replaceAll("[\r\n]+", " ");
	}

	public static String getTextWithoutBrackets(String str){
		return str.replaceAll("[()]", "");
	}

	public static String getTextWithoutExtraSpacesBetweenWords(String str){
		return str.trim().replaceAll(" +", " ");
	}


	public static boolean textHasNewLine(String value){
		String newline = "\n";
		boolean hasNewline = value.contains(newline);
		if(hasNewline){
			return  true;
		}
		return false;
	}

	public static boolean textHasSpecialChar(String text){
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isDigit(text.charAt(i))&& !Character.isLetter(text.charAt(i))&& !Character.isWhitespace(text.charAt(i))){
				count++;
			}
		}
		if (count > 0)
			return false;
		else{
			return true;
		}
	}

	public static void sendMail(String email, String password, String fromAddress, Address[] addressList, String subject, String messageBody, String filename){
		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement
		props.put("mail.smtp.host", "smtp.gmail.com");

		// set the port of socket factory
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, password);
					}
				});
		try {
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
			// Set the from address
			message.setFrom(new InternetAddress(fromAddress));
			// Set the recipient address
			//InternetAddress.parse("mukesh.otwani50@gmail.com")
			message.setRecipients(Message.RecipientType.TO,addressList);
			// Add the subject link
			message.setSubject(subject);
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
			// Set the body of email
			messageBodyPart1.setText(messageBody);
			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			// Mention the file which you want to send
			//String filename = "G:\\a.xlsx";
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);
			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));
			// set the file
			messageBodyPart2.setFileName(filename);
			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();
			// add body part 1
			multipart.addBodyPart(messageBodyPart2);
			// add body part 2
			multipart.addBodyPart(messageBodyPart1);
			// set the content
			message.setContent(multipart);
			// finally send the email
			Transport.send(message);
			System.out.println("=====Email Sent=====");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}

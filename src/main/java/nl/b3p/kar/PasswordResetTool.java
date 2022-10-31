package nl.b3p.kar;

import javax.mail.Message;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import nl.b3p.kar.hibernate.Gebruiker;

public class PasswordResetTool {
	
	private String fromAddress;

	public PasswordResetTool() {
		InitialContext context;
		try {
			context = new InitialContext();
			Context xmlNode = (Context) context.lookup("java:comp/env");
			fromAddress = "ovdata@dova.nu";
  
		} catch (NamingException e) {
			e.printStackTrace();
		}
		   
	}
	public boolean ProcessMail(String body,Gebruiker g) {
		try {
			Mailer.sendMail("KarGeoTool", fromAddress, "Wachtwoord reset", body, g.getEmail());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public String CreateResetPasswordMessage(Gebruiker g) {
		return CreateBodyResetPassword(g.getPasswordResetCode());
	}
	private String CreateBodyResetPassword(String resetCode) {
		String url = "acc.kargeotool.nl/kargeotool/recover2.html?resetToken="+ resetCode;
		String body = "";
		body += "Beste lezer, <br/>";
        body += "<br/>";
        body += "Er is voor uw account van de Kar GEO tool een verzoek gedaan voor een nieuw wachtwoord. <br/>";
        body += "Klik op onderstaande link om uw wachtwoord te resetten. Mocht de link niet werken kopieer deze link in uw browser.<br/><br/>";        
        body += "<br/> Ga naar <a href=\""+url+"\">"+url+"</a> om uw wachtwoord te resetten<br/><br/>";     
        body += "Heeft u geen aanvraag gedaan voor een nieuw wachtwoord? Neem contact op DOVA via <a href=\"mailto:ovdata@dova.nu?subject=Account%20herstel%20vraag.\">ovdata@dova.nu</a>. <br /><br />";
        body += "Met vriendelijke groet<br /> DOVA";
		return body;
	}
}
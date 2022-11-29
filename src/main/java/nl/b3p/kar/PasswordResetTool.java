package nl.b3p.kar;

import javax.mail.Message;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.sourceforge.stripes.action.ActionBeanContext;
import nl.b3p.kar.hibernate.Gebruiker;

public class PasswordResetTool {
	
	private String fromAddress;
	private ActionBeanContext context;
	public PasswordResetTool() {
		InitialContext context;
		try {
			context = new InitialContext();
			Context xmlNode = (Context) context.lookup("java:comp/env");
			fromAddress = "noreply@dova.nu";
  
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
		String url = context.getServletContext().getInitParameter("application-url");
		url += "/recover2.html?resetToken="+ resetCode;
		String body = "";
		body += "Beste lezer, <br/>";
        body += "<br/>";
        body += "Er is voor uw account van de KARGeoTool een verzoek gedaan voor een nieuw wachtwoord. <br/>";
        body += "Klik op onderstaande link om uw wachtwoord te resetten. Mocht de link niet werken kopieer deze link in uw browser.<br/>";        
        body += "<br/> Ga naar <a href=\"https://"+url+"\">"+url+"</a> om uw wachtwoord te resetten.<br/><br/>";     
        body += "Heeft u geen aanvraag gedaan voor een nieuw wachtwoord, neem dan contact op met DOVA via <a href=\"mailto:ovdata@dova.nu?subject=Account%20herstel%20vraag.\">ovdata@dova.nu</a>. <br /><br />";
        body += "Met vriendelijke groet,<br /><br /> DOVA";
		return body;
	}
}

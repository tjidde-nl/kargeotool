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
			fromAddress = (String) xmlNode.lookup("PARAM_INFORM_ADMINS_FROMADDRESS");
  
		} catch (NamingException e) {
			// TODO Auto-generated catch block
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
		String body = "";
		body += "Beste, \n";
        body += "\n";
        body += "Er is op voor uw account bij de Kar GEO tool een verzoek gedaan om voor een nieuw wachtwoord. \n";
        body += "Hiervoor heeft u de volgende code nodig om dit te valideren\n\n";
        body += resetCode;
        body += "\n\n Ga naar <a href=\"kargeotool.nl/recover2.jsp\">Kargeotool.nl/recover2.jsp</a>om uw wachtwachtwoord te resetten\n\n";        	
        body += "Met vriendelijke groet";
		return body;
	}
}

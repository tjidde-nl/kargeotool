package nl.b3p.kar;

import java.util.List;

import nl.b3p.kar.hibernate.Gebruiker;

public class UsernameRequestTool {
	public void SendAllUserNames(List<Gebruiker> users) {
		String body = CreateBody(users);
		try {
			Mailer.sendMail("KarGeoTool", "noreply@dova.nu", "Opvragen gebruikersnamen", body, users.get(0).getEmail());
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	private String CreateBody(List<Gebruiker> users) {
		String body = "Beste lezer, <br/><br/>";
		body+= "U heeft aanvraag gedaan voor de gebruikersnamen dat op uw emailadres geregistreerd staan.<br/><br/>";
		body+= "De volgende gebruikersnamen zijn gevonden;<br/>";
		for (Gebruiker gebruiker : users) {
			body+= "- "+ gebruiker.getUsername()+"<br/>";
		}
		body += "<br/>";
		body += "Met vriendelijke groet,<br/>";
		body += "Dova team KarGeoTool";
		return body;
	}
}

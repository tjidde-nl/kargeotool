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
		body+= "U heeft een aanvraag gedaan voor de gebruikersnaam of gebruikersnamen die op uw mailadres geregistreerd staan met betrekking tot uw account voor de KARGeoTool.<br/><br/>";
		body+= "De volgende gebruikersnamen zijn gevonden:<br/>";
		for (Gebruiker gebruiker : users) {
			body+= "- "+ gebruiker.getUsername()+"<br/>";
		}
		body += "<br/>";
		body += "Met vriendelijke groet,<br/><br/>";
		body += "DOVA";
		return body;
	}
}

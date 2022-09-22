package nl.b3p.kar.endpoint;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nl.b3p.kar.SecurityRealm;
import nl.b3p.kar.UsernameRequestTool;
import nl.b3p.kar.hibernate.Gebruiker;

@Path("username")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class UsernameEndpoint {
	private SecurityRealm sr = new SecurityRealm();
	private UsernameRequestTool urt = new UsernameRequestTool();
	@POST
	@Path("requestAllUsernamesEmail")
	public boolean RequestNewPassword(String email) {
		urt.SendAllUserNames(sr.GetGebruikersEmail(email));
		return true;
	}
}

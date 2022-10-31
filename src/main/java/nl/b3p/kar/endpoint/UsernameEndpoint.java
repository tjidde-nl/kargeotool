package nl.b3p.kar.endpoint;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.b3p.kar.SecurityRealm;
import nl.b3p.kar.UsernameRequestTool;
import nl.b3p.kar.hibernate.Gebruiker;

@Path("username")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Api(tags={"Username"})
public class UsernameEndpoint {
	private SecurityRealm sr = new SecurityRealm();
	private UsernameRequestTool urt = new UsernameRequestTool();
	@POST
	@Path("requestAllUsernamesEmail")
	@ApiOperation(value = "Request all usernames bound to given email")
	 @ApiResponses({
	    @ApiResponse(code=200, message="true")
	  })
	public boolean RequestNewPassword(String email) {
		urt.SendAllUserNames(sr.GetGebruikersEmail(email.toLowerCase().trim()));
		return true;
	}
}

package nl.b3p.kar.endpoint;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


import javax.ws.rs.*;

import io.swagger.annotations.*;
import nl.b3p.kar.PasswordResetTool;
import nl.b3p.kar.SecurityRealm;
import nl.b3p.kar.endpoint.ApiModels.*;
import nl.b3p.kar.hibernate.Gebruiker;

@Path("password")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Api(tags={"Password"})
public class PasswordEndpoint {
	private PasswordResetTool prt = new PasswordResetTool();
	private SecurityRealm sr = new SecurityRealm();
	@POST
	@Path("requestNewPassword")
	 @ApiOperation(value = "requests a password reset email bound to username")
	 @ApiResponses({
	    @ApiResponse(code=200, message="username")
	  })
	public String RequestNewPassword(String username) {
		Gebruiker g = sr.GetGebruikerUsername(username);
		if(g==null)return "No User:"+username;
		g = sr.SetRecoveryCode(g);
		String body = prt.CreateResetPasswordMessage(g);
		prt.ProcessMail(body, g);
		return 	g.toString();
	}
	@POST
	@Path("ChangePasswordRecover/{uid}")	
	 @ApiOperation(value = "resets password of the user bound to uid. uid will be disposed after call.")
	 @ApiResponses({
	    @ApiResponse(code=200, message="success")
	  })
	public String ChangePasswordRecovery(@PathParam(value = "uid")String uid, String newPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Gebruiker g = sr.GetGebruikerRecoveryCode(uid);		
		if(g == null)return "no recovery code found!";
		g.changePassword(null, newPassword);
		g.setPasswordResetCode("");
		sr.UpdateGebruiker(g);
		return "success";
	}
	@POST
	@Path("ChangePasswordUsername")
	public void ChangePasswordUsername(UserPass up[]){
		
	}
	@GET
	@ApiOperation(value = "Test API")
	 @ApiResponses({
	    @ApiResponse(code=200, message="???")
	  })
	public String GetPassword() {
		return "Why you do this? \n You can't get your password...";
	}
}

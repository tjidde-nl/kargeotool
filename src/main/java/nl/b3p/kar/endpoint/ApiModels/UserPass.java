package nl.b3p.kar.endpoint.ApiModels;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserPass {
	@NotNull(message = "Name is mandatory")
	@NotBlank(message = "Name can't be blank")
	@NotEmpty(message = "Name can't be empty")
	private String userName;

	@NotNull(message = "Old Password is mandatory")
	@NotBlank(message = "Old Password can't be blank")
	@NotEmpty(message = "Old Password can't be empty")
	private String oldPassWord;
	
	@NotNull(message = "New Password is mandatory")
	@NotBlank(message = "New Password can't be blank")
	@NotEmpty(message = "New Password can't be empty")
	private String newPassWord;
}

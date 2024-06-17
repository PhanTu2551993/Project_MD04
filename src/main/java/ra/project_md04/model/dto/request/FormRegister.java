package ra.project_md04.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.project_md04.validation.EmailExist;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormRegister {
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String username;
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String fullName;
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String phone;
	@EmailExist(message = "email is exist")
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String email;
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String password;
	private Set<String> roles;
}

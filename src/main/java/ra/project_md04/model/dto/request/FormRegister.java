package ra.project_md04.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormRegister {
	private String username;
	private String fullName;
	private String phone;
	private String email;
	private String password;
	private Set<String> roles;
}

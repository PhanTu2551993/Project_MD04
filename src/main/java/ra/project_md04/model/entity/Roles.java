package ra.project_md04.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ra.project_md04.constans.RoleName;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	@Enumerated(EnumType.STRING)
	private RoleName roleName;
	
}

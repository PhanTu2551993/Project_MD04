package ra.project_md04.model.dto.request;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String oldPass;
    private String newPass;
    private String confirmNewPass;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}

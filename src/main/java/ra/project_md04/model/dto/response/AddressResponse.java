package ra.project_md04.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {
    private Long addressId;
    private String fullAddress;
    private String phone;
    private String receiveName;
}

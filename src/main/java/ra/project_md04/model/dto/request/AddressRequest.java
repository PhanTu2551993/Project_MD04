package ra.project_md04.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressRequest {
    private Long addressId;
    private String fullAddress;
    private String phone;
    private String receiveName;
    private Long userId;
}

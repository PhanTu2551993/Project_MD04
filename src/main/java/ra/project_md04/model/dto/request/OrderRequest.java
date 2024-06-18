package ra.project_md04.model.dto.request;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    private Long userId;
    private List<OrderItem> items;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private String note;
}

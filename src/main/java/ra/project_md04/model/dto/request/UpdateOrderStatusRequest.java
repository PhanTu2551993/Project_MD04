package ra.project_md04.model.dto.request;

import lombok.*;
import ra.project_md04.constans.OrderStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOrderStatusRequest {
    private OrderStatus orderStatus;
}

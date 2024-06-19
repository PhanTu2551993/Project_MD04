package ra.project_md04.model.dto.response;


import jakarta.persistence.*;
import lombok.*;
import ra.project_md04.constans.OrderStatus;


import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long orderId;
    private String serialNumber;
    private String userName;
    private Long userId;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private String note;
    private Date createdAt;
    private Date receivedAt;
    private List<OrderDetailsResponse> products;
}

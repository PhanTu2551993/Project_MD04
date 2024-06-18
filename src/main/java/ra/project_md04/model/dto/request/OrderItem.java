package ra.project_md04.model.dto.request;

import lombok.Data;

@Data
public class OrderItem {
    private Long productId;
    private int quantity;
}

package ra.project_md04.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItemResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private Double price;
}

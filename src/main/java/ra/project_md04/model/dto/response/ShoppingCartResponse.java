
package ra.project_md04.model.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCartResponse {
    private Long shoppingCartId;
    private Long productId;
    private String productName;
    private Integer orderQuantity;
}

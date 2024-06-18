package ra.project_md04.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddToCartRequest {
    private Long productId;
    private Integer quantity;
}

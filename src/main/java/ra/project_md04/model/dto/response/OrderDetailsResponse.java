package ra.project_md04.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailsResponse {
    private Long productId;
    private Integer quantity;
    private Double price;
    private String name;

}

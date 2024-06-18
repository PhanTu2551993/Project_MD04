package ra.project_md04.model.dto.response;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishListResponse {
    private Long wishListId;
    private String wishListProductName;
    private Long userId;
    private Long productId;
}

package ra.project_md04.model.dto.request;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishListRequest {
    private Long wishListId;
    private Long userId;
    private Long productId;
}

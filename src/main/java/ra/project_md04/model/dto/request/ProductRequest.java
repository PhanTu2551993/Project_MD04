package ra.project_md04.model.dto.request;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    private String sku;
    private String productName;
    private String description;
    private Double unitPrice;
    private Integer stockQuantity;
    private String image;
    private Long categoryId;
    private Date createdAt;
    private Date updatedAt;
}

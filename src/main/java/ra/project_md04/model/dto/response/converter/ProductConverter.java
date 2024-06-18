package ra.project_md04.model.dto.response.converter;

import ra.project_md04.model.dto.response.ProductResponse;
import ra.project_md04.model.entity.Product;

public class ProductConverter {
    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .sku(product.getSku())
                .productName(product.getProductName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .stockQuantity(product.getStockQuantity())
                .image(product.getImage())
                .categoryId(product.getCategory().getCategoryId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}

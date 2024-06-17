package ra.project_md04.service;

import org.springframework.data.domain.Page;
import ra.project_md04.model.dto.request.ProductRequest;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.Users;

;import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(Integer page, Integer itemPage, String orderBy, String direction);
    List<Product> findProductsByCategoryId(Long categoryId);
    List<Product> findAll();
    Product findProductById(Long proId);
    Product save(ProductRequest productRequest);
    Product update(Long productId,ProductRequest productRequest);
    void delete(Long proId);
    Page<Product> findProductsByProNameOrDescription(String searchText,Integer page,Integer itemPage, String orderBy, String direction);
}

package ra.project_md04.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project_md04.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long proId);
    List<Product> findProductsByCategory_CategoryId(Long categoryId);
    @Query("select p from Product p where p.productName like concat('%',:searchText,'%') or p.description like concat('%',:searchText,'%')")
    Page<Product> findProductsByProNameOrDescription(String searchText, Pageable pageable);
}

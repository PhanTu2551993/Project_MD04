package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.ShoppingCart;
import ra.project_md04.model.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findAllByUser(Users users);
    Optional<ShoppingCart> findByUserAndProduct(Users user, Product product);
    Optional<ShoppingCart> findByShoppingCartIdAndUser(Long shoppingCartId, Users user);
}

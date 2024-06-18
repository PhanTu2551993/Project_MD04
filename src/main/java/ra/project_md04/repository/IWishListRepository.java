package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Users;
import ra.project_md04.model.entity.WishList;

import java.util.List;
import java.util.Optional;

public interface IWishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUser(Users users);
    Optional<WishList> findByWishListIdAndUser(Long wishListId, Users users);
}

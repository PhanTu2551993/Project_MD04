package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.Users;

import java.util.List;
import java.util.Optional;

public interface IAddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUsers(Users users);
    Optional<Address> findByAddressIdAndUsers(Long addressId, Users user);
}

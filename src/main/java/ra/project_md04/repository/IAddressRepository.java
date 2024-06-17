package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md04.model.entity.Address;

public interface IAddressRepository extends JpaRepository<Address, Integer> {
}

package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md04.model.entity.Order;
import ra.project_md04.model.entity.OrderDetails;
import ra.project_md04.model.entity.Users;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetails, Long> {
}

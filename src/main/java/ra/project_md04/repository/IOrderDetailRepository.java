package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md04.model.entity.OrderDetails;

public interface IOrderDetailRepository extends JpaRepository<OrderDetails, Long> {
}

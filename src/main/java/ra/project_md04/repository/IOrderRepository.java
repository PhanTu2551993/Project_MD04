package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md04.model.entity.Order;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}

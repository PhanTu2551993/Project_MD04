package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md04.constans.OrderStatus;
import ra.project_md04.model.entity.Order;
import ra.project_md04.model.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUsers(Users users);
    Optional<Order> findByOrderId(Long orderId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByStatusAndUsers(OrderStatus status, Users user);
    Optional<Order> findBySerialNumberAndUsers(String serialNumber, Users users);
    Optional<Order> findByOrderIdAndUsers(Long orderId, Users users);
}

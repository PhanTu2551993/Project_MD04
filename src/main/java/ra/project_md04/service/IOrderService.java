package ra.project_md04.service;

import ra.project_md04.constans.OrderStatus;
import ra.project_md04.model.dto.request.OrderRequest;
import ra.project_md04.model.entity.Order;
import ra.project_md04.model.entity.OrderDetails;

import java.util.List;

public interface IOrderService {
    Order placeOrder(OrderRequest orderRequest);
    List<Order> getAllOrders();
    Order getOrderById(Long orderId);
    List<Order> getAllUserOrders();
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getHistoryOrdersByStatus(OrderStatus status);
    Order getOrderBySerialNumber(String serialNumber);
    Boolean cancelOrder(Long orderId);
    Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus);

}

package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md04.constans.OrderStatus;
import ra.project_md04.model.dto.request.OrderItem;
import ra.project_md04.model.dto.request.OrderRequest;
import ra.project_md04.model.entity.Order;
import ra.project_md04.model.entity.OrderDetails;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.Users;
import ra.project_md04.repository.IOrderDetailRepository;
import ra.project_md04.repository.IOrderRepository;
import ra.project_md04.repository.IProductRepository;
import ra.project_md04.service.IOrderService;
import ra.project_md04.service.IProductService;
import ra.project_md04.service.IUserService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IUserService userService;


    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Order order = Order.builder()
                .users(currentUser)
                .serialNumber(UUID.randomUUID().toString())
                .totalPrice(calculateTotalPrice(orderRequest.getItems()))
                .status(OrderStatus.WAITING)
                .receiveName(orderRequest.getReceiveName())
                .receiveAddress(orderRequest.getReceiveAddress())
                .receivePhone(orderRequest.getReceivePhone())
                .note(orderRequest.getNote())
                .createdAt(new Date())
                .receivedAt(new Date())
                .build();

        order = orderRepository.save(order);

        Order finalOrder = order;
        List<OrderDetails> orderDetailsList = orderRequest.getItems().stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tồn tại sản phẩm"));

            return OrderDetails.builder()
                    .order(finalOrder)
                    .product(product)
                    .name(product.getProductName())
                    .unitPrice(product.getUnitPrice())
                    .orderQuantity(item.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        List<OrderDetails> orderDetails = orderDetailRepository.saveAll(orderDetailsList);
        order.setOrderDetails(orderDetails);
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Không tồn tại đơn hàng"));
    }

    @Override
    public List<Order> getAllUserOrders() {
        Users currentUser = userService.getCurrentLoggedInUser();
        return orderRepository.findAllByUsers(currentUser);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getHistoryOrdersByStatus(OrderStatus status) {
        Users currentUser = userService.getCurrentLoggedInUser();
        return orderRepository.findByStatusAndUsers(status, currentUser);
    }

    @Override
    public Order getOrderBySerialNumber(String serialNumber) {
        Users currentUser = userService.getCurrentLoggedInUser();
        return orderRepository.findBySerialNumberAndUsers(serialNumber,currentUser).orElseThrow(()->new NoSuchElementException("Không tồn tại"));
    }

    @Override
    public Boolean cancelOrder(Long orderId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Order order = orderRepository.findByOrderIdAndUsers(orderId, currentUser)
                .orElseThrow(() -> new NoSuchElementException("Khong tồn tại đơn hàng"));

        if (order.getStatus() == OrderStatus.WAITING) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng"));

        order.setStatus(orderStatus);
        orderRepository.save(order);
        return true;
    }


    private Double calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Không tồn tại sản phẩm"));
                    return product.getUnitPrice() * item.getQuantity();
                })
                .sum();
    }
}

package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                .userId(currentUser.getUserId())
                .serialNumber(UUID.randomUUID().toString())
                .totalPrice(calculateTotalPrice(orderRequest.getItems()))
                .status("Pending")
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
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            return OrderDetails.builder()
                    .order(finalOrder)
                    .product(product)
                    .name(product.getProductName())
                    .unitPrice(product.getUnitPrice())
                    .orderQuantity(item.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetailsList);

        return order;
    }
    private Double calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    return product.getUnitPrice() * item.getQuantity();
                })
                .sum();
    }
}

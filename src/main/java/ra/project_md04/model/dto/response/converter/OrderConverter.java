package ra.project_md04.model.dto.response.converter;

import ra.project_md04.model.dto.response.OrderDetailsResponse;
import ra.project_md04.model.dto.response.OrderResponse;
import ra.project_md04.model.entity.Order;



import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderResponse toOrderResponse(Order order) {
        List<OrderDetailsResponse> products = order.getOrderDetails().stream()
                .map(orderDetails -> OrderDetailsResponse.builder()
                        .productId(orderDetails.getProduct().getProductId())
                        .name(orderDetails.getProduct().getProductName())
                        .quantity(orderDetails.getOrderQuantity())
                        .price(orderDetails.getUnitPrice()* orderDetails.getOrderQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userName(order.getUsers().getFullName())
                .note(order.getNote())
                .receiveAddress(order.getReceiveAddress())
                .receivePhone(order.getReceivePhone())
                .createdAt(order.getCreatedAt())
                .receivedAt(order.getReceivedAt())
                .userId(order.getUsers().getUserId())
                .status(order.getStatus())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .receiveName(order.getReceiveName())
                .products(products)
                .build();
    }
}

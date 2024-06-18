package ra.project_md04.service;

import ra.project_md04.model.dto.request.OrderRequest;
import ra.project_md04.model.entity.Order;

public interface IOrderService {
    Order placeOrder(OrderRequest orderRequest);
}

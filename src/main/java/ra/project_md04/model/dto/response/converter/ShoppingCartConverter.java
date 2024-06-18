package ra.project_md04.model.dto.response.converter;

import ra.project_md04.model.dto.response.CartItemResponse;
import ra.project_md04.model.dto.response.ShoppingCartResponse;
import ra.project_md04.model.entity.ShoppingCart;


public class ShoppingCartConverter {
    public static ShoppingCartResponse convertToResponse(ShoppingCart shoppingCart) {
        ShoppingCartResponse response = new ShoppingCartResponse();
        response.setShoppingCartId(shoppingCart.getShoppingCartId());
        response.setProductId(shoppingCart.getProduct().getProductId());
        response.setProductName(shoppingCart.getProduct().getProductName());
        response.setOrderQuantity(shoppingCart.getOrderQuantity());
        return response;
    }
}

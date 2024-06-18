package ra.project_md04.service;

import ra.project_md04.model.dto.request.AddToCartRequest;
import ra.project_md04.model.dto.response.ShoppingCartResponse;
import ra.project_md04.model.entity.ShoppingCart;

import java.util.List;

public interface IShoppingCartService {
    ShoppingCart addToCart(AddToCartRequest addToCartRequest);
    List<ShoppingCartResponse> getShoppingCart();
    void removeFromCart(Long shoppingCartId);
    void removeAllFromCart();
    ShoppingCart updateCartItemQuantity(Long shoppingCartId, Integer quantity);
}

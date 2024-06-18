package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md04.model.dto.request.AddToCartRequest;
import ra.project_md04.model.dto.response.ShoppingCartResponse;
import ra.project_md04.model.dto.response.converter.ShoppingCartConverter;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.ShoppingCart;
import ra.project_md04.model.entity.Users;
import ra.project_md04.repository.IAddressRepository;
import ra.project_md04.repository.IProductRepository;
import ra.project_md04.repository.IShoppingCartRepository;
import ra.project_md04.service.IProductService;
import ra.project_md04.service.IShoppingCartService;
import ra.project_md04.service.IUserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public ShoppingCart addToCart(AddToCartRequest addToCartRequest) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Product product = productRepository.findById(addToCartRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserAndProduct(currentUser, product)
                .orElse(ShoppingCart.builder()
                        .user(currentUser)
                        .product(product)
                        .orderQuantity(0)
                        .build());

        shoppingCart.setOrderQuantity(shoppingCart.getOrderQuantity() + addToCartRequest.getQuantity());
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCartResponse> getShoppingCart() {
        Users currentUser = userService.getCurrentLoggedInUser();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser(currentUser);
        return shoppingCarts.stream().map(ShoppingCartConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public void removeFromCart(Long shoppingCartId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByShoppingCartIdAndUser(shoppingCartId,currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không có sản phẩm"));
        shoppingCartRepository.delete(shoppingCart);
    }
    @Override
    public void removeAllFromCart() {
        Users currentUser = userService.getCurrentLoggedInUser();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser(currentUser);
        shoppingCartRepository.deleteAll(shoppingCarts);
    }

    @Override
    public ShoppingCart updateCartItemQuantity(Long shoppingCartId, Integer quantity) {
        Users currentUser = userService.getCurrentLoggedInUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByShoppingCartIdAndUser(shoppingCartId,currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không có sản phẩm"));
        shoppingCart.setOrderQuantity(quantity);
        return shoppingCartRepository.save(shoppingCart);
    }
}

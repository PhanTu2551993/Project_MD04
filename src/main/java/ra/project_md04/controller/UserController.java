package ra.project_md04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.constans.OrderStatus;
import ra.project_md04.model.dto.request.*;
import ra.project_md04.model.dto.response.*;
import ra.project_md04.model.dto.response.converter.AddressConverter;
import ra.project_md04.model.dto.response.converter.OrderConverter;
import ra.project_md04.model.dto.response.converter.ShoppingCartConverter;
import ra.project_md04.model.dto.response.converter.UserConverter;
import ra.project_md04.model.entity.*;
import ra.project_md04.service.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IWishListService wishListService;
    @Autowired
    private IOrderService orderService;


    @PutMapping("/account/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UpdateUserRequest updateUserRequest) {
        boolean result = userService.changePassword(updateUserRequest.getOldPass(), updateUserRequest.getNewPass(), updateUserRequest.getConfirmNewPass());

        if (result) {
            return ResponseEntity.ok("Đổi mật khẩu thành công !!");
        } else {
            return ResponseEntity.badRequest().body("Thay đổi mật khẩu thất bại");
        }
    }

    @PutMapping("/account")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        Users updatedUser = userService.updateUser(updateUserRequest);
        UserResponse userResponses = UserConverter.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/account")
    public ResponseEntity<Users> getInfoUser() {
        Users infoUser = userService.getCurrentLoggedInUser();
        return new ResponseEntity<>(infoUser, HttpStatus.OK);
    }

    @PostMapping("/account/addresses")
    public ResponseEntity<AddressResponse> addNewAddress(@RequestBody AddressRequest addressRequest) {
        Address newAddress = addressService.addNewAddress(addressRequest);
        AddressResponse addressResponse = AddressConverter.toAddressResponse(newAddress);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @GetMapping("/account/addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        List<AddressResponse> addresses = addressService.getUserAddresses();
        return ResponseEntity.ok(addresses);

    }

    @GetMapping("/account/addresses/{addressId}")
    public ResponseEntity<AddressResponse> getAddressByAddressId(@PathVariable Long addressId) {
        AddressResponse addressResponse = addressService.getAddressByAddressId(addressId);
        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/account/addresses/{addressId}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long addressId) {
        addressService.deleteAddressById(addressId);
        return ResponseEntity.ok().body("đã xóa thành công địa chỉ có ID : " + addressId);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<ShoppingCartResponse> addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        ShoppingCart shoppingCart = shoppingCartService.addToCart(addToCartRequest);
        ShoppingCartResponse shoppingCartResponse = ShoppingCartConverter.convertToResponse(shoppingCart);
        return ResponseEntity.ok(shoppingCartResponse);
    }

    @GetMapping("/cart/list")
    public ResponseEntity<List<ShoppingCartResponse>> getUserShoppingCarts() {
        List<ShoppingCartResponse> shoppingCarts = shoppingCartService.getShoppingCart();
        return ResponseEntity.ok(shoppingCarts);

    }

    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.removeFromCart(cartItemId);
        return ResponseEntity.ok().body("đã xóa thành công sản phẩm trong giỏ hàng có ID : " + cartItemId);
    }

    @DeleteMapping("/cart/clear")
    public ResponseEntity<?> removeAllFromCart() {
        shoppingCartService.removeAllFromCart();
        return ResponseEntity.ok().body("đã xóa thành công tất cả sản phẩm trong giỏ hàng");
    }

    @PutMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ShoppingCartResponse> updateCartItemQuantity(@PathVariable Long cartItemId,
                                                                       @RequestParam Integer quantity) {
        ShoppingCart updatedCart = shoppingCartService.updateCartItemQuantity(cartItemId, quantity);
        ShoppingCartResponse response = ShoppingCartConverter.convertToResponse(updatedCart);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        OrderResponse orderResponse = OrderConverter.toOrderResponse(order);
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/wish-list")
    public ResponseEntity<WishListResponse> addWishList(@RequestBody WishListRequest wishListRequest) {
        WishListResponse addedWishList = wishListService.addWishList(wishListRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedWishList);
    }

    @GetMapping("/wish-list")
    public ResponseEntity<List<WishListResponse>> getUserWishList() {
        List<WishListResponse> wishListResponseList = wishListService.getWishList();
        return ResponseEntity.ok(wishListResponseList);

    }

    @DeleteMapping("/wish-list/{wishListId}")
    public ResponseEntity<?> deleteWishList(@PathVariable Long wishListId) {
        wishListService.deleteWishList(wishListId);
        return ResponseEntity.ok().body("đã xóa thành công sản phẩm trong danh sách yêu thích có ID : " + wishListId);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllUserOrders();
        List<OrderResponse> orderResponseList = orders.stream()
                .map(OrderConverter::toOrderResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
    }

    @GetMapping("/history/{serialNumber}")
    public ResponseEntity<OrderResponse> getHistory(@PathVariable String serialNumber) {
        Order order = orderService.getOrderBySerialNumber(serialNumber);
        OrderResponse orderResponse = OrderConverter.toOrderResponse(order);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/historyOrder/{orderStatus}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus orderStatus) {
        List<Order> orders = orderService.getHistoryOrdersByStatus(orderStatus);
        List<OrderResponse> orderResponseList = orders.stream()
                .map(OrderConverter::toOrderResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
    }

    @PutMapping("/history/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        Boolean result = orderService.cancelOrder(orderId);

        if (result) {
            return ResponseEntity.ok("Đơn hàng đã được hủy thành công!");
        } else {
            return ResponseEntity.badRequest().body("Không thể hủy đơn hàng. Đơn hàng không ở trạng thái chờ xác nhận.");
        }
    }
}
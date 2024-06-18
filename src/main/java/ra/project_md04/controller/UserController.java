package ra.project_md04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.model.dto.request.*;
import ra.project_md04.model.dto.response.AddressResponse;
import ra.project_md04.model.dto.response.ShoppingCartResponse;
import ra.project_md04.model.dto.response.WishListResponse;
import ra.project_md04.model.dto.response.converter.AddressConverter;
import ra.project_md04.model.dto.response.converter.ShoppingCartConverter;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Order;
import ra.project_md04.model.entity.ShoppingCart;
import ra.project_md04.model.entity.Users;
import ra.project_md04.service.*;

import java.util.List;

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
    public ResponseEntity<Users> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        Users updatedUser = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/account")
    public ResponseEntity<Users> getInfoUser() {
        Users infoUser = userService.getCurrentLoggedInUser();
        return new ResponseEntity<>(infoUser, HttpStatus.OK);
    }

    @PostMapping("/account/addresses")
    public ResponseEntity<Address> addNewAddress(@RequestBody AddressRequest addressRequest) {
        Address newAddress = addressService.addNewAddress(addressRequest);
        return new ResponseEntity<>(newAddress, HttpStatus.OK);
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

    @PostMapping("/cart/checkout")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(order);
    }


}
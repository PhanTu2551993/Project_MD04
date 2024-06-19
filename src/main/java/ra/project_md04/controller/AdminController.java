package ra.project_md04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.constans.OrderStatus;
import ra.project_md04.model.dto.request.CategoryRequest;
import ra.project_md04.model.dto.request.PageRequest;
import ra.project_md04.model.dto.request.ProductRequest;
import ra.project_md04.model.dto.request.UpdateOrderStatusRequest;
import ra.project_md04.model.dto.response.OrderResponse;
import ra.project_md04.model.dto.response.ProductResponse;
import ra.project_md04.model.dto.response.UserResponse;
import ra.project_md04.model.dto.response.converter.OrderConverter;
import ra.project_md04.model.dto.response.converter.ProductConverter;
import ra.project_md04.model.dto.response.converter.UserConverter;
import ra.project_md04.model.entity.*;
import ra.project_md04.service.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
		@Autowired
		IUserService userService;
		@Autowired
		private ICategoryService categoryService;
		@Autowired
		private IProductService productService;
		@Autowired
		private IRoleService roleService;
		@Autowired
		private IOrderService orderService;

	@GetMapping
	public ResponseEntity<?> admin() {
		return ResponseEntity.ok().body("đã vào được admin");
	}

	// User controller

	@GetMapping("/listUsers")
	public ResponseEntity<List<UserResponse>> getListUser(@RequestBody PageRequest pageRequest){
		List<Users> usersList = userService.getAllUser( pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
		List<UserResponse> userResponses = usersList.stream()
				.map(UserConverter::toUserResponse)//lamda exprestion
				.collect(Collectors.toList());
		return new ResponseEntity<>(userResponses, HttpStatus.OK);
	}

	@GetMapping("/searchAndPaging")
	public ResponseEntity<List<UserResponse>> getUserWithSearchAndPage(@RequestBody PageRequest pageRequest){
		List<Users> content = userService.getUserPaging(pageRequest.getName(), pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
		List<UserResponse> userResponses = content.stream()
				.map(UserConverter::toUserResponse)
				.collect(Collectors.toList());
		return new ResponseEntity<>(userResponses, HttpStatus.OK);
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<Users> changeUserStatus(@PathVariable Long userId, @RequestParam Boolean status) {
		Users changeUserStatus = userService.updateUserStatus(userId, status);
		return new ResponseEntity<>(changeUserStatus, HttpStatus.OK);
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Roles>> getAll(){
		return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
	}

	//Category Controller

	@PostMapping("/categories")
	public ResponseEntity<Category> insertCategory(@RequestBody CategoryRequest categoryRequest){
		return new ResponseEntity<>(categoryService.save(categoryRequest),HttpStatus.OK);
	}

	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest){
		return new ResponseEntity<>(categoryService.update(categoryId, categoryRequest),HttpStatus.OK);
	}

	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
		categoryService.delete(categoryId);
		return ResponseEntity.ok().body("đã xóa thành công danh mục có ID : " +categoryId);
	}

	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId){
		return new ResponseEntity<>(categoryService.findCategoryById(categoryId),HttpStatus.OK);
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getListCategory(@RequestBody PageRequest pageRequest){
		List<Category> categoryList = categoryService.getAllCategory( pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}

	// Product Controller

	@PutMapping("/products/{productId}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId,@RequestBody ProductRequest productRequest){
		Product product = productService.update(productId,productRequest);
		ProductResponse productResponse = ProductConverter.toProductResponse(product);
		return new ResponseEntity<>(productResponse,HttpStatus.OK);
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
		productService.delete(productId);
		return ResponseEntity.ok().body("đã xóa thành công sản phẩm có ID : " +productId);
	}

	@PostMapping("/products")
	public ResponseEntity<ProductResponse> insertProduct(@RequestBody ProductRequest productRequest){
		Product product = productService.save(productRequest);
		ProductResponse productResponse = ProductConverter.toProductResponse(product);
		return new ResponseEntity<>(productResponse,HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
		Product product = productService.findProductById(productId);
		if (product != null) {
			ProductResponse productResponse = ProductConverter.toProductResponse(product);
			return new ResponseEntity<>(productResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductResponse>> getListProduct(@RequestBody PageRequest pageRequest) {
		List<Product> productsList = productService.getAllProducts(pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
		List<ProductResponse> productResponses = productsList.stream()
				.map(ProductConverter::toProductResponse)
				.collect(Collectors.toList());
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}

	//Order

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/orders/{orderStatus}")
	public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus orderStatus) {
		List<Order> orders = orderService.getOrdersByStatus(orderStatus);
		List<OrderResponse> orderResponseList = orders.stream()
				.map(OrderConverter::toOrderResponse)
				.collect(Collectors.toList());
		return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
		Order order = orderService.getOrderById(orderId);
		OrderResponse orderResponse = OrderConverter.toOrderResponse(order);
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}

	@PutMapping("/orders/status/{orderId}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request) {
		boolean result = orderService.updateOrderStatus(orderId, request.getOrderStatus());

		if (result) {
			return ResponseEntity.ok("Cập nhật trạng thái đơn hàng thành công!");
		} else {
			return ResponseEntity.badRequest().body("Cập nhật trạng thái đơn hàng thất bại.");
		}
	}

}

package ra.project_md04.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.model.dto.request.PageRequest;
import ra.project_md04.model.dto.request.ProductRequest;
import ra.project_md04.model.dto.response.ProductResponse;
import ra.project_md04.model.dto.response.converter.ProductConverter;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.Users;
import ra.project_md04.service.IProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getListProduct(@RequestBody PageRequest pageRequest) {
        List<Product> productsList = productService.getAllProducts(pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
        List<ProductResponse> productResponses = productsList.stream()
                .map(ProductConverter::toProductResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<List<ProductResponse>> getProductWithSearchAndPage(@RequestBody PageRequest pageRequest){
        List<Product> productsList = productService.findProductsByProNameOrDescription(pageRequest.getName(), pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
        List<ProductResponse> productResponses = productsList.stream()
                .map(ProductConverter::toProductResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productResponses,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(@PathVariable Long categoryId){
        List<Product> productsList = productService.findProductsByCategoryId(categoryId);
        List<ProductResponse> productResponses = productsList.stream()
                .map(ProductConverter::toProductResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }
}

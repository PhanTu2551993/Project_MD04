package ra.project_md04.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.model.dto.request.PageRequest;
import ra.project_md04.model.dto.request.ProductRequest;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.Users;
import ra.project_md04.service.IProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getListProduct(@RequestBody PageRequest pageRequest){
        List<Product> productsList = productService.getAllProducts( pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<List<Product>> getProductWithSearchAndPage(@RequestBody PageRequest pageRequest){
        List<Product> content = productService.findProductsByProNameOrDescription(pageRequest.getName(), pageRequest.getPage() - 1, pageRequest.getItemPage(), pageRequest.getSortBy(), pageRequest.getDirection()).getContent();
        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId){
        List<Product> products = productService.findProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

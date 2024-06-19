package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.project_md04.model.dto.request.ProductRequest;
import ra.project_md04.model.dto.response.ProductResponse;
import ra.project_md04.model.entity.Category;
import ra.project_md04.model.entity.Product;
import ra.project_md04.repository.IProductRepository;
import ra.project_md04.service.ICategoryService;
import ra.project_md04.service.IProductService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryService categoryService;

    @Override
    public Page<Product> getAllProducts(Integer page, Integer itemPage, String orderBy, String direction) {
        Pageable pageable = null;
        if(orderBy!=null && !orderBy.isEmpty()){
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page, itemPage,sort);
        }else{
            pageable = PageRequest.of(page, itemPage);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findProductsByCategoryId(Long categoryId) {
        return productRepository.findProductsByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long proId) {
        return productRepository.findById(proId).orElseThrow(()->new NoSuchElementException("Không tồn tại sản phẩm có ID : "+proId));
    }

    @Override
    public Product save(ProductRequest productRequest) {
        Product product = new Product();
        product.setSku(UUID.randomUUID().toString());
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setCategory(categoryService.findCategoryById(productRequest.getCategoryId()));
        return productRepository.save(product);
    }

    @Override
    public Product update(Long productId,ProductRequest productRequest) {
        Product currentProduct = findProductById(productId);
        currentProduct.setProductName(productRequest.getProductName());
        currentProduct.setDescription(productRequest.getDescription());
        currentProduct.setImage(productRequest.getImage());
        currentProduct.setUnitPrice(productRequest.getUnitPrice());
        currentProduct.setStockQuantity(productRequest.getStockQuantity());
        currentProduct.setUpdatedAt(new Date());
        currentProduct.setCategory(categoryService.findCategoryById(productRequest.getCategoryId()));
        return productRepository.save(currentProduct);
    }

    @Override
    public void delete(Long proId) {
        productRepository.deleteById(proId);
    }

    @Override
    public Page<Product> findProductsByProNameOrDescription(String searchText,Integer page,Integer itemPage, String orderBy, String direction) {
        Pageable pageable = null;
        if (orderBy != null && !orderBy.isEmpty()) {
            Sort sort = null;
            switch (direction) {
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page, itemPage, sort);
        } else {
            pageable = PageRequest.of(page, itemPage);
        }
        if (searchText != null && !searchText.isEmpty()) {
            return productRepository.findProductsByProNameOrDescription(searchText, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }
}

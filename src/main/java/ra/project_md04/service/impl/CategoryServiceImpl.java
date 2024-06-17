package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.project_md04.model.dto.request.CategoryRequest;
import ra.project_md04.model.entity.Category;
import ra.project_md04.repository.ICategoryRepository;
import ra.project_md04.service.ICategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Page<Category> getAllCategory(Integer page, Integer itemPage, String orderBy, String direction) {
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
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(()->new NoSuchElementException("Không tồn tại danh mục có ID : "+catId));
    }

    @Override
    public Category save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        category.setStatus(categoryRequest.getStatus());
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long categoryId, CategoryRequest categoryRequest) {
        Category currentCategory = findCategoryById(categoryId);
        currentCategory.setCategoryName(categoryRequest.getCategoryName());
        currentCategory.setDescription(categoryRequest.getDescription());
        currentCategory.setStatus(categoryRequest.getStatus());
        return categoryRepository.save(currentCategory);
    }

    @Override
    public void delete(Long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<Category> findCategoryByName(String catName) {
        return List.of();
    }
}

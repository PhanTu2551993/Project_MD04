package ra.project_md04.service;


import org.springframework.data.domain.Page;
import ra.project_md04.model.dto.request.CategoryRequest;
import ra.project_md04.model.entity.Category;

import java.util.List;

public interface ICategoryService {
    Page<Category> getAllCategory(Integer page, Integer itemPage, String orderBy, String direction);
    List<Category> findAll();
    Category findCategoryById(Long catId);
    Category save(CategoryRequest categoryRequest);
    Category update(Long categoryId, CategoryRequest categoryRequest);
    void delete(Long catId);
    List<Category> findCategoryByName(String catName);
}

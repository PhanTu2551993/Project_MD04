package ra.project_md04.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.model.entity.Category;
import ra.project_md04.service.ICategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/getByName/{catName}")
    public ResponseEntity<List<Category>> getCategoryByName(@PathVariable String catName){
        return new ResponseEntity<>(categoryService.findCategoryByName(catName), HttpStatus.OK);
    }
}

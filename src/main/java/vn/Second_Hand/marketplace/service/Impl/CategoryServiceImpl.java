package vn.Second_Hand.marketplace.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.entity.Category;
import vn.Second_Hand.marketplace.repository.CategoryRepository;
import vn.Second_Hand.marketplace.service.ICategoryService;

import java.util.List;
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

package vn.Second_Hand.marketplace.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.entity.Category;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.repository.CategoryRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.service.IProductService;

import java.awt.print.Pageable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Product> getTop10BestSellingProducts() {
        return productRepository.findTop10BestSellers();
    }

    @Override
    public List<Product> getProductsCreatedLast7Days() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date sevenDaysAgo = calendar.getTime();
        return productRepository.findProductsCreatedLast7Days(sevenDaysAgo);
    }
    @Override
    public List<Product> searchProductsByName(String keyword) {
        return productRepository.searchByName(keyword);
    }



}

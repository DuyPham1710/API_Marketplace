package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.entity.Category;
import vn.Second_Hand.marketplace.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getProductsByCategory(int maDanhMuc);
    List<Product> getTop10BestSellingProducts();
    List<Product> getProductsCreatedLast7Days();
    List<Product> searchProductsByName(String keyword);
}

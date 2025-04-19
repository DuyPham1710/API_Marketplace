package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Category;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.ProductMapper;
import vn.Second_Hand.marketplace.repository.CategoryRepository;
import vn.Second_Hand.marketplace.repository.ProductImageRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.service.IProductService;

import java.awt.print.Pageable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements IProductService {
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    ProductMapper productMapper;
    // về sửa cái này
    @Override
    public ProductResponse getProductWithImages(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ProductImage> images = productImageRepository.findByProduct(product);

        return productMapper.toProductResponse(product, images);
    }

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

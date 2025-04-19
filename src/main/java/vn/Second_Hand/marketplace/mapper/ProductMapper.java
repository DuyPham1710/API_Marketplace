package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "product.category.categoryName", target = "categoryName")
    @Mapping(target = "initialImages", expression = "java(mapInitialImages(images))")
    @Mapping(target = "currentImages", expression = "java(mapCurrentImages(images))")
    ProductResponse toProductResponse(Product product, List<ProductImage> images);
    default List<String> mapInitialImages(List<ProductImage> images) {
        return images.stream()
                .map(ProductImage::getInitialImage)
                .collect(Collectors.toList());
    }

    default List<String> mapCurrentImages(List<ProductImage> images) {
        return images.stream()
                .map(ProductImage::getCurrentImage)
                .collect(Collectors.toList());
    }
}

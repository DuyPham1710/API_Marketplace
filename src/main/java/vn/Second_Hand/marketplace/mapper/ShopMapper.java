package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.dto.responses.ShopResponse;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    @Mapping(source = "user.avt", target = "avt")
    @Mapping(source = "user.username", target = "username")
    ShopResponse toShopResponse(User user, int totalReviews, double averageRating, List<Integer> followerIds, List<ProductResponse> products);
} 
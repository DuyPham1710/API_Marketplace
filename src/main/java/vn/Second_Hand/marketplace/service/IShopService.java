package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.FollowRequest;
import vn.Second_Hand.marketplace.dto.responses.ShopResponse;
import java.util.List;

public interface IShopService {
    List<ShopResponse> getAllShopsExceptCurrentUser();
    List<ShopResponse> getAllShops();
    String toggleFollow(FollowRequest request);
    List<Integer> getFollowedShopIds();
    ShopResponse getCurrentUserShop();
} 
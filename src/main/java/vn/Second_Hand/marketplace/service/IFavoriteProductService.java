package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.entity.FavoriteProduct;

import java.util.List;

public interface IFavoriteProductService {
    public String toggleFavorite(int userId, int productId);
    public List<FavoriteProduct> getFavorites(int userId);
}

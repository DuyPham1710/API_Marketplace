package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.CartRequest;
import vn.Second_Hand.marketplace.entity.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> getAllCartItems();
    String addToCart(CartRequest request);
    String updateCart(CartRequest request);
    String removeFromCart(CartRequest request);
}

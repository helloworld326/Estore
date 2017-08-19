package com.fly.service;

import java.util.List;

import com.fly.domain.Cart;
import com.fly.domain.User;

public interface CartService {

	int addToCart(Cart cart);

	List<Cart> findAllByUid(User user);

	void updateCartNum(Cart cart);

	void deleteCartGoodByGid(int gid);

}

package com.fly.service.Impl;

import java.sql.SQLException;
import java.util.List;

import com.fly.dao.CartDao;
import com.fly.dao.GoodDao;
import com.fly.dao.Impl.CartDaoImpl;
import com.fly.dao.Impl.GoodDaoImpl;
import com.fly.domain.Cart;
import com.fly.domain.Good;
import com.fly.domain.User;
import com.fly.service.CartService;

public class CartServiceImpl implements CartService {
	private CartDao cartDao = new CartDaoImpl();
	
	public int addToCart(Cart cart) {
		try {
			Cart cartDB = cartDao.findByUidAndGid(cart.getUid(), cart.getGid());
			if(cartDB == null){
				// 表中无次记录,数量设置为1
				cart.setBuynum(1);
				cartDao.addCart(cart);
			} else {
				// 表中已有记录,购买数量加1
				cartDB.setBuynum(cartDB.getBuynum() + 1);
				cartDao.update(cartDB.getUid(), cartDB.getGid(), cartDB.getBuynum());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public List<Cart> findAllByUid(User user) {
		List<Cart> cartList;
		try {
			cartList = cartDao.findAllByUid(user);
			GoodDao goodDao = new GoodDaoImpl();
			if(cartList != null){
				for (Cart cart : cartList) {
					Good good = goodDao.findGoodById(cart.getGid());
					cart.setGood(good);
				}
			}
			return cartList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateCartNum(Cart cart) {
		try {
			cartDao.update(cart.getUid(), cart.getGid(), cart.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCartGoodByGid(int gid) {
		try {
			cartDao.deleteCartGoodByGid(gid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

package com.niit.BackEnd.Service;

import com.niit.BackEnd.DAO.CartDAO;
import com.niit.BackEnd.Model.Cart;
import com.niit.BackEnd.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDAO cartDao;

    public Cart getCartById(int cartId) {
        return cartDao.getCartById(cartId);
    }

    public void update(Cart cart) {
        cartDao.update(cart);
    }
}

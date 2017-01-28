package com.niit.BackEnd.Service;

import com.niit.BackEnd.Model.Cart;

public interface CartService {

    Cart getCartById (int cartId);

    void update(Cart cart);
}

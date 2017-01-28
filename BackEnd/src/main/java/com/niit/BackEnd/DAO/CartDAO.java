package com.niit.BackEnd.DAO;

import com.niit.BackEnd.Model.Cart;

import java.io.IOException;

public interface CartDAO {

    Cart getCartById (int cartId);

    Cart validate(int cartId) throws IOException;

    void update(Cart cart);
}

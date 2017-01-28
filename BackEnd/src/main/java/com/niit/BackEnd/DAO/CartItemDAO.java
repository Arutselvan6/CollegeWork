package com.niit.BackEnd.DAO;

import com.niit.BackEnd.Model.Cart;
import com.niit.BackEnd.Model.CartItem;

public interface CartItemDAO {

    void addCartItem(CartItem cartItem);

    void removeCartItem(CartItem cartItem);

    void removeAllCartItems(Cart cart);

    CartItem getCartItemByProductId (int productId);

}

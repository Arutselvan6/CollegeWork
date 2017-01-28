package com.niit.BackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.BackEnd.Model.UserOrder;
import com.niit.BackEnd.DAO.UserOrderDAO;
import com.niit.BackEnd.Model.Cart;
import com.niit.BackEnd.Model.CartItem;

@Service
public class UserOrderServiceImpl implements UserOrderService {

	@Autowired
	UserOrderDAO ud;
	
	@Autowired
	CartService cartService;
	
	public void addUserOrder(UserOrder userOrder) {
		ud.addUserOrder(userOrder);
	}

	public double getUserOrderGrandTotal(int cartId) {
        double grandTotal=0;
        Cart cart = cartService.getCartById(cartId);
        List<CartItem> cartItems = cart.getCartItems();

        for (CartItem item : cartItems) {
            grandTotal+=item.getTotalPrice();
        }

        return grandTotal;
	}

}

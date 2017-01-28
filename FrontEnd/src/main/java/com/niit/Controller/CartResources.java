package com.niit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.niit.BackEnd.Model.Cart;
import com.niit.BackEnd.Model.CartItem;
import com.niit.BackEnd.Model.InUsers;
import com.niit.BackEnd.Model.Product;
import com.niit.BackEnd.Service.CartItemService;
import com.niit.BackEnd.Service.CartService;
import com.niit.BackEnd.Service.ProductService;
import com.niit.BackEnd.Service.UserService;

import java.util.List;

@Controller
@RequestMapping("/rest/cart")
public class CartResources {

	static AnnotationConfigApplicationContext ctx;
	static UserService us;
	static ProductService ps;
	static CartService cs;
	static CartItemService cis;
	static{
			ctx = new AnnotationConfigApplicationContext();
			ctx.scan("com.niit.BackEnd");
			ctx.refresh();
			us = (UserService)ctx.getBean("userService");
			ps = (ProductService)ctx.getBean("productService");
			cs = (CartService)ctx.getBean("cartService");
			cis = (CartItemService)ctx.getBean("cartItemService");
	}
	
    @RequestMapping("/{cartId}")
    public @ResponseBody
    Cart getCartById (@PathVariable(value = "cartId") int cartId) {
        return cs.getCartById(cartId);
    }

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addItem (@PathVariable(value ="productId") int productId, @AuthenticationPrincipal User activeUser) {

        InUsers customer = us.getUserByName(activeUser.getUsername());
        Cart cart = customer.getCart();
        Product product = ps.getProductById(productId);
        List<CartItem> cartItems = cart.getCartItems();
       
        for (int i=0; i<cartItems.size(); i++) {
            if(product.getProductId()==cartItems.get(i).getProduct().getProductId())
            {
                CartItem cartItem = cartItems.get(i);
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setTotalPrice(product.getProductPrice()*cartItem.getQuantity());
                cis.addCartItem(cartItem);

                return;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(product.getProductPrice()*cartItem.getQuantity());
        cartItem.setCart(cart);
        cis.addCartItem(cartItem);
    }

    @RequestMapping(value = "/remove/{productId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeItem (@PathVariable(value = "productId") int productId) {
        CartItem cartItem = cis.getCartItemByProductId(productId);
        cis.removeCartItem(cartItem);

    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable(value = "cartId") int cartId) {
        Cart cart = cs.getCartById(cartId);
        cis.removeAllCartItems(cart);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload.")
    public void handleClientErrors (Exception e) {}

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error.")
    public void handleServerErrors (Exception e) {}
}

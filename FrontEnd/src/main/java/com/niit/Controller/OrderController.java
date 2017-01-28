package com.niit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.BackEnd.Model.Cart;
import com.niit.BackEnd.Model.InUsers;
import com.niit.BackEnd.Model.UserOrder;
import com.niit.BackEnd.Service.CartService;
import com.niit.BackEnd.Service.UserOrderService;

@Controller
public class OrderController {

	static AnnotationConfigApplicationContext ctx;
	static CartService cs;
	static UserOrderService uos;
	static{
			ctx = new AnnotationConfigApplicationContext();
			ctx.scan("com.niit.BackEnd");
			ctx.refresh();
			cs = (CartService)ctx.getBean("cartService");
			uos = (UserOrderService)ctx.getBean("userOrderService");
	}
	
    @RequestMapping("/order/{cartId}")
    public String createOrder(@PathVariable("cartId") int cartId) {
        UserOrder customerOrder = new UserOrder();
        Cart cart=cs.getCartById(cartId);
        customerOrder.setCart(cart);

        InUsers customer = cart.getUser();
        customerOrder.setUser(customer);      
        uos.addUserOrder(customerOrder);

        return "redirect:/checkout?cartId="+cartId;
    }
}

package com.niit.Controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.BackEnd.Model.InUsers;
import com.niit.BackEnd.Service.UserService;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

	static AnnotationConfigApplicationContext ctx;
	static UserService us;
	static{
			ctx = new AnnotationConfigApplicationContext();
			ctx.scan("com.niit.BackEnd");
			ctx.refresh();
			us = (UserService)ctx.getBean("userService");
	}
	
	
    @RequestMapping
    public String getCart(@AuthenticationPrincipal User activeUser){
        InUsers customer = us.getUserByName (activeUser.getUsername());
        int cartId = customer.getCart().getCartId();

        return "redirect:/customer/cart/"+cartId;
    }

    @RequestMapping("/{cartId}")
    public String getCartRedirect(@PathVariable (value = "cartId") int cartId, Model model) {
        model.addAttribute("cartId", cartId);

        return "cart";
    }

}

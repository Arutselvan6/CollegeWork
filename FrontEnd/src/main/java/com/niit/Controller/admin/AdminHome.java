package com.niit.Controller.admin;

import com.niit.BackEnd.Model.InUsers;
import com.niit.BackEnd.Model.Product;
import com.niit.BackEnd.Service.ProductService;
import com.niit.BackEnd.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminHome {

	static AnnotationConfigApplicationContext ctx;
	static ProductService ps;
	static UserService us;
	static{
		ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.niit.BackEnd");
		ctx.refresh();
		ps = (ProductService)ctx.getBean("productService");
		us = (UserService)ctx.getBean("userService");
	}
    
    @RequestMapping
    public String adminPage() {
        return "adminhome";
    }

    @RequestMapping("/productInventory")
    public String productInventory(Model model) {
        List<Product> products = ps.getAllProducts();
        model.addAttribute("products", products);

        return "productInventory";
    }

    @RequestMapping("/customer")
    public String customerManagement(Model model) {

        List<InUsers> customerList = us.getAllUsers();
        model.addAttribute("customerList", customerList);

        return "customerManagement";
    }
}

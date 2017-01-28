package com.niit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.BackEnd.Model.InUsers;
import com.niit.BackEnd.Service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegisterController {

	static AnnotationConfigApplicationContext ctx;
	static UserService us;
	static{
			ctx = new AnnotationConfigApplicationContext();
			ctx.scan("com.niit.BackEnd");
			ctx.refresh();
			us = (UserService)ctx.getBean("userService");
	}
	
    @RequestMapping("/register")
    public String registerCustomer(Model model) {

        InUsers customer = new InUsers();
        model.addAttribute("customer", customer);

        return "registerCustomer";

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerCustomerPost(@Valid @ModelAttribute("customer") InUsers customer, BindingResult result,
                                       Model model) {

        if (result.hasErrors()) {
            return "registerCustomer";
        }

        List<InUsers> customerList=us.getAllUsers();

        for (int i=0; i< customerList.size(); i++) {
            if(customer.getEmail().equals(customerList.get(i).getEmail())) {
                model.addAttribute("emailMsg", "Email already exists");

                return "registerCustomer";
            }

            if(customer.getUname().equals(customerList.get(i).getUname())) {
                model.addAttribute("usernameMsg", "Username already exists");

                return "registerCustomer";
            }
        }

        customer.setEnabled(true);
        us.addUser(customer);

        return "registerCustomerSuccess";

    }

}

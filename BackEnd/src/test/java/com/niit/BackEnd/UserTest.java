package com.niit.BackEnd;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.BackEnd.Model.InUsers;
import com.niit.BackEnd.Service.UserService;

public class UserTest {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		
		context.scan("com.niit.BackEnd");
		context.refresh();
		
		UserService us = (UserService) context.getBean("userService");
		
		InUsers u = new InUsers();
		u.setUname("Arut");
		u.setUpass("123456");
		u.setUaddr("chennai");
		System.out.println("User Viewd Successfully");
		if(us.addUser(u)){
			System.out.println("User Added Successfully");
			u = new InUsers();
			u.setUname("Arut1");
			u.setUpass("12345");
			u.setUaddr("chn");
			us.addUser(u);
		}
		else{
			System.out.println("Unable to Add User.");
		}
		
		InUsers u1 = us.getUserById(1);
		
		if(u1 != null)
		{
			u1.setUname("Arutselvan");
			u1.setUaddr("TVM");
		
			if(us.updUser(u1)){
				System.out.println("User Updated Successfully");
			}
			else{
				System.out.println("Unable to Update User.");
			}
		}
		else{
			System.out.println("No Object with Id 1");
		}
		
		List<InUsers> l = us.getAllUsers();
		
		if(!l.isEmpty()){
			for(InUsers x : l)
				System.out.println(x);
		}
		else{
			System.out.println("No Data Found");
		}
		
		if(us.delUser(2)){
			System.out.println("User Deleted Successfully");
		}
		else{
			System.out.println("Unable to Delete User.");
		}

		context.close();
	}

}

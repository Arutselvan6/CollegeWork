package com.niit.BackEnd.Service;

import java.util.List;

import com.niit.BackEnd.Model.InUsers;

public interface UserService {

	boolean addUser(InUsers u);
	boolean delUser(int uid);
	boolean updUser(InUsers u);
	InUsers getUserById(int uid); 
	InUsers getUserByName(String name);
	List<InUsers> getAllUsers();
}

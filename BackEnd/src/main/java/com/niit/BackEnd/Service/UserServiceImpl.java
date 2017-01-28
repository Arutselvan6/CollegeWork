package com.niit.BackEnd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.BackEnd.DAO.UserDAO;
import com.niit.BackEnd.Model.InUsers;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO ud;
	
	public boolean addUser(InUsers u) {
		return ud.addUser(u);
	}

	public boolean delUser(int uid) {
		return ud.delUser(uid);
	}

	public boolean updUser(InUsers u) {
		return ud.updUser(u);
	}

	public InUsers getUserById(int uid) {
		return ud.getUserById(uid);
	}
	
	public InUsers getUserByName(String name){
		return ud.getUserByName(name);
	}

	public List<InUsers> getAllUsers() {
		return ud.getAllUsers();
	}

}

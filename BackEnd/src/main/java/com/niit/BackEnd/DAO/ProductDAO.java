package com.niit.BackEnd.DAO;

import java.util.List;

import com.niit.BackEnd.Model.Product;

public interface ProductDAO {

	void addProduct(Product p);
	void delProduct(int pid);
	void updProduct(Product p);
	Product getProductById(int pid);
	List<Product> getAllProducts();
}

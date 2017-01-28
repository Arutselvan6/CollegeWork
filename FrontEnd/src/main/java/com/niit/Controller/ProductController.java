package com.niit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niit.BackEnd.Model.Product;
import com.niit.BackEnd.Service.ProductService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

	static AnnotationConfigApplicationContext ctx;
	static ProductService ps;
	static{
			ctx = new AnnotationConfigApplicationContext();
			ctx.scan("com.nii"
					+ "t.BackEnd");
			ctx.refresh();
			ps = (ProductService)ctx.getBean("productService");
	}

    @RequestMapping("/productList/all")
    public String getProducts(Model model) {
        List<Product> products = ps.getAllProducts();
        model.addAttribute("products", products);

        return "productList";
    }
    
    @RequestMapping("/product/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        product.setProductCategory("instrument");
        product.setProductCondition("new");
        product.setProductStatus("active");

        model.addAttribute("product", product);

        return "addProduct";
    }

    @RequestMapping("/viewProduct/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) throws IOException {
        Product product=ps.getProductById(productId);
        model.addAttribute("product", product);

        return "viewProduct";
    }

    @RequestMapping("/productList")
    public String getProductByCategory(@RequestParam("searchCondition") String searchCondition, Model model) {
        List<Product> products = ps.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("searchCondition", searchCondition);

        return "productList";
    }
}

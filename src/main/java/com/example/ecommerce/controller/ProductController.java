package com.example.ecommerce.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;

import springfox.documentation.spring.web.json.Json;


@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }
   
    @GetMapping("/get/{id}")
    public Map<String, String > getProduct( @PathVariable  Integer id){
    	Map<String, String> responseObj = new HashMap<String, String>();
    	if(!id.equals(null)) {
        	Optional<Product> optionalProduct = productService.readProduct(id);
        	if(!optionalProduct.isPresent()) {
        		responseObj.put("error", "true");
        		responseObj.put("message","id not found");
        		return responseObj;
        	}
        	
        	Product product = optionalProduct.get();
        	
        	responseObj.put("name", product.getName());
        	responseObj.put("description", product.getDescription());
//        	responseObj.put("price", product.getPrice());
        	responseObj.put("imageURL", product.getImageURL());
        	return responseObj;
        	
    	}
    	responseObj.put("error","true");
    	responseObj.put("message","id required");
    	return responseObj;
    }
    
    
	@GetMapping("/products")
    public Map<Integer, Map<String,String>> getProducts() {
    	Map<Integer,Map<String,String>> response = new HashMap<Integer, Map<String,String>>();
    		
    		List<Product> productList= productService.listProducts();
    		for (Product product : productList) {
    			Map<String, String> responseObj = new HashMap<String, String>();
    			responseObj.put("name", product.getName());
            	responseObj.put("description", product.getDescription());
//            	responseObj.put("price", product.getPrice());
            	responseObj.put("imageURL", product.getImageURL());
            	response.put(product.getId(),responseObj);
			}
    		return response;
    
	}
}
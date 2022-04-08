package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
@Autowired
    private  ProductRepository productRepository;

	public void addProduct(ProductDto productDto, Category category) {
	    Product product = getProductFromDto(productDto, category);
	    productRepository.save(product);
	}
	public  Product getProductFromDto(ProductDto productDto, Category category) {
	    Product product = new Product();
	    product.setCategory(category);
	    product.setDescription(productDto.getDescription());
	    product.setImageURL(productDto.getImageURL());
	    product.setPrice(productDto.getPrice());
	    product.setName(productDto.getName());
	    return product;
	}
	public  Optional<Product> readProduct(Integer productId) {
		return productRepository.findById(productId);
	}
	public List<Product> listProducts() {
		return productRepository.findAll();
	}

}

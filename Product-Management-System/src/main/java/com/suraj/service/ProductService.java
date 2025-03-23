package com.suraj.service;

import java.util.List;

import com.suraj.dto.ProductDto;
import com.suraj.dto.ProductResponse;
import com.suraj.model.Product;

public interface ProductService {
	
	public Boolean saveProduct(ProductDto productDto);
	
	public List<ProductDto> getAllProduct();
	
	public ProductDto getProductById(Integer id);
	
	public Boolean deleteProduct(Integer id);
	
	public ProductResponse getProductWithPagination(Integer pageNo , Integer pageSize , String sortBy , String sortDir);

}

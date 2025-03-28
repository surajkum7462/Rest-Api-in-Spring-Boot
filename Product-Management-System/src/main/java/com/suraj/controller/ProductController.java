package com.suraj.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.ProductDto;
import com.suraj.dto.ProductResponse;
import com.suraj.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/save-product")
	public ResponseEntity<?> saveProduct(@RequestBody /* @Valid */ProductDto productDto) {

		try {
			validationProduct(productDto);

			Boolean saveProduct = productService.saveProduct(productDto);
			if (!ObjectUtils.isEmpty(saveProduct)) {
				return new ResponseEntity<>("Product Saved Success", HttpStatus.CREATED);
			}

		} catch (BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Product Not Saved ", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void validationProduct(ProductDto productDto) throws BadRequestException {
		if ( productDto.getName()!=null && productDto.getName().isEmpty()) {
			throw new BadRequestException("Name is not empty");
		}

		if (productDto.getName() == null) {
			throw new BadRequestException("Name is not null");
		}
		
		
		
		if ( productDto.getDescription()!=null && productDto.getDescription().isEmpty()) {
			throw new BadRequestException("Description is not empty");
		}

		if (productDto.getDescription() == null) {
			throw new BadRequestException("Description is not null");
		}
		
		
		if (productDto.getDescription()!= null && !productDto.getDescription().isEmpty() && (productDto.getDescription().length()<3 || productDto.getDescription().length()>10)) {
			throw new BadRequestException("Description is min and max 3-10");
		}

	}

	@GetMapping("/get-products")
	public ResponseEntity<?> getProducts() {
		List<ProductDto> allProduct = null;
		try {

			allProduct = productService.getAllProduct();
			if (CollectionUtils.isEmpty(allProduct)) {
				return new ResponseEntity<>("No any prodducts", HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(allProduct, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduct(@PathVariable(name = "id") Integer id) {

		ProductDto product = null;
		try {

			product = productService.getProductById(id);
			if (ObjectUtils.isEmpty(product)) {
				return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {
		Boolean deleteProduct = null;
		try {

			deleteProduct = productService.deleteProduct(id);
			if (!deleteProduct) {
				return new ResponseEntity<>("Product not available", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/page-products")
	public ResponseEntity<?> getProductsPaginate(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
		ProductResponse productResponse = null;
		String name = null;
		name.toUpperCase();// for checking exception handling
		try {
			productResponse = productService.getProductWithPagination(pageNo, pageSize, sortBy, sortDir);
			if (ObjectUtils.isEmpty(productResponse)) {
				return new ResponseEntity<>("No Products", HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

}

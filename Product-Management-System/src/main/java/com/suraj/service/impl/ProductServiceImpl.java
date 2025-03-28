package com.suraj.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.dto.ProductDto;
import com.suraj.dto.ProductResponse;
import com.suraj.dto.ProductResponse.ProductResponseBuilder;
import com.suraj.model.Product;
import com.suraj.repo.ProductRepo;
import com.suraj.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveProduct(ProductDto productDto) {

		/*
		 * This is complex approach so we use mapper Product product = new Product();
		 * product.setId(productDto.getId()); product.setName(productDto.getName());
		 * product.setPrice(productDto.getPrice());
		 * product.setQuantity(productDto.getQuantity());
		 * 
		 * product.setDescription(productDto.getDescription()); Product save =
		 * productRepo.save(product);
		 */

		Product save = mapper.map(productDto, Product.class);
		productRepo.save(save);

		if (!ObjectUtils.isEmpty(save)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<ProductDto> getAllProduct() {
		List<Product> productList = productRepo.findAll();

		List<ProductDto> productDtoList = productList.stream().map(product -> mapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		return productDtoList;
	}

	@Override
	public ProductDto getProductById(Integer id) {
		Optional<Product> findByProduct = productRepo.findById(id);
		if (findByProduct.isPresent()) {
			Product product = findByProduct.get();
			ProductDto productDto = mapper.map(product, ProductDto.class);
			return productDto;
		}
		return null;
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		Optional<Product> findByProduct = productRepo.findById(id);
		if (findByProduct.isPresent()) {
			Product product = findByProduct.get();
			productRepo.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public ProductResponse getProductWithPagination(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		
		/*
		 * Sort sort = Sort.by(sortBy).ascending(); Sort sort2 =
		 * Sort.by(sortBy).descending();
		 */
		
		Sort sort = 	sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		PageRequest pagebale = PageRequest.of(pageNo, pageSize,sort);
		Page<Product> page = productRepo.findAll(pagebale);

		List<Product> products = page.getContent();

		List<ProductDto> productDtos = products.stream().map(prod -> mapper.map(prod, ProductDto.class)).toList();

		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();

		boolean first = page.isFirst();
		boolean last = page.isLast();

		/*
		 * This is complex to set one by one so we use builder class ProductResponse
		 * productResponse = new ProductResponse();
		 * productResponse.setProducts(productDtos);
		 * 
		 * productResponse.setTotalElements(totalElements);
		 */

		ProductResponse productResponse = ProductResponse.builder().products(productDtos).totalElements(totalElements)
				.totalPages(totalPages).isFirst(first).isLast(last).pageNo(pageNo).pageSize(pageSize).build();

		return productResponse;
	}

}

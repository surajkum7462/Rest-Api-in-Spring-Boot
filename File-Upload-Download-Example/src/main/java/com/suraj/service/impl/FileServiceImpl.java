package com.suraj.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.suraj.model.Product;
import com.suraj.repo.ProductRepo;
import com.suraj.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	
	@Autowired
	private ProductRepo productRepo;

	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public Boolean uploadFile(MultipartFile file) throws IOException {

		String fileName = file.getOriginalFilename();
		File savefile = new File(uploadPath);

		if (!savefile.exists()) {
			savefile.mkdir();
		}

		String storePath = uploadPath.concat(fileName);

		long upload = Files.copy(file.getInputStream(), Paths.get(storePath));

		if (upload != 0) {
			return true;
		}
		return false;

	}

	@Override
	public byte[] downloadFile(String file) throws Exception {
		String fullPath = uploadPath.concat(file);// file/pig.jpeg
		try {
			FileInputStream ios = new FileInputStream(fullPath);
			return StreamUtils.copyToByteArray(ios);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		
	}

	@Override
	public Boolean saveProduct(Product product) {
		Product save = productRepo.save(product);
		if(!ObjectUtils.isEmpty(save))
		{
			return true;
		}
		return null;
	}

	@Override
	public String uploadFileWithData(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		File savefile = new File(uploadPath);
		
		String rndString = UUID.randomUUID().toString();
		//My_Photo.jpeg - > My_Photo_ufgvygfueevyyyyyvy.jpeg
		//My_Photo get then
		String removeExtension = FilenameUtils.removeExtension(fileName);
		
	    String extension = FilenameUtils.getExtension(fileName);
		
		
		fileName=removeExtension+"_"+rndString+"."+extension;

		if (!savefile.exists()) {
			savefile.mkdir();
		}

		String storePath = uploadPath.concat(fileName);

		long upload = Files.copy(file.getInputStream(), Paths.get(storePath));

		if (upload != 0) {
			return fileName;
		}
		return null;
	}

}

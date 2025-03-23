package com.suraj.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suraj.model.Product;
import com.suraj.service.FileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
		try {
			Boolean uploadFile = fileService.uploadFile(file);
			if (uploadFile) {
				return new ResponseEntity<>("Upload Successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("File Upload Failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/download")
	public ResponseEntity<?> downloadFile(@RequestParam String file) {
		try {
			byte[] downloadFile = fileService.downloadFile(file);
			String contentType = getContentType(file);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(contentType));

			headers.setContentDispositionFormData("attachment", file);

			return ResponseEntity.ok().headers(headers).body(downloadFile);

		} catch (FileNotFoundException e) {

			return new ResponseEntity<>("File Not Found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public String getContentType(String fileName) {
		String extension = FilenameUtils.getExtension(fileName).toLowerCase();

		switch (extension) {
		// Documents
		case "pdf":
			return "application/pdf";
		case "doc":
			return "application/msword";
		case "docx":
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		case "xls":
			return "application/vnd.ms-excel";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case "ppt":
			return "application/vnd.ms-powerpoint";
		case "pptx":
			return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		case "txt":
			return "text/plain";
		case "csv":
			return "text/csv";
		case "json":
			return "application/json";
		case "xml":
			return "application/xml";

		// Images
		case "jpeg":
		case "jpg":
			return "image/jpeg";
		case "png":
			return "image/png";
		case "gif":
			return "image/gif";
		case "bmp":
			return "image/bmp";
		case "tiff":
		case "tif":
			return "image/tiff";
		case "svg":
			return "image/svg+xml";
		case "webp":
			return "image/webp";

		// Audio
		case "mp3":
			return "audio/mpeg";
		case "wav":
			return "audio/wav";
		case "ogg":
			return "audio/ogg";
		case "aac":
			return "audio/aac";

		// Video
		case "mp4":
			return "video/mp4";
		case "avi":
			return "video/x-msvideo";
		case "mov":
			return "video/quicktime";
		case "wmv":
			return "video/x-ms-wmv";
		case "flv":
			return "video/x-flv";
		case "mkv":
			return "video/x-matroska";
		case "webm":
			return "video/webm";

		// Compressed files
		case "zip":
			return "application/zip";
		case "rar":
			return "application/vnd.rar";
		case "tar":
			return "application/x-tar";
		case "gz":
			return "application/gzip";
		case "7z":
			return "application/x-7z-compressed";

		// Default case for unknown file types
		default:
			throw new IllegalArgumentException("Unsupported file type: " + extension);
		}
	}

	@PostMapping("/upload-data")
	public ResponseEntity<?> uploadFileWithData(@RequestParam String product, @RequestParam MultipartFile file) {

//		log.info("Product:{}", product);
//		log.info("file:{}", file);
		
		
		List<String> extension = Arrays.asList("jpeg","png","jpg");
		
		if(file.isEmpty())
		{
			return new ResponseEntity<>("Please upload the file",HttpStatus.BAD_REQUEST);
		}
		else
		{
			String originalFilename = file.getOriginalFilename();
			String fileExtension = FilenameUtils.getExtension(originalFilename);
			boolean contains = extension.contains(fileExtension);
			if(!contains)
			{
				return new ResponseEntity<>("Please upload jpeg,png and jpg",HttpStatus.BAD_REQUEST);
			}
		}
		
		

		try {
			String fileName = fileService.uploadFileWithData(file);
			if (StringUtils.hasText(fileName)) {
				// Convert String Product into Product Object
				
				ObjectMapper objectMapper = new ObjectMapper();
				Product productObj = objectMapper.readValue(product, Product.class);
				//String fileName = file.getOriginalFilename();
				
				productObj.setImageName(fileName);
				Boolean saveProduct = fileService.saveProduct(productObj);
				
				if(saveProduct)
				{
					return new ResponseEntity<>("Product and Image Uploaded success", HttpStatus.OK);
				}
				
				else
				{
					return new ResponseEntity<>("File Uploaded but product not saved", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				
			} else {
				return new ResponseEntity<>("File Upload Failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) { // TODO: handle exception return new
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

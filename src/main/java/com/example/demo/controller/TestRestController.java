package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.User;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;




/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")

public class TestRestController {


	private ResponseEntity<?> responseEntity;
	static final long EXPIRATION_TIME = 300000;
	@Autowired
	private AmazonS3 client;
	@Value("classpath:resource-data.txt")
	Resource resourceFile;

	
	 @GetMapping("user")
	 public ResponseEntity<?> getUserById() {
			try {
				User user = new User();
				user.setUserName("poc-bucket-debu");
				user.setUserPassword("Debu");
				
				
				client.putObject(
					    "poc-bucket100",
					    "testFileFromPath",
					    new File("C:\\Synverse\\codes\\TestCOS\\src\\main\\resources\\test.txt")
					);
				client.putObject("poc-bucket100", "testkeytest", "testvalue");
				responseEntity = new ResponseEntity<>("Hello",HttpStatus.OK);
				//createBucket("try100");
			}
			
			catch (Exception e) {
				e.printStackTrace();
				responseEntity = new ResponseEntity<>("Error !! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return responseEntity;
		}
	 
	 
	 public  void createBucket(String bucketName) {
		    System.out.printf("Creating new bucket: %s\n", bucketName);
		    client.createBucket(bucketName);
		    System.out.printf("Bucket: %s created!\n", bucketName);
		}
	 
		
	 
}

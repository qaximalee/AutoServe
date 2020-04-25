package com.balti.autoserve.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.balti.autoserve.entities.Restaurant;
import com.balti.autoserve.exceptions.RestaurantException;
import com.balti.autoserve.services.FileStorageService;
import com.balti.autoserve.services.RestaurantService;

@RestController
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
    @Autowired
    private FileStorageService fileStorageService;
	
	@GetMapping("/admin/get/restaurantByPaging")
	public List<Restaurant> getAllRestaurantByPaging(
			@RequestParam Integer pageNo, 
			@RequestParam Integer pageSize, 
			@RequestParam String sortBy) throws RestaurantException {
		return restaurantService.getAllRestaurantPageable(pageNo, pageSize, sortBy);
	}
	
	@GetMapping("/admin/get/restaurant")
	public List<Restaurant> getAllRestaurant(){
		return restaurantService.getAllRestaurant();
	}
	
	@GetMapping("/admin/get/restaurantByFilter")
	public List<Restaurant> getAllRestaurantByFilter(
			@RequestParam String name, 
			@RequestParam String city, 
			@RequestParam String province, 
			@RequestParam String country) throws RestaurantException{
		return restaurantService.getAllRestaurantByFilter(name, city, province, country);
	}
	
	
	@GetMapping("/admin/get/singleRestaurantByFilter")
	public Restaurant getRestaurantByFilter(
			@RequestParam Long id, 
			@RequestParam String email, 
			@RequestParam String website, 
			@RequestParam String phone) throws RestaurantException {
		return restaurantService.getRestaurantByFilter(id, email, website, phone);
	}
	
	@PostMapping("/admin/add/restaurant")
	public Restaurant addRestaurant(
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String website,
			@RequestParam String address1,
			@RequestParam String address2,
			@RequestParam String phone1,
			@RequestParam String phone2,
			@RequestParam boolean active,
			@RequestParam String city,
			@RequestParam String province,
			@RequestParam String country,
			@RequestParam String latitude,
			@RequestParam String longitude,
			@RequestParam("file") MultipartFile file
			) throws RestaurantException {
		
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
		
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName(name);
		restaurant.setEmail(email);
		restaurant.setWebsite(website);
		restaurant.setAddress1(address1);
		restaurant.setAddress2(address2);
		restaurant.setPhone1(phone1);
		restaurant.setPhone2(phone1);
		restaurant.setActive(active);
		restaurant.setCity(city);
		restaurant.setProvince(province);
		restaurant.setCountry(country);
		restaurant.setLatitude(latitude);
		restaurant.setLongitude(longitude);
		restaurant.setImageUrl(fileDownloadUri);
		return restaurantService.addRestaurant(restaurant);
	}
	
	@PostMapping("/admin/update/restaurant")
	public Restaurant updateRestaurant(
			@RequestParam Long id,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String website,
			@RequestParam String address1,
			@RequestParam String address2,
			@RequestParam String phone1,
			@RequestParam String phone2,
			@RequestParam boolean active,
			@RequestParam String city,
			@RequestParam String province,
			@RequestParam String country,
			@RequestParam String latitude,
			@RequestParam String longitude,
			@RequestParam("file") MultipartFile file) throws RestaurantException {
		
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
		
		
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurant.setName(name);
		restaurant.setEmail(email);
		restaurant.setWebsite(website);
		restaurant.setAddress1(address1);
		restaurant.setAddress2(address2);
		restaurant.setPhone1(phone1);
		restaurant.setPhone2(phone1);
		restaurant.setActive(active);
		restaurant.setCity(city);
		restaurant.setProvince(province);
		restaurant.setCountry(country);
		restaurant.setLatitude(latitude);
		restaurant.setLongitude(longitude);
		restaurant.setImageUrl(fileDownloadUri);
		
		return restaurantService.updateRestaurant(restaurant);
	}
	
	@PostMapping("/admin/delete/restaurant")
	public ResponseEntity<Map<String, String>> deleteRestaurant(@RequestParam Long id){
		Map<String, String> response = new HashMap<>();
		try {
			response.put("exist", "Restaurant deleted successfully");
			restaurantService.deleteRestaurant(id);
			return new ResponseEntity<Map<String, String>>(response, null, HttpStatus.OK);
		}catch(RestaurantException e) {
			e.printStackTrace();
			response.put("exist", "Restaurant not exist");
			return new ResponseEntity<Map<String, String>>(response, null, HttpStatus.NOT_FOUND);
		}
		
	}
}

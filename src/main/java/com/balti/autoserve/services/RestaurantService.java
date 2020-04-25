package com.balti.autoserve.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.balti.autoserve.constants.ExceptionConstants;
import com.balti.autoserve.entities.Restaurant;
import com.balti.autoserve.exceptions.RestaurantException;
import com.balti.autoserve.repositories.RestaurantRepository;

@Service
public class RestaurantService {

	
	@Autowired
	private RestaurantRepository restaurantRepo;
	
	public List<Restaurant> getAllRestaurantPageable(Integer pageNo, Integer pageSize, String sortBy) throws RestaurantException{
		Pageable paging = null;
		if(sortBy.equals(""))
			paging = PageRequest.of(pageNo, pageSize);
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<Restaurant> pagedResult = restaurantRepo.findByActiveTrue(paging); 
		
		if(pagedResult.hasContent())
			return pagedResult.getContent();
		else
			throw new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND);
	}
	
	public List<Restaurant> getAllRestaurant(){
		return restaurantRepo.findAll();
	}
	
	public List<Restaurant> getAllRestaurantByFilter(String name, String city, String province, String country) throws RestaurantException{
		List<Restaurant> restaurantList = null;
		if(!name.equals("")) {
			restaurantList = restaurantRepo.findByNameLike(name);
			restaurantList.stream().findAny().orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "Name: " + name));
		}else if(!city.equals("")) {
			restaurantList = restaurantRepo.findByCityAndActiveTrue(city);
			restaurantList.stream().findAny().orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "City: " + city));
		}else if(!province.equals("")) {
			restaurantList = restaurantRepo.findByProvinceAndActiveTrue(province);
			restaurantList.stream().findAny().orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "Province: " + province));
		}else if(!country.equals("")) {
			restaurantList = restaurantRepo.findByCountryAndActiveTrue(country);
			restaurantList.stream().findAny().orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "Country: " + country));
		}
		
		return restaurantList;
	}
	
	public Restaurant getRestaurantByFilter(Long id, String email, String website, String phone) throws RestaurantException{
		Optional<Restaurant> restaurant = null;
		if(id != null) {
			restaurant = restaurantRepo.findById(id);
			restaurant.orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "ID: " + id));
		}else if(!email.equals("")) {
			restaurant = restaurantRepo.findByEmail(email);
			restaurant.orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "Email: " + email));
		}else if(!website.equals("")) {
			restaurant = restaurantRepo.findByWebsite(website);
			restaurant.orElseThrow(()-> new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "Website: " + website));
		}else if(!phone.equals("")) {
			if(restaurantRepo.findByPhone1(phone).isPresent()) 
				restaurant = restaurantRepo.findByPhone1(phone);
			else if(restaurantRepo.findByPhone2(phone).isPresent()) 
				restaurant = restaurantRepo.findByPhone2(phone);
			else
				throw new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "Phone: " + phone);
		}
		
		if(restaurant.get().isActive()) 
			throw new RestaurantException(ExceptionConstants.RESTAURANT_IS_NOT_ACTIVE);
		else
			return restaurant.get();
	}
	
	public Restaurant addRestaurant(Restaurant restaurant) throws RestaurantException {
		if(restaurantRepo.findByName(restaurant.getName()).isPresent())
			throw new RestaurantException(ExceptionConstants.RESTAURANT_ALREADY_EXIST + " Name: " + restaurant.getName());
		return restaurantRepo.save(restaurant);
	}
	
	public Restaurant updateRestaurant(Restaurant restaurant) throws RestaurantException{
		if(restaurantRepo.findById(restaurant.getId()).isPresent()) {
			Restaurant updatedRestaurant = new Restaurant();
			updatedRestaurant.setId(restaurant.getId());
			updatedRestaurant.setActive(restaurant.isActive());
			updatedRestaurant.setAddress1(restaurant.getAddress1());
			updatedRestaurant.setAddress2(restaurant.getAddress2());
			updatedRestaurant.setCity(restaurant.getCity());
			updatedRestaurant.setCountry(restaurant.getCountry());
			updatedRestaurant.setEmail(restaurant.getEmail());
			updatedRestaurant.setImageUrl(restaurant.getImageUrl());
			updatedRestaurant.setLatitude(restaurant.getLatitude());
			updatedRestaurant.setLongitude(restaurant.getLongitude());
			updatedRestaurant.setWebsite(restaurant.getWebsite());
			updatedRestaurant.setPhone1(restaurant.getPhone1());
			updatedRestaurant.setPhone2(restaurant.getPhone2());
			updatedRestaurant.setProvince(restaurant.getProvince());
			return restaurantRepo.save(updatedRestaurant);
		}else {
			throw new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "ID: " + restaurant.getId());
		}
	}
	
	public void deleteRestaurant(Long restaurantId) throws RestaurantException {
		if(restaurantRepo.findById(restaurantId).isPresent())
			restaurantRepo.deleteById(restaurantId);
		else
			throw new RestaurantException(ExceptionConstants.RESTAURANT_NOT_FOUND + "ID: " + restaurantId);
	}
	
}

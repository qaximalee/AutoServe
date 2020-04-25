package com.balti.autoserve.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.balti.autoserve.entities.Address;
import com.balti.autoserve.exceptions.AddressException;
import com.balti.autoserve.services.AddressService;

@RestController
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	
	@GetMapping(value= {"/user/get/addresses", "/kitchen/get/addresses"})
	public List<Address> getAllAddress(){
		return addressService.getAllAddress();
	}
	
	@GetMapping(value= {"/user/get/addressByFilter", "/kitchen/get/addressByFilter"})
	public Address getAddressByFilter(
			@RequestParam Long id,
			@RequestParam Long userId
			) throws AddressException {
		return addressService.getAddressByFilter(id, userId);
	}
	
	@GetMapping(value= {"/user/get/address", "/kitchen/get/address"})
	public Address addAddress(@RequestBody Address address) throws AddressException {
		return addressService.addAddress(address);
	}
	
	@PostMapping(value= {"/user/update/address","/kitchen/update/address/"})
	public Address updateAddress(@RequestBody Address address) throws AddressException {
		return addressService.updateAddress(address);
	}
	
	@PostMapping(value= {"/user/delete/address","/kitchen/delete/address/"})
	public ResponseEntity<Map<String, String>> deleteAddress(@RequestParam Long id){
		Map<String, String> response = new HashMap<>();
		try {
			response.put("exist", "Address deleted successfully");
			addressService.deleteAddress(id);
			return new ResponseEntity<Map<String, String>>(response, null, HttpStatus.OK);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.put("exist", "Address not exist");
			return new ResponseEntity<Map<String, String>>(response, null, HttpStatus.NOT_FOUND);
		}
	}
}

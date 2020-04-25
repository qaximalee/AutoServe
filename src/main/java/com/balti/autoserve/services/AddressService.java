package com.balti.autoserve.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balti.autoserve.constants.ExceptionConstants;
import com.balti.autoserve.entities.Address;
import com.balti.autoserve.exceptions.AddressException;
import com.balti.autoserve.repositories.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepo;
	
	public Address addAddress(Address address) throws AddressException {
		if(addressRepo.findById(address.getId()).isPresent())
			throw new AddressException(ExceptionConstants.ADDRESS_ALREADY_EXIST + "ID: " + address.getId());
		return addressRepo.save(address);
	}
	
	public List<Address> getAllAddress(){
		return addressRepo.findAll();
	}
	
	public Address getAddressByFilter(Long id, Long userId) throws AddressException{
		Optional<Address> address = null;
		
		if(id != null) {
			address = addressRepo.findById(id);
			address.orElseThrow( () -> new AddressException(ExceptionConstants.ADDRESS_NOT_FOUND + "ID: " + id));
		}else {
			address = addressRepo.findByUserId(userId);
			address.orElseThrow(()-> new AddressException(ExceptionConstants.ADDRESS_NOT_FOUND + "User ID: " + userId));
		}
		
		return address.get(); 
	}
	
	public Address updateAddress(Address address) throws AddressException{
		if(addressRepo.findById(address.getId()).isPresent()) {
			Address updateAddress = new Address();
			updateAddress.setId(address.getId());
			updateAddress.setUser(address.getUser());
			updateAddress.setAddress1(address.getAddress1());
			updateAddress.setAddress2(address.getAddress2());
			updateAddress.setZipcode(address.getZipcode());
			updateAddress.setCity(address.getCity());
			updateAddress.setProvince(address.getProvince());
			updateAddress.setCountry(address.getCountry());
			updateAddress.setLatitude(address.getLatitude());
			updateAddress.setLongitude(address.getLongitude());
			
			return addressRepo.save(updateAddress);
		}
		throw new AddressException(ExceptionConstants.ADDRESS_NOT_FOUND + "ID: " + address.getId());
	}
	
	public void deleteAddress(Long id) throws AddressException{
		if(addressRepo.findById(id).isPresent()) 
			addressRepo.deleteById(id);
		else
			throw new AddressException(ExceptionConstants.ADDRESS_NOT_FOUND + "ID: " + id);
	}
}

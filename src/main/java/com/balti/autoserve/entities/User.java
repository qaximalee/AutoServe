package com.balti.autoserve.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.balti.autoserve.enums.Gender;
import com.sun.istack.internal.NotNull;

@Entity
public class User extends AuditModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String userName;
	
	@NotNull
	@Column
	private boolean active;
	
	@NotNull
	@Column
	private String roles;
	
	@Size(min=5, max=20)
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@Size(min=5, max=20)
	@Column(name="last_name", nullable=false)
	private String lastName;
	
	@Size(min=5, max=40)
	@Column(name="full_name", nullable=true)
	private String fullName;
	
	@Column(name="nic", nullable=true)
	@Size(min=13, max=13)
	private String NIC;
	
	@NotNull
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@NotNull
	@Column(name="password", nullable=false)
	private String password;
	
	@Null
	@Column
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	
	@OneToOne
	//@JoinColumn(name = "restaurant_id", nullable = true)
	@Null
	private Restaurant restaurant;
	
	@OneToOne
    //@JoinColumn(name = "address_id", nullable = true)
	@Null
	private Address address;

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNIC() {
		return NIC;
	}

	public void setNIC(String nIC) {
		NIC = nIC;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}

package com.balti.autoserve.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Setting extends AuditModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name="logo_url")
	private String logoUrl;
	
	@Null
	@Column(name="background_color")
	private String backgroundColor;
	
	@NotBlank
	@Column(name="banner_page_image_url")
	private String bannerPageImageUrl;
	
	@NotNull
	@OneToOne
	private Restaurant restaurant;

	public Setting() { }

	public Setting(Long id, @NotBlank String logoUrl, @Null String backgroundColor, @NotBlank String bannerPageImageUrl,
			@NotNull Restaurant restaurant) {
		super();
		this.id = id;
		this.logoUrl = logoUrl;
		this.backgroundColor = backgroundColor;
		this.bannerPageImageUrl = bannerPageImageUrl;
		this.restaurant = restaurant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBannerPageImageUrl() {
		return bannerPageImageUrl;
	}

	public void setBannerPageImageUrl(String bannerPageImageUrl) {
		this.bannerPageImageUrl = bannerPageImageUrl;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}

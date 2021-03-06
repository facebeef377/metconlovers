package com.metcon.metconlovers.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Event {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	private String name;
	private String description;
	private boolean type;
	public Timestamp date_start;

	public Timestamp getDate_start() {
		return date_start;
	}

	public void setDate_start(Timestamp date_start) {
		this.date_start = date_start;
	}

	public Timestamp getDate_end() {
		return date_end;
	}

	public void setDate_end(Timestamp date_end) {
		this.date_end = date_end;
	}

	public Timestamp date_end;
	private String country;
	private String city;
	private String zipcode;
	private String street;
	private String flat;
	private String home;
	private String phone;
	private String email;
	private String url_fb;
	private String url_logo;

	public boolean isType() {
		return type;
	}

	private String url_insta;
	private String url_yt;
	private Integer owner_id;
	private String status;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private Timestamp timestamp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl_fb() {
		return url_fb;
	}
	public void setUrl_fb(String url_fb) {
		this.url_fb = url_fb;
	}
	public String getUrl_logo() {
		return url_logo;
	}
	public void setUrl_logo(String url_logo) {
		this.url_logo = url_logo;
	}
	public String getUrl_insta() {
		return url_insta;
	}
	public void setUrl_insta(String url_insta) {
		this.url_insta = url_insta;
	}
	public String getUrl_yt() {
		return url_yt;
	}
	public void setUrl_yt(String url_yt) {
		this.url_yt = url_yt;
	}
	public Integer getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

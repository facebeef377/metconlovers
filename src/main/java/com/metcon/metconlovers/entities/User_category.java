package com.metcon.metconlovers.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
@Entity@IdClass(User_categoryId.class)
public
class User_category {
	
	@Id
	private Integer user_id;
	@Id
	private Integer cat_id;
	

	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getCat_id() {
		return cat_id;
	}
	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}

}
@Embeddable
class User_categoryId implements Serializable {
    Integer user_id;
    Integer cat_id;}
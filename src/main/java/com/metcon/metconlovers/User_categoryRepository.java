package com.metcon.metconlovers;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.metcon.metconlovers.User_category;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface User_categoryRepository extends CrudRepository<User_category, Integer> {
	
	
}
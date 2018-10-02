package com.metcon.metconlovers;

import java.util.List;

import com.metcon.metconlovers.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.event_id = ?1")
	List<Category> getCategoriesByEventID(Integer event_id);



}
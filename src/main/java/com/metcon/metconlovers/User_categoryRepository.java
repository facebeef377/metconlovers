package com.metcon.metconlovers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.metcon.metconlovers.entities.User_category;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface User_categoryRepository extends CrudRepository<User_category, Integer> {

    @Query(value = "DELETE FROM user_category WHERE user_id=?1 AND cat_id=?2",nativeQuery = true)
    List<Object> removePlayer(Integer user_id, Integer cat_id);


	
}
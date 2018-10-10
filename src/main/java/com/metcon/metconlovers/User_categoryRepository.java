package com.metcon.metconlovers;

import com.metcon.metconlovers.entities.User_category;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface User_categoryRepository extends CrudRepository<User_category, Integer> {


}
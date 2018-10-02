package com.metcon.metconlovers;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.metcon.metconlovers.entities.Workout;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface WorkoutRepository extends CrudRepository<Workout, Integer> {
	
	@Query("SELECT w FROM Workout w WHERE w.cat_id = ?1")
	List<Workout> getWorkoutsByCatID(Integer cat_id);

	@Query(value = "SELECT * FROM workout WHERE id = ?1 LIMIT 1",nativeQuery = true)
	Workout getWorkoutsByID(Integer id);
	
}
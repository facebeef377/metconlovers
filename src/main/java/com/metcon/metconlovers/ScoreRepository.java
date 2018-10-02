package com.metcon.metconlovers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.metcon.metconlovers.entities.Score;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ScoreRepository extends CrudRepository<Score, Integer> {

    @Query(value = "SELECT * FROM score  WHERE workout_id = ?1 AND user_id= ?2 LIMIT 1",nativeQuery = true)
   Score findByWorkout_idAndUser_id(Integer workout_id,Integer user_id);

    @Query("SELECT m FROM Score m WHERE m.workout_id = ?1 ")
    List<Score> findByWorkout_id(Integer workout_id);

    @Query("SELECT m FROM Score m WHERE m.user_id = ?1")
    List<Score> findByUserId(Integer user_id);


	
}
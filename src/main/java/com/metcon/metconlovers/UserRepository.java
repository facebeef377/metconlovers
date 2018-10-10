package com.metcon.metconlovers;

import com.metcon.metconlovers.entities.MetconUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<MetconUser, Integer> {

    MetconUser findByLogin(String login);
    
    @Query(value = "SELECT * FROM metcon_user WHERE login = ?1 LIMIT 1",nativeQuery = true)
	MetconUser getUserByLogin(String user_login);

    @Query(value = "SELECT * FROM metcon_user WHERE email = ?1",nativeQuery = true)
    MetconUser getUserByEmail(String user_email);
    
    @Query("SELECT mu FROM MetconUser mu, User_category uc WHERE uc.user_id = mu.id AND uc.cat_id = ?1")
  	List<MetconUser> getPlayersByCatID(Integer cat_id);

    @Query("SELECT   mu.id, mu.name, mu.surname, w.name, s.score_reps, s.score_time, s.score_weight,w.id FROM Workout w INNER JOIN User_category uc ON w.cat_id = uc.cat_id INNER JOIN MetconUser mu ON uc.user_id = mu.id LEFT OUTER JOIN Score s ON s.workout_id = w.id AND s.user_id = uc.user_id AND s.user_id = mu.id  WHERE uc.cat_id = ?1")
    Iterable<Object> getScoresByCatID(Integer cat_id);
}
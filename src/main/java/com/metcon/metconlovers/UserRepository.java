package com.metcon.metconlovers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.metcon.metconlovers.MetconUser;
import com.metcon.metconlovers.User_category;

public interface UserRepository extends CrudRepository<MetconUser, Integer> {

    public MetconUser findByLogin(String login);
    
    @Query("SELECT m FROM MetconUser m WHERE m.login = ?1")
	MetconUser getUserByLogin(String user_login);

    @Query("SELECT m FROM MetconUser m WHERE m.email = ?1")
    MetconUser getUserByEmail(String user_email);
    
    @Query("SELECT mu FROM MetconUser mu, User_category uc WHERE uc.user_id = mu.id AND uc.cat_id = ?1")
  	List<MetconUser> getPlayersByCatID(Integer cat_id);

    @Query("SELECT   mu.id, mu.name, mu.surname, w.name, s.score_reps, s.score_time, s.score_weight,w.id FROM Workout w INNER JOIN User_category uc ON w.cat_id = uc.cat_id INNER JOIN MetconUser mu ON uc.user_id = mu.id LEFT OUTER JOIN Score s ON s.workout_id = w.id AND s.user_id = uc.user_id AND s.user_id = mu.id  WHERE uc.cat_id = 19")
    Iterable<Object> getScoresByCatID(Integer cat_id);
}
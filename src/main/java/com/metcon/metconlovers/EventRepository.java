package com.metcon.metconlovers;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.metcon.metconlovers.entities.Event;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends CrudRepository<Event, Integer> {

	@Query(value="SELECT * FROM event WHERE owner_id = ?1", nativeQuery = true)
	List<Event> getEventByOwnerID(Integer owner_id);

    @Query(value="SELECT * FROM event,category,workout WHERE workout.cat_id=category.id AND  category.event_id=event.id AND workout.id= ?1 LIMIT 1", nativeQuery = true)
    Event getEventByWorkoutID(Integer workout_id);

    @Query(value="SELECT * FROM event WHERE id = ?1 LIMIT 1", nativeQuery = true)
    Event getEventByID(Integer id);

    @Query(value="SELECT * FROM event WHERE date_end >= NOW() AND date_end  < NOW() + INTERVAL 10 DAY ORDER BY date_end ASC LIMIT 5", nativeQuery = true)
    List<Event> getRecentEvents();
	
}
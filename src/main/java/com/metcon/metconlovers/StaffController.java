package com.metcon.metconlovers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metcon.metconlovers.Event;
import com.metcon.metconlovers.EventRepository;
import com.metcon.metconlovers.Category;
import com.metcon.metconlovers.CategoryRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/staff") // This means URL's start with /demo (after Application path)
public class StaffController {
	
	@Autowired
	private EventRepository events;
	@Autowired
	private CategoryRepository categories;
	@Autowired
	private WorkoutRepository workouts;
	
	/////////////////////////////////////////////////////
	//		LISTOWANIE
	/////////////////////////////////////////////////////

	//Zwraca liste wszystkich eventow w serwisie
	@PostMapping(path="/getAllEvents")
	public @ResponseBody Iterable<Event> getAllEvents() {
		return events.findAll();
	}
	
	//Zwraca liste wszystkich kategorii w serwisie
	@PostMapping(path="/getAllCategories")
	public @ResponseBody Iterable<Category> getAllCategories() {
		return categories.findAll();
	}
	
	//Zwraca liste wszystkich treningow w serwisie
	@PostMapping(path="/getAllWorkouts")
	public @ResponseBody Iterable<Workout> getAllWorkouts() {
		return workouts.findAll();
	}
	
	//Zwraca liste eventow przypisanych do organizatora
	@PostMapping(path="/getEventsByOwnerID")
	public @ResponseBody List<Event> getEventByOwnerID(@RequestBody Map<String,Object> payload) {
		Integer owner_id = (Integer) payload.get("owner_id");
		return events.getEventByOwnerID(owner_id);
	}
	
	//Zwraca liste kategorii przypisanych do eventu
	@PostMapping(path="/getCategoriesByEventID")
	public @ResponseBody List<Category> getCategoriesByEventID(@RequestBody Map<String,Object> payload) {
		Integer event_id = (Integer) payload.get("event_id");
		return categories.getCategoriesByEventID(event_id);
	}
	
	//Zwraca liste treningow przypisanych do kategorii
	@PostMapping(path="/getWorkoutsByCatID")
	public @ResponseBody List<Workout> getWorkoutsByCatID(@RequestBody Map<String,Object> payload) {
		Integer cat_id = (Integer) payload.get("cat_id");
		return workouts.getWorkoutsByCatID(cat_id);
	}
	
	/////////////////////////////////////////////////////
	//		DODAWANIE
	/////////////////////////////////////////////////////
	
	//Zapisuje nowe zawody lub edytuje istniejace
	@PostMapping(path="/addEvent")
	public @ResponseBody Map<String,String> addNewEvent(@RequestBody Event evt) {
		Map<String, String> result = new HashMap<String, String>();
		events.save(evt);
		result.put("status", "OK");
		return result;		
	}
	
	//Zapisuje nową kategorię lub edytuje istniejącą
	@PostMapping(path="/addCategory")
	public @ResponseBody Map<String,String> addNewCategory(@RequestBody Category cat) {
		Map<String, String> result = new HashMap<String, String>();
		categories.save(cat);
		result.put("status", "OK");
		return result;		
	}
	
	//Zapisuje nowy trening lub edytuje istniejacy
		@PostMapping(path="/addWorkout")
		public @ResponseBody Map<String,String> addNewWorkout(@RequestBody Workout wkt) {
			Map<String, String> result = new HashMap<String, String>();
			workouts.save(wkt);
			result.put("status", "OK");
			return result;		
		}
	
	/////////////////////////////////////////////////////
	//		USUWANIE
	/////////////////////////////////////////////////////
	
	@PostMapping(path="/removeEvent")
	public @ResponseBody Map<String,String> removeEvent(@RequestBody Map<String,Object> payload) {
		Map<String, String> result = new HashMap<String, String>();
		Integer id = (Integer) payload.get("id");
		events.deleteById(id);
		result.put("status", "OK");
		return result;		
	}
	
	@PostMapping(path="/removeCategory")
	public @ResponseBody Map<String,String> removeCategory(@RequestBody Map<String,Object> payload) {
		Map<String, String> result = new HashMap<String, String>();
		Integer id = (Integer) payload.get("id");
		categories.deleteById(id);
		result.put("status", "OK");
		return result;		
	}
	
	@PostMapping(path="/removeWorkout")
	public @ResponseBody Map<String,String> removeWorkout(@RequestBody Map<String,Object> payload) {
		Map<String, String> result = new HashMap<String, String>();
		Integer id = (Integer) payload.get("id");
		workouts.deleteById(id);
		result.put("status", "OK");
		return result;		
	}
	
}
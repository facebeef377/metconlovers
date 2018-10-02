package com.metcon.metconlovers.controlers;

import com.metcon.metconlovers.*;
import com.metcon.metconlovers.emailer.Emailer;
import com.metcon.metconlovers.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;


@Controller    // This means that this class is a Controller
@RequestMapping(path = "/staff") // This means URL's start with /demo (after Application path)
public class StaffController {


    @Autowired
    private EventRepository events;
    @Autowired
    private CategoryRepository categories;
    @Autowired
    private WorkoutRepository workouts;
    @Autowired
    private ScoreRepository scores;
    @Autowired
    private User_categoryRepository players;
    @Autowired
    private UserRepository users;
    @Autowired
    private Emailer emailer;


/////////////////////////////////////////////////////
    //		LISTOWANIE
    /////////////////////////////////////////////////////

    //Zwraca liste wszystkich eventow w serwisie
    @PostMapping(path = "/getAllEvents")
    public @ResponseBody
    Iterable<Event> getAllEvents() {
        return events.findAll();
    }

    @PostMapping(path = "/getScoresByCatID")
    public @ResponseBody
    Iterable<Object> getScoresByCategoryID(@RequestBody Map<String, Object> payload) {
        Integer cat_id = (Integer) payload.get("cat_id");
        return users.getScoresByCatID(cat_id);
    }

    @PostMapping(path="/removePlayer")
    public @ResponseBody
    Map<String,String> removePlayer(@RequestBody User_category user_category)
    {
        Map<String, String> result = new HashMap<>();
        result.put("status", "OK");
        players.delete(user_category);
        return result;
    }

    @PostMapping(path="/saveTime")
    public @ResponseBody Map<String, String> saveTimestamp(@RequestBody Map<String, Object> payload)
    {
    	Integer id = (Integer) payload.get("id");
    	Event event = events.getEventByID(id);
       // Event temp_event=events.getEventByID(event.getId());
        event.setTimestamp(new Timestamp(new Date().getTime()));
        events.save(event);
        Map<String, String> result = new HashMap<>();
        result.put("status", "OK");
        return result;
    }
    
    @PostMapping(path="/addPlayer")
    public @ResponseBody Map<String, String> addPlayer(@RequestBody Map<String, Object> payload)
    {
    	Integer cat_id = (Integer) payload.get("cat_id");
    	String user_email = (String) payload.get("user_email");
    	MetconUser user = users.getUserByEmail(user_email);
    	Map<String, String> result = new HashMap<>();
    	if(user == null) {
    		result.put("status", "FAIL");
    	} else {
    		Integer id = user.getId();
        	User_category uc = new User_category();
        	uc.setCat_id(cat_id);
        	uc.setUser_id(id);
        	players.save(uc);
        	String name="sadasd";
            emailer.sendEmailInvite(user,name);
            result.put("status", "OK");
    	}
        return result;
    }



    //Zwraca liste wszystkich kategorii w serwisie
    @PostMapping(path = "/getAllCategories")
    public @ResponseBody
    Iterable<Category> getAllCategories() {
        return categories.findAll();
    }

    //Zwraca liste wszystkich treningow w serwisie
    @PostMapping(path = "/getAllWorkouts")
    public @ResponseBody
    Iterable<Workout> getAllWorkouts() {
        return workouts.findAll();
    }

    //Zwraca liste wszystkich wyników w serwisie
    @PostMapping(path = "/getAllScores")
    public @ResponseBody
    Iterable<Score> getAllScores() {
        return scores.findAll();
    }

    //Zwraca liste wszystkich przypisań zawodników w serwisie
    @PostMapping(path = "/getAllPlayers")
    public @ResponseBody
    Iterable<User_category> getAllPlayers() {
        return players.findAll();
    }

    //Zwraca liste eventow przypisanych do organizatora
    @PostMapping(path = "/getEventsByOwnerID")
    public @ResponseBody
    List<Event> getEventByOwnerID(@RequestBody Map<String, Object> payload) {
        Integer owner_id = (Integer) payload.get("owner_id");
        return events.getEventByOwnerID(owner_id);
    }

    //Zwraca liste kategorii przypisanych do eventu
    @PostMapping(path = "/getCategoriesByEventID")
    public @ResponseBody
    List<Category> getCategoriesByEventID(@RequestBody Map<String, Object> payload) {
        Integer event_id = (Integer) payload.get("event_id");
        return categories.getCategoriesByEventID(event_id);
    }

    //Zwraca liste treningow przypisanych do kategorii
    @PostMapping(path = "/getWorkoutsByCatID")
    public @ResponseBody
    List<Workout> getWorkoutsByCatID(@RequestBody Map<String, Object> payload) {
        Integer cat_id = (Integer) payload.get("cat_id");
        return workouts.getWorkoutsByCatID(cat_id);
    }

    //Zwraca listę wszystkich zawodników przypisanych do kategorii
    @PostMapping(path = "/getPlayersByCatID")
    public @ResponseBody
    List<MetconUser> getPlayersByCatID(@RequestBody Map<String, Object> payload) {
        Integer cat_id = (Integer) payload.get("cat_id");
        return users.getPlayersByCatID(cat_id);
    }

    /////////////////////////////////////////////////////
    //		DODAWANIE
    /////////////////////////////////////////////////////

    //Zapisuje nowe zawody lub edytuje istniejace
    @PostMapping(path = "/addEvent")
    public @ResponseBody
    Map<String, String> addNewEvent(@RequestBody Event evt) {
        Map<String, String> result = new HashMap<>();
        events.save(evt);
        result.put("status", "OK");
        return result;
    }

    //Zapisuje nową kategorię lub edytuje istniejącą
    @PostMapping(path = "/addCategory")
    public @ResponseBody
    Map<String, String> addNewCategory(@RequestBody Category cat) {
        Map<String, String> result = new HashMap<>();
        categories.save(cat);
        result.put("status", "OK");
        return result;
    }

    //Zapisuje nową kategorię lub edytuje istniejącą
    @PostMapping(path = "/signForCat")
    public @ResponseBody
    Map<String, String> signInForCat(@RequestBody  Map<String, String> json) {
        Map<String, String> result = new HashMap<>();


        result.put("status", "OK");
        return result;
    }


    @PostMapping(path = "/addScores")
    public @ResponseBody
    Map<String, String> addScores(@RequestBody List<MetconUser> users) {
        Map<String, String> result = new HashMap<>();

        List<Score> temp_scores = new ArrayList<>();
        for (MetconUser user : users) {
            temp_scores.addAll(user.getScores());
        }
        scores.saveAll(temp_scores);
        result.put("status", "OK");
        return result;
    }

    @PostMapping(path = "/test")
    public @ResponseBody
    Map<String, String> test() {
        Map<String, String> result = new HashMap<>();
        System.out.println("sadfjgsyaidgdfisadgfisad");
        result.put("status", "OK");
        return result;}

    @PostMapping(path = "/addScore")
    public @ResponseBody
    Map<String, String> addScore(@RequestBody Score score) {
        Map<String, String> result = new HashMap<>();
        Event event = events.getEventByWorkoutID(score.getWorkout_id());
        score.setScore_time( (int) (new Timestamp(new Date().getTime()).getTime()-event.getTimestamp().getTime()));
        scores.save(score);
        result.put("status", "OK");
        return result;}

    //Zapisuje nowy trening lub edytuje istniejacy
    @PostMapping(path = "/addWorkout")
    public @ResponseBody
    Map<String, String> addNewWorkout(@RequestBody Workout wkt) {
        Map<String, String> result = new HashMap<>();
        workouts.save(wkt);
        result.put("status", "OK");
        return result;
    }


    /////////////////////////////////////////////////////
    //		USUWANIE
    /////////////////////////////////////////////////////

    @PostMapping(path = "/removeEvent")
    public @ResponseBody
    Map<String, String> removeEvent(@RequestBody Map<String, Object> payload) {
        Map<String, String> result = new HashMap<>();
        Integer id = (Integer) payload.get("id");
        events.deleteById(id);
        result.put("status", "OK");
        return result;
    }

    @PostMapping(path = "/removeCategory")
    public @ResponseBody
    Map<String, String> removeCategory(@RequestBody Map<String, Object> payload) {
        Map<String, String> result = new HashMap<>();
        Integer id = (Integer) payload.get("id");
        categories.deleteById(id);
        result.put("status", "OK");
        return result;
    }

    @PostMapping(path = "/removeWorkout")
    public @ResponseBody
    Map<String, String> removeWorkout(@RequestBody Map<String, Object> payload) {
        Map<String, String> result = new HashMap<>();
        Integer id = (Integer) payload.get("id");
        workouts.deleteById(id);
        result.put("status", "OK");
        return result;
    }

}
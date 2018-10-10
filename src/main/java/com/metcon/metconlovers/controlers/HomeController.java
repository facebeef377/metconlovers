package com.metcon.metconlovers.controlers;

import com.metcon.metconlovers.*;
import com.metcon.metconlovers.entities.Category;
import com.metcon.metconlovers.entities.Event;
import com.metcon.metconlovers.entities.Message;
import com.metcon.metconlovers.entities.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/home")
public class HomeController {



    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    MessageRepository messageRepository;

    @PostMapping(path = "/message")
    public @ResponseBody
    Map<String, String> sendMessage(@RequestBody Message message) {
        Map<String, String> result = new HashMap<>();
        message.setSeen(false);
        messageRepository.save(message);
        result.put("Status", "OK");
        return result;
    }

    @PostMapping(path = "/get_recent_events")
    public @ResponseBody
    Iterable<Event> getAllEvents() {
        return eventRepository.getRecentEvents();
    }

    @PostMapping(path = "/getCategoriesByEventID")
    public @ResponseBody
    List<Category> getCategoriesByEventID(@RequestBody Map<String, Object> payload) {
        Integer event_id = (Integer) payload.get("event_id");
        return categoryRepository.getCategoriesByEventID(event_id);
    }

    //Zwraca liste treningow przypisanych do kategorii
    @PostMapping(path = "/getWorkoutsByCatID")
    public @ResponseBody
    List<Workout> getWorkoutsByCatID(@RequestBody Map<String, Object> payload) {
        Integer cat_id = (Integer) payload.get("cat_id");
        return workoutRepository.getWorkoutsByCatID(cat_id);
    }
    @PostMapping(path = "/getScoresByCatID")
    public @ResponseBody
    Iterable<Object> getScoresByCategoryID(@RequestBody Map<String, Object> payload) {
        Integer cat_id = (Integer) payload.get("cat_id");
        return userRepository.getScoresByCatID(cat_id);
    }

}

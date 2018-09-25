package com.metcon.metconlovers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Score {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	private Integer user_id;
	private Integer workout_id;
	private String score_time;
	private String score_weight;
	private String score_reps;
	
}

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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getWorkout_id() {
		return workout_id;
	}
	public void setWorkout_id(Integer workout_id) {
		this.workout_id = workout_id;
	}
	public String getScore_time() {
		return score_time;
	}
	public void setScore_time(String score_time) {
		this.score_time = score_time;
	}
	public String getScore_weight() {
		return score_weight;
	}
	public void setScore_weight(String score_weight) {
		this.score_weight = score_weight;
	}
	public String getScore_reps() {
		return score_reps;
	}
	public void setScore_reps(String score_reps) {
		this.score_reps = score_reps;
	}
	
}

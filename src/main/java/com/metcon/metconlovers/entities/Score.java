package com.metcon.metconlovers.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity@IdClass(ScoreId.class)
public class Score {

    @Id
    private Integer user_id;
    @Id
    private Integer workout_id;
    private Integer score_time;
    private String score_weight;
    private String score_reps;



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

    public Integer getScore_time() {
        return score_time;
    }

    public void setScore_time(Integer score_time) {
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

@Embeddable
class ScoreId implements Serializable {
    Integer user_id;
    Integer workout_id;
}
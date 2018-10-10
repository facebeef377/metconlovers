package com.metcon.metconlovers.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(User_teamId.class)
public class User_team {

    @Id
    private Integer user_id;
    @Id
    private Integer team_id;


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }
}

@Embeddable
class User_teamId implements Serializable {
    Integer user_id;
    Integer team_id;
}

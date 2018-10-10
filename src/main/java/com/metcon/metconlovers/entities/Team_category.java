package com.metcon.metconlovers.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(Team_categoryId.class)
public class Team_category {

    @Id
    private Integer team_id;
    @Id
    private Integer cat_id;




    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }
}

@Embeddable
class Team_categoryId implements Serializable {
    Integer cat_id;
    Integer team_id;
}

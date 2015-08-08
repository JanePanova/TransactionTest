package LoyaltyPlant.Model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by JaneJava on 5/3/15.
 */

public class Account{

    private Integer id;
    private String name;
    private boolean blocked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = "";
            return;
        }
        this.name = name;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Account() {
    }

    public Account(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

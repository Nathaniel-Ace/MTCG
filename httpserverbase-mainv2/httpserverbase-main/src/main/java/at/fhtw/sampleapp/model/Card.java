package at.fhtw.sampleapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

public class Card {

    @Getter
    @Setter
    @JsonAlias({"id"})
    private String id;

    @Getter
    @Setter
    @JsonAlias({"name"})
    private String name;

    @Getter
    @Setter
    @JsonAlias({"damage"})
    private Integer damage;


    @Getter
    @Setter
    private int type;


    public Card(String id, String name, Integer damage, MonsterType type) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.type = type.ordinal();
    }

}

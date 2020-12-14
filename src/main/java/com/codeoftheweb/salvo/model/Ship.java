package com.codeoftheweb.salvo.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name  = "native", strategy = "native")
    private long  id;
    private String type;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="GamePlayerID")
    private GamePlayer gamePlayer;



    @ElementCollection
    @Column(name= "locations")
    private List<String> locations = new ArrayList<>();

    public Ship() {
        this.locations=locations;
    }

    public Ship(String type,List <String> location, GamePlayer gamePlayer) {
        this.type = type;
        this.locations= location;
        this.gamePlayer=gamePlayer;



    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;

    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GamePlayer getPlayer() {
        return gamePlayer;
    }

    public void setPlayer(GamePlayer player) {
        this.gamePlayer = player;
    }

    public List<String> getLocation() {
        return locations;
    }

    public void setLocation(List<String> location) {
        this.locations = location;
    }


}



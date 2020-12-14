package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {
    //~ Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date creation_date;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gameplayers;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score> scores;

    //~ Constructors
    public Game() {
        this.creation_date = new Date();
        this.gameplayers = new HashSet<GamePlayer>();
    }
    public Game(Date date) {
        this.creation_date = date;
        this.gameplayers = new HashSet<GamePlayer>();
    }



    public long getId() { return id; }
    public Date getCreation_date() { return creation_date; }
    public void setCreation_date(Date creation_date) { this.creation_date = creation_date; }
    public Set<GamePlayer> getGamePlayers() { return gameplayers; }
    public void setGamePlayers(Set<GamePlayer> gameplayers) { this.gameplayers = gameplayers; }

    //~ Methods
    public void addGameplay(GamePlayer gameplayer){
        gameplayer.setGame(this);
        gameplayers.add(gameplayer);
    }
    public List<Player> getPlayers() {
        return getGamePlayers().stream()
                .map(sub -> sub.getPlayer())
                .collect(toList());
    }
}
package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date creation_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvoes;


    public GamePlayer() {

        this.ships = new LinkedHashSet<>();
        this.salvoes= new LinkedHashSet<>();
    }
    public GamePlayer(Player player, Game game) {
        this.creation_date = new Date();
        player.addGameplay(this);
        game.addGameplay(this);
        this.ships = new HashSet<>();
        this.salvoes = new HashSet<>();
    }

    public long getId() { return id; }
    public Date getCreation_date() { return creation_date; }
    public void setCreation_date(Date creation_date) { this.creation_date = creation_date; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
    public Set<Ship> getShips() { return ships; }
    public void setShips(Set<Ship> ships) { this.ships = ships; }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public void addShip(Ship ship){
        ship.setGamePlayer(this);
        ships.add(ship);
    }
    public void addSalvo(Salvo salvo){
        salvo.setGamePlayer(this);
        salvoes.add(salvo);
    }



}



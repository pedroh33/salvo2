package com.codeoftheweb.salvo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gameplayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> scores;

    public Player() {}
    public Player(String email,String name, String password) {
        this.name = name;
        this.email = email;
        this.password=password;
        gameplayers = new HashSet<>();
    }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getWonScore(){
        return this.getScores().stream()
                .filter(score -> score.getScore()== 1.0D)
                .count();
    }
    public long getLostScore(){
        return this.getScores().stream()
                .filter(score -> score.getScore()== 0.0D)
                .count();
    }
    public long getTiedScore(){
        return this.getScores().stream()
                .filter(score -> score.getScore()== 0.5D)
                .count();
    }
    public double getTotalScore(){
        return getWonScore() * 1.0D + getTiedScore() * 0.5D;
    }
    public Score getGameScore(Game game) {
    return scores.stream().filter(score -> score.getGame().getId()==game.getId()).findFirst().orElse(null);
    }

    public Long getId() {
        return id;
    }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public void addGameplay(GamePlayer gameplayer){
        gameplayer.setPlayer(this);
        gameplayers.add(gameplayer);
    }
    @JsonIgnore
    public List<Game> getGames() {
        return gameplayers.stream()
                .map(sub -> sub.getGame())
                .collect(toList());
    }

    public Set<Score>getScores(){
        return scores;
    }

    public void setScores(Set<Score>scores){
        this.scores=scores;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


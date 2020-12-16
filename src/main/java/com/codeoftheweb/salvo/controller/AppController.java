package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.*;
import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.ScoreRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppController {


    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    PasswordEncoder passwordEncoder;




    @RequestMapping("/game/{idGame}/players")
    public ResponseEntity<Map<String, Object>>joinGame(@PathVariable long idGame, Authentication authentication){
        if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error", "Is guest"), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerRepository.findByEmail(authentication.getName());

        Game gameToJoin = gameRepository.getOne(idGame);

        if (gameToJoin==null){
            return new ResponseEntity<>(Util.makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
        }

        long gamePlayersCounts =gameToJoin.getGamePlayers().size();


        if(gamePlayersCounts == 1){


            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, gameToJoin));
            return new ResponseEntity<>(Util.makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED);
        }else{

            return new ResponseEntity<>(Util.makeMap("error", "GAME IS FULL!"), HttpStatus.FORBIDDEN);
        }
    }


    @RequestMapping("/leaderBoard")
    public List<Map<String, Object>> getLeaderBoard() {

        return playerRepository.findAll()
                .stream()
                .map(player -> {
                    PlayerDto playerDto = new PlayerDto(player);
                    return playerDto.makePlayerScoreDTO();
                })
                .collect(Collectors.toList());
    }


    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));

        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getEmail());
        dto.put("nombre", player.getName());

        return dto;
    }

    @RequestMapping(value="/game_view/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>>getGameView(@PathVariable long id, Authentication authentication){
        Player player = playerRepository.findByEmail(authentication.getName());


        if(Util.isGuest(authentication)){

            return new ResponseEntity<>(Util.makeMap("error", "Not logged in"), HttpStatus.UNAUTHORIZED);
        }
        Long playerLogged = playerRepository.findByEmail(authentication.getName()).getId();
        Long playerCheck = gamePlayerRepository.getOne(id).getPlayer().getId();

        if (playerLogged != playerCheck){
            return new ResponseEntity<>(Util.makeMap("error", "This is not your game"), HttpStatus.FORBIDDEN);
        }
        //los GamePlayerDto antes eran GameView_DTO
        GamePlayer gamePlayer = gamePlayerRepository.getOne(id);
        if(Util.getGameState(gamePlayer) == "WON"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Set<Score> scores = new HashSet<>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinishDate(LocalDateTime.now());
                score1.setScore(1D);
                scoreRepository.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.getOpponent(gamePlayer).getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinishDate(LocalDateTime.now());
                score2.setScore(0D);
                scoreRepository.save(score2);
                scores.add(score1);
                scores.add(score2);

                Util.getOpponent(gamePlayer).getGame().setScores(scores);
            }
        }
        if(Util.getGameState(gamePlayer) == "TIE"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinishDate(LocalDateTime.now());
                score1.setScore(0.5D);
                scoreRepository.save(score1);
            }
        }
        return new ResponseEntity<>(GamePlayerDto.makeGameViewDTO(gamePlayer), HttpStatus.ACCEPTED);
    }
}
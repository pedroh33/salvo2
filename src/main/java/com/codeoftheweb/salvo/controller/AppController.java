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


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        GamePlayer gamePlayer=gamePlayerRepository.getOne(id);

        if(Util.isGuest(authentication)){

            return new ResponseEntity<>(Util.makeMap("error", "Not logged in"), HttpStatus.UNAUTHORIZED);
        }
    if(player.getId() != gamePlayer.getPlayer().getId()){
        return new ResponseEntity<>(Util.makeMap("error", "Raja de aca tramposo"), HttpStatus.UNAUTHORIZED);

    }
    GamePlayerDto gpDto=new GamePlayerDto(gamePlayer);
    return new ResponseEntity<>(gpDto.makeGameViewDTO(), HttpStatus.ACCEPTED);

    }






}






package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.GameDto;
import com.codeoftheweb.salvo.dto.GamePlayerDto;
import com.codeoftheweb.salvo.dto.PlayerDto;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    PlayerRepository playerRepository;


    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public Map<String, Object> getGameAll(Authentication authentication) {
        Map<String,  Object>  dto = new LinkedHashMap<>();

        if(Util.isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player  = playerRepository.findByEmail(authentication.getName());
            PlayerDto playerDTO   =   new PlayerDto(player);
            dto.put("player", playerDTO.makePlayerDTO());
        }

        dto.put("games", gameRepository.findAll()
                .stream()
                .map(game -> {
                    GameDto gameDTO =   new GameDto(game);
                    return  gameDTO.makeGameDTO();
                })
                .collect(Collectors.toList()));

        return dto;

    }





    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {


        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>("Access denied", HttpStatus.UNAUTHORIZED);

        }
        Player player = playerRepository.findByEmail(authentication.getName());

        if (player == null) {
            return new ResponseEntity<>("Access denied", HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.save(new Game());

        GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game));

        return new ResponseEntity<>(Util.makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED );
    }


}
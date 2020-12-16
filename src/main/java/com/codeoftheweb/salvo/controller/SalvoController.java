package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.SalvoRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SalvoRepository salvoRepository;


    @RequestMapping(path = "/games/players/{gamePlayerID}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setSalvoes(@PathVariable Long gamePlayerID, @RequestBody Salvo salvo, Authentication authentication) {
        Player p1=playerRepository.findByEmail(authentication.getName());
        if(p1!=null){
            GamePlayer gp= gamePlayerRepository.getOne(gamePlayerID);
            if(p1.getId()==gp.getPlayer().getId()){
                GamePlayer opponent = Util.getOpponent(gp);
                if(opponent.getSalvoes().size() >= gp.getSalvoes().size()){
                    salvo.setTurn(gp.getSalvoes().size()+1);
                    salvo.setGamePlayer(gp);
                    salvoRepository.save(salvo);
                    return new ResponseEntity<>(Util.makeMap("OK", "You did it, you fired"), HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>(Util.makeMap("error", "this not your turn"), HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>(Util.makeMap("error", "This no your section"), HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(Util.makeMap("error", "This no your section"), HttpStatus.UNAUTHORIZED);
        }
    }
}


















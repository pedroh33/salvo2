package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.util.Util;

import java.util.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GamePlayerDto {

    private GamePlayer gamePlayer;

    public GamePlayerDto(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    public GamePlayerDto() {
    }

    public Map<String, Object> makeGamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        PlayerDto playerDTO = new PlayerDto(gamePlayer.getPlayer());
        dto.put("id", this.gamePlayer.getId());
        dto.put("player", playerDTO.makePlayerDTO());

        return dto;
    }
/*
    public Map<String,  Object> makeGameViewDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> hits = new LinkedHashMap<>();
        HitsDto hitsDto=new HitsDto();
        Set<GamePlayer> gamePlayers = gamePlayer.getGame().getGamePlayers();
        List<Salvo> salvoes = gamePlayers
                .stream()
                .flatMap(gp -> gp.getSalvoes().stream())
                .collect(Collectors.toList());

        hits.put("self", hitsDto.makeHitsDTO(gamePlayer));
        hits.put("opponent", hitsDto.makeHitsDTO(Util.getOpponent(gamePlayer)));
        dto.put("id", this.gamePlayer.getGame().getId());
        dto.put("created", this.gamePlayer.getGame().getCreation_date());
        dto.put("gamePlayers", this.gamePlayer.getGame().getGamePlayers()
                .stream()
                .map(gamePlayer -> {
                    GamePlayerDto gamePlayerDTO = new GamePlayerDto(gamePlayer);
                    return gamePlayerDTO.makeGamePlayerDTO();
                })
                .collect(Collectors.toList()));
        dto.put("ships",  this.gamePlayer.getShips().stream().map(ship  -> {
            ShipDto shipDTO = new ShipDto(ship);
            return  shipDTO.makeShipDTO();

        }));
        dto.put("salvoes", salvoes
                .stream()
                .map(salvo -> {
                    SalvoDTO salvoDTO = new SalvoDTO(salvo);
                    return salvoDTO.makeSalvoDTO();
                }).collect(Collectors.toList()));


        dto.put("hits", hits);
        dto.put("gameState", "PLAY");
        return  dto;

    }
*/
    public static Map<String, Object>makeGameViewDTO(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> hits = new LinkedHashMap<>();
        HitsDto hitsDto=new HitsDto();

        dto.put("created", gamePlayer.getGame().getCreation_date());
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                        .stream()
                        .map(gamePlayer1 -> {
                            GamePlayerDto gamePlayerDTO = new GamePlayerDto(gamePlayer1);
                            return gamePlayerDTO.makeGamePlayerDTO();
                                    }));
        dto.put("ships", gamePlayer.getShips().stream()
                .map(s -> ShipDto.makeShipDTO(s))
                .collect(Collectors.toList()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                .stream()
                .flatMap(gp -> gp.getSalvoes().stream())
                .map(salvo ->{
                    return SalvoDTO.makeSalvoDTO(salvo);
                })
            .collect(Collectors.toList()));

    if(gamePlayer.getGame().getGamePlayers().size()==2){
        hits.put("self", hitsDto.makeHitsDTO(gamePlayer));
        hits.put("opponent",hitsDto.makeHitsDTO(Util.getOpponent(gamePlayer)));
    }else{
        hits.put("self",new ArrayList<>());
        hits.put("opponent",new ArrayList<>());
    }
    dto.put("hits", hits);
    dto.put("gameState", Util.getGameState(gamePlayer));
    return dto;

}

















    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}




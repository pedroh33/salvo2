package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.dto.HitsDto;
import com.codeoftheweb.salvo.model.GamePlayer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Util {
    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }


    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


    public static GamePlayer getOpponent(GamePlayer myself) {
        GamePlayer opponent = new GamePlayer();
        for (GamePlayer g : myself.getGame().getGamePlayers()) {
            if (g.getId() != myself.getId()) {
                opponent = g;
            }
        }
        return opponent;
    }

    public static List<String> getLocationsByType(String type, GamePlayer self) {
        return self.getShips().size() == 0 ? new ArrayList<>() : self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getLocations();
    }

    public static Map<String, Integer> shipTypes = Stream.of(
            new Object[][]{
                    {"carrier", 5},
                    {"battleship", 4},
                    {"submarine", 3},
                    {"destroyer", 3},
                    {"patrolboat", 2}
            }).collect(toMap(data -> (String) data[0], data -> (Integer) data[1]));

    public static boolean outOfBoundsLocation(String location) {
        char row = location.charAt(0);
        int col = Integer.parseInt(location.substring(1));
        if (row < 'A' || 'J' < row) return true;
        if (col < 1 || 10 < col) return true;
        return false;
    }

    public static String getGameState(GamePlayer gamePlayer) {
        if (gamePlayer.getShips().size() == 0) {
            return "PLACESHIPS";
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 1){
            return "WAITINGFOROPP";
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 2) {

            GamePlayer opponentGp = Util.getOpponent(gamePlayer);

            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (getIfAllSunk(opponentGp, gamePlayer)) && (!getIfAllSunk(gamePlayer, opponentGp))) {
                return "WON";
            }
            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (getIfAllSunk(opponentGp, gamePlayer)) && (getIfAllSunk(gamePlayer, opponentGp))) {
                return "TIE";
            }
            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (!getIfAllSunk(opponentGp, gamePlayer)) && (getIfAllSunk(gamePlayer, opponentGp))) {
                return "LOST";
            }

            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (gamePlayer.getId() < opponentGp.getId())) {
                return "PLAY";
            }
            if (gamePlayer.getSalvoes().size() < opponentGp.getSalvoes().size()){
                return "PLAY";
            }
            if ((gamePlayer.getSalvoes().size() == opponentGp.getSalvoes().size()) && (gamePlayer.getId() > opponentGp.getId())) {
                return "WAIT";
            }
            if (gamePlayer.getSalvoes().size() > opponentGp.getSalvoes().size()){
                return "WAIT";
            }

        }
        return "UNDEFINED";

    }

    public static Boolean getIfAllSunk (GamePlayer self, GamePlayer opponent) {

        if(!opponent.getShips().isEmpty() && !self.getSalvoes().isEmpty()){
            return opponent.getSalvoes().stream().flatMap(salvo -> salvo.getLocations()
                    .stream())
                    .collect(Collectors.toList())
                    .containsAll(self.getShips()
                            .stream()
                    .flatMap(ship -> ship.getLocations().stream()).collect(Collectors.toList()));
        }
        return false;
    }

}








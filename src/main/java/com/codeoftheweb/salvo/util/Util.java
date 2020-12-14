package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.model.GamePlayer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static Map<String, Object> makeMap (String key,Object value){
        Map<String , Object>map= new HashMap<>();
        map.put(key, value);
        return map;
    }


    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }




    public static GamePlayer getOpponent(GamePlayer myself){
        GamePlayer opponent= new GamePlayer();
        for( GamePlayer g :myself.getGame().getGamePlayers()){
            if(g.getId()!=myself.getId()){
                opponent=g;
            }
        }
        return opponent;
    }

    public static List<String> getLocationsByType(String type, GamePlayer self){
        return  self.getShips().size()  ==  0 ? new ArrayList<>() : self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getLocations();
    }

}

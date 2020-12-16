package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Ship;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShipDto {

    private Ship ship;
    public ShipDto(Ship ship){
        this.ship=ship;
    }

    public ShipDto() {
    }

    public static Map<String, Object>makeShipDTO(Ship ship){
        Map<String, Object>dto = new HashMap<>();
        dto.put("id",ship.getId());
        dto.put("type",ship.getType());
        dto.put("locations",ship.getLocation());

        return dto;

    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}

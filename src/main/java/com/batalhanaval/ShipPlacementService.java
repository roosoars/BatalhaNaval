package com.batalhanaval;

import java.util.List;

import com.batalhanaval.model.Ship;
import org.springframework.stereotype.Service;

@Service
public class ShipPlacementService {

    public boolean validateShipPlacement(List<Ship> ships) {
        // Lógica de validação (garante que os navios não se sobreponham)
        for (int i = 0; i < ships.size(); i++) {
            for (int j = i + 1; j < ships.size(); j++) {
                if (isOverlap(ships.get(i), ships.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOverlap(Ship ship1, Ship ship2) {
        // Verifica se os navios se sobrepõem
        int[][] ship1Coords = getShipCoordinates(ship1);
        int[][] ship2Coords = getShipCoordinates(ship2);

        for (int[] coord1 : ship1Coords) {
            for (int[] coord2 : ship2Coords) {
                if (coord1[0] == coord2[0] && coord1[1] == coord2[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[][] getShipCoordinates(Ship ship) {
        int[][] coordinates = new int[ship.getLength()][2];

        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.isHorizontal()) {
                coordinates[i][0] = ship.getX() + i;
                coordinates[i][1] = ship.getY();
            } else {
                coordinates[i][0] = ship.getX();
                coordinates[i][1] = ship.getY() + i;
            }
        }

        return coordinates;
    }
}

package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 地图类
 */
public class Map {
    private List<Coord>myCoords = new ArrayList<>();
    private List<Coord>opponentCoords = new ArrayList<>();

    public void addMyCharacterCoords(List<Coord>coords){
        myCoords = coords;
    }

    public void addOpponentCharacterCoords(List<Coord>coords){
        opponentCoords = coords;
    }


}

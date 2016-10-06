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

    private List<Coord>foodCoords = new ArrayList<>();

    private MapListener mapListener;

    public void addMyCharacterCoords(List<Coord>coords){
        myCoords = coords;
        checkStatus();
    }

    public void addOpponentCharacterCoords(List<Coord>coords){
        opponentCoords = coords;
        checkStatus();
    }

    public Map(MapListener mapListener){
        this.mapListener = mapListener;
    }

    public void addFood(int x,int y){

    }

    private void checkStatus(){
        //检查蛇的状态
        try {
            Coord myCoord = myCoords.get(0);
            Coord opponentCoord = opponentCoords.get(0);
            if (myCoord.x<0 || myCoord.x > 19){
                mapListener.onMyDied();
                return;
            }
            if (myCoord.y < 0 || myCoord.y > 14){
                mapListener.onMyDied();
                return;
            }

            if (opponentCoord.x<0 || opponentCoord.x > 19){
                mapListener.onOpponentDied();
                return;
            }
            if (opponentCoord.y < 0 || opponentCoord.y > 14){
                mapListener.onOpponentDied();
                return;
            }

            if (myCoord.x == opponentCoord.x && myCoord.y == opponentCoord.y){
                mapListener.onBothDied();
                return;
            }
            try {
                for (int i = 1 ; i < myCoords.size(); i++){
                    if (myCoord.x == myCoords.get(i).x && myCoord.y == myCoords.get(i).y){
                        mapListener.onMyDied();
                        return;
                    }
                }
            }catch (Exception e){

            }
            try {
                for (int i = 1 ; i < opponentCoords.size(); i++){
                    if (myCoord.x == opponentCoords.get(i).x && myCoord.y == opponentCoords.get(i).y){
                        mapListener.onMyDied();
                        return;
                    }
                }
            }catch (Exception e){

            }


            try {
                for (int i = 1 ; i < myCoords.size(); i++){
                    if (opponentCoord.x == myCoords.get(i).x && opponentCoord.y == myCoords.get(i).y){
                        mapListener.onOpponentDied();
                        return;
                    }
                }
            }catch (Exception e){

            }
            try {
                for (int i = 1 ; i < opponentCoords.size(); i++){
                    if (opponentCoord.x == opponentCoords.get(i).x && opponentCoord.y == opponentCoords.get(i).y){
                        mapListener.onOpponentDied();
                        return;
                    }
                }
            }catch (Exception e){

            }

            try {
                for (int i = 0 ; i < foodCoords.size() ; i ++){
                    if (myCoord.x == foodCoords.get(i).x && myCoord.y == foodCoords.get(i).y){
                        mapListener.onMyAteFood();
                        foodCoords.remove(i);
                        return;
                    }
                    if (opponentCoord.x == foodCoords.get(i).x && opponentCoord.y == foodCoords.get(i).y){
                        mapListener.onOpponentAteFood();
                        foodCoords.remove(i);
                        return;
                    }
                }
            }catch (Exception e){

            }
        }catch (Exception e){

        }

    }


    public Coord getFreeCoord(){
        Coord coord = getRandomCoord();
        while (checkFreeCoord(coord)){
            coord = getRandomCoord();
        }
        return coord;
    }

    private Coord getRandomCoord(){
        int x = (int)(Math.random() * 20);
        int y = (int)(Math.random() * 15);
        return new Coord(x,y);
    }

    private boolean checkFreeCoord(Coord coord){
        List<Coord>list = new ArrayList<>();
        list.addAll(myCoords);
        list.addAll(opponentCoords);
        for (Coord element : list){
            if (coord.x == element.x && coord.y == element.y){
                return true;
            }
        }
        return false;
    }

}

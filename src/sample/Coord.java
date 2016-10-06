package sample;

/**
 * Created by HUPENG on 2016/10/5.
 */
public class Coord {
    public Coord(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Coord(int x,int y,int mode){
        this.x = x;
        this.y = y;
        this.mode = mode;
    }
    public int x;
    public int y;
    public int mode;
}

package sample;

/**
 * Created by HUPENG on 2016/10/6.
 * 地图监听器
 */
public interface MapListener {
    public void onMyDied();
    public void onOpponentDied();
    public void onBothDied();
    public void onMyAteFood(Coord coord);
    public void onOpponentAteFood(Coord coord);
}

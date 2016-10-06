package sample;

import javafx.scene.input.KeyCode;

import java.util.List;

/**
 * Created by HUPENG on 2016/10/4.
 */
public interface CharacterListener {
    /**
     * 发出移动请求
     * */
    public void onMoveRequest(KeyCode keyCode);

    /**
     * 精灵已经移动
     * */
    public void onMoved(List<Coord>list);

    /**
     * 发出放置炸弹请求
     * */
    public void onBombRequest(int x, int y, boolean isMyCharacterSprite);
}

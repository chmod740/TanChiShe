package sample;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

/**
 * 游戏盘,精灵控制器
 * @author HUPENG
 */
public class GameBoard {
    /**
     * 游戏过程监听器
     * */
    private GameListener gameListener;

    /**
     * 判断是否是服务器
     * */
    private boolean isServer;

    /**
     * 自己的精灵
     * */
    private CharacterSprite myCharacterSprite;


    /**
     * 对手的精灵
     * */
    private CharacterSprite opponentCharacterSprite;



    /**
     * 地图管理
     * */
    private MapManager mapManager;

    public GameBoard(GameListener gameListener, boolean isServer){
        this.gameListener = gameListener;
        this.isServer = isServer;
        this.mapManager = new MapManager();
        if (isServer){
            myCharacterSprite = new CharacterSprite(new MyCharacterListener(),2,2,40,40);
            opponentCharacterSprite = new CharacterSprite(null,10,2,40,40);
        }else{
            opponentCharacterSprite = new CharacterSprite(null,2,2,40,40);
            myCharacterSprite = new CharacterSprite(new MyCharacterListener(),10,2,40,40);
        }
        gameListener.onMyCharacterSpriteCreated(myCharacterSprite);
        gameListener.onOpponentCharacterSpriteCreated(opponentCharacterSprite);
    }

    public void addLocalKeyCode(KeyCode keyCode){
        myCharacterSprite.addKeyCode(keyCode);
    }

    /**
     * 移动对手的精灵
     * */
    public void moveOpponentCharacterSprite(KeyCode keyCode){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //更新JavaFX的主线程的代码放在此处
                if(keyCode == KeyCode.LEFT){
                    opponentCharacterSprite.moveLeft();
                }else if(keyCode == KeyCode.RIGHT){
                    opponentCharacterSprite.moveRight();
                }else if(keyCode == KeyCode.UP){
                    opponentCharacterSprite.moveUp();
                }else if(keyCode == KeyCode.DOWN){
                    opponentCharacterSprite.moveDown();
                }
            }
        });
    }

    public void removeLocalKeyCode(KeyCode keyCode){
        myCharacterSprite.removeKeyCode(keyCode);
    }


    /**
     * 我的人物精灵的走动的回调的监听器
     * */
    class MyCharacterListener implements CharacterListener{

        @Override
        public void onMoveRequest(int x, int y) {

        }

        @Override
        public void onMoved(KeyCode keyCode) {
            gameListener.onMyCharacterSpriteMoved(keyCode);
        }

        @Override
        public void onBombRequest(int x, int y, boolean isMyCharacterSprite) {

        }
    }
}

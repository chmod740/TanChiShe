package sample;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.List;

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
     * 地图
     * */
    private Map map;

    /**
     * 食物精灵
     * */
    private FoodSprite foodSprite;

    private boolean isMyCharacterSpriteStop = false;
    private boolean isOpponentCharacterSpriteStop = false;

    public GameBoard(GameListener gameListener, boolean isServer){
        this.gameListener = gameListener;
        this.isServer = isServer;
        this.map = new Map(new MyMapListener());
        if (isServer){
            myCharacterSprite = new CharacterSprite(new MyCharacterListener(),2,2,40,40,true);
            opponentCharacterSprite = new CharacterSprite(new OpponentCharacterListener(),10,2,40,40,false);

        }else{
            opponentCharacterSprite = new CharacterSprite(new OpponentCharacterListener(),2,2,40,40,false);
            myCharacterSprite = new CharacterSprite(new MyCharacterListener(),10,2,40,40,true);
        }
        foodSprite = new FoodSprite(40,40);

        if (isServer){
            provideFood(null);
        }

        gameListener.onMyCharacterSpriteCreated(myCharacterSprite);
        gameListener.onOpponentCharacterSpriteCreated(opponentCharacterSprite);
        gameListener.onFoodSpriteCreated(foodSprite);
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
        public void onMoveRequest(KeyCode keyCode) {
            gameListener.onMyCharacterSpriteMoved(keyCode);
        }
        @Override
        public void onMoved(List<Coord> list) {
            map.addMyCharacterCoords(list);
        }
        @Override
        public void onBombRequest(int x, int y, boolean isMyCharacterSprite) {

        }
    }


    /**
     * 对手的精灵的回调
     * */
    class OpponentCharacterListener implements CharacterListener{
        @Override
        public void onMoveRequest(KeyCode keyCode) {

        }

        @Override
        public void onMoved(List<Coord> list) {
            map.addOpponentCharacterCoords(list);
        }

        @Override
        public void onBombRequest(int x, int y, boolean isMyCharacterSprite) {

        }
    }

    /**
     * 地图回调
     * */
    class MyMapListener implements MapListener{

        @Override
        public void onMyDied() {
            if (!isMyCharacterSpriteStop){
                System.out.println("我方阵亡");
                myCharacterSprite.stop();

                isMyCharacterSpriteStop = true;
                if (isOpponentCharacterSpriteStop){
                    gameOver();
                }
            }
        }

        @Override
        public void onOpponentDied() {
            if (!isOpponentCharacterSpriteStop){
                System.out.println("对方阵亡");
                opponentCharacterSprite.stop();

                isOpponentCharacterSpriteStop = true;
                if (isMyCharacterSpriteStop){
                    gameOver();
                }
            }
        }

        @Override
        public void onBothDied() {
            if (!isMyCharacterSpriteStop || !isMyCharacterSpriteStop){
                opponentCharacterSprite.stop();
                myCharacterSprite.stop();
                System.out.println("双方阵亡");

                isMyCharacterSpriteStop = true;
                isOpponentCharacterSpriteStop = true;
                gameOver();
            }
        }

        @Override
        public void onMyAteFood(Coord coord) {
            System.out.println("我方吃");

            myCharacterSprite.addScore(1);
            myCharacterSprite.addSpeed();

            myCharacterSprite.addLength(coord);

            foodSprite.removeFood(coord);

            if (isServer){
                provideFood(null);
            }

        }

        @Override
        public void onOpponentAteFood(Coord coord) {
            System.out.println("对方吃");

            opponentCharacterSprite.addScore(1);
//            opponentCharacterSprite.addSpeed();

            opponentCharacterSprite.addLength(coord);

            foodSprite.removeFood(coord);

            if (isServer){
                provideFood(null);
            }

        }
    }

    /**
     * 提供食物
     * */
    public void provideFood(Coord coord){
        if (coord == null){
            coord = map.getFreeCoord();
        }
        map.addFood(coord.x,coord.y);
        foodSprite.addFood(coord);
        if (isServer){
            gameListener.onFoodAdd(coord);
        }
    }

    /**
     * 游戏结束
     * */
    private void gameOver(){
        System.out.println("游戏结束");
    }
}

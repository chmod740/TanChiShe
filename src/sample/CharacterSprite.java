package sample;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 人物类
 */
public class CharacterSprite extends Parent {
    private int score = 0;

    long intevalPeriod = 500;

    private enum Direction {
        Left, Right, Up, Down
    }
    private Direction direction = Direction.Left;
    private Direction lastDirection ;
    private int x, y, width, height;
    private int index = 0;
    private int indexDiv = 5;
    private ImageView mImageView;
    private int speed = 40;

    private int length = 1;

    //定时器
    private TimerTask timerTask = null;
    private Timer timer;

    private List<KeyCode> directions = new ArrayList<>();

    private CharacterListener characterListener;

    private List<Coord>data = new ArrayList<>();
    private List<ImageView>imageViews = new ArrayList<>();


    public CharacterSprite(CharacterListener characterListener, int x, int y, int width, int height,boolean isMine) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.characterListener = characterListener;

        intevalPeriod = 500;

        data.add(new Coord(x, y));
        data.add(new Coord(x, y-1));
        data.add(new Coord(x, y-2));
        direction = Direction.Down;
        move();

        if (isMine){
            //增加Timer
            addTaskTimer();
            timer = new Timer();
            //首次执行的时候的延时
            long delay = 0;
            //每次执行的时候的时延
            long intevalPeriod = 500;
            // schedules the task to be run in an interval
            timer.scheduleAtFixedRate(timerTask, delay,
                    intevalPeriod);
        }

    }

    /**
     * 像下移动
     */
    public void moveDown() {
        direction = Direction.Down;

        move();

        lastDirection = direction;
    }

    /**
     * 像左移动
     */
    public void moveLeft() {
        direction = Direction.Left;
        move();
        lastDirection = direction;

    }

    /**
     * 像右移动
     */
    public void moveRight() {
        direction = Direction.Right;
        move();
        lastDirection = direction;
    }

    /**
     * 像上移动
     */
    public void moveUp() {
        direction = Direction.Up;
        move();
        lastDirection = direction;
    }


    public void move(){
        /**
         * 清空原有的imageView
         * */
        for (ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }
        imageViews.clear();

        Coord coord = data.get(0);
        Coord tCoord = null;
        Image actor = null;
        if (direction == Direction.Up){
            tCoord = new Coord(coord.x,coord.y-1);
            actor = new Image(getClass().getResourceAsStream("img/up.png"));
        }
        if (direction == Direction.Down){
            tCoord = new Coord(coord.x,coord.y+1);
            actor = new Image(getClass().getResourceAsStream("img/down.png"));
        }
        if (direction == Direction.Left){
            tCoord = new Coord(coord.x-1,coord.y);
            actor = new Image(getClass().getResourceAsStream("img/left.png"));
        }
        if (direction == Direction.Right){
            tCoord = new Coord(coord.x+1,coord.y);
            actor = new Image(getClass().getResourceAsStream("img/right.png"));
        }

        data.remove(data.size()-1);
        data.add(0,tCoord);

        for (int i = 1 ; i < data.size() ; i++){
            Image t_actor = new Image(getClass().getResourceAsStream("img/body.png"));
            ImageView t_imageView = new ImageView(t_actor);
            t_imageView.setLayoutX(data.get(i).x * width);
            t_imageView.setLayoutY(data.get(i).y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }

        ImageView imageView = new ImageView(actor);
        imageView.setLayoutX(data.get(0).x * width);
        imageView.setLayoutY(data.get(0).y * height);
        getChildren().add(imageView);
        imageViews.add(imageView);
        characterListener.onMoved(data);
    }

    /**
     * 人物停止
     * */
    public void stop(){
        try {
            timer.cancel();
        }catch (Exception e){

        }
    }

    public void addKeyCode(KeyCode keyCode){
        if (keyCode == KeyCode.LEFT && lastDirection!= Direction.Right){
            directions.clear();
            directions.add(keyCode);
        }
        if (keyCode == KeyCode.RIGHT && lastDirection!= Direction.Left){
            directions.clear();
            directions.add(keyCode);
        }
        if (keyCode == KeyCode.UP && lastDirection!= Direction.Down){
            directions.clear();
            directions.add(keyCode);
        }
        if (keyCode == KeyCode.DOWN && lastDirection!= Direction.Up){
            directions.clear();
            directions.add(keyCode);
        }

    }

    public void removeKeyCode(KeyCode keyCode){

    }

    private void addTaskTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //更新JavaFX的主线程的代码放在此处
                        int size = directions.size();
                        if (size>0){
                            KeyCode keyCode = directions.get(size-1);
                            characterListener.onMoveRequest(keyCode);
                            if(keyCode == KeyCode.LEFT){
                                moveLeft();
                            }else if(keyCode == KeyCode.RIGHT){
                                moveRight();
                            }else if(keyCode == KeyCode.UP){
                                moveUp();
                            }else if(keyCode == KeyCode.DOWN){
                                moveDown();
                            }
                        }
                    }
                });
            }
        };
    }

    public void addSpeed(){
        timer.cancel();
        timer = new Timer();
        addTaskTimer();
        //首次执行的时候的延时
        long delay = 0;
        //每次执行的时候的时延
        if (intevalPeriod > 200){
            intevalPeriod = intevalPeriod - 20;
        }
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(timerTask, delay,
                intevalPeriod);
    }

    public void subSpeed(){
        timer.cancel();
        timer = new Timer();
        //首次执行的时候的延时
        long delay = 0;
        //每次执行的时候的时延
        intevalPeriod = intevalPeriod + 30;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(timerTask, delay,
                intevalPeriod);
    }

    public void addScore(int i){
        score = score + i;
    }

    public int getScore(){
        return score;
    }

    public void addLength(Coord coord){
        data.add(coord);
    }
}

package sample;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 人物类
 */
public class CharacterSprite extends Parent {
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


    public CharacterSprite(CharacterListener characterListener, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.characterListener = characterListener;
        Image actor = new Image(getClass().getResourceAsStream("img/down.png"));
        mImageView = new ImageView(actor);
        mImageView.setViewport(new Rectangle2D(0, 0, width, height));
        mImageView.setLayoutX(x*width);
        mImageView.setLayoutY(y*height);

        imageViews.add(mImageView);
        Coord coord = new Coord(x,y);
        data.add(coord);

        getChildren().add(mImageView);

        if (characterListener!=null){
            //增加Timer
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
                                if(keyCode == KeyCode.LEFT){
                                    moveLeft();
                                }else if(keyCode == KeyCode.RIGHT){
                                    moveRight();
                                }else if(keyCode == KeyCode.UP){
                                    moveUp();
                                }else if(keyCode == KeyCode.DOWN){
                                    moveDown();
                                }
                                characterListener.onMoved(keyCode);
                            }else {
                                stop();
                            }
                        }
                    });
                }
            };
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
        /**
         * 清空原有的imageView
         * */
        for (ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }
        imageViews.clear();

        Coord coord = data.get(0);
        coord.y = coord.y + 1;
        data.remove(data.size()-1);
        data.add(0,coord);

        Image actor = new Image(getClass().getResourceAsStream("img/down.png"));
        ImageView imageView = new ImageView(actor);
        imageView.setLayoutX(coord.x * width);
        imageView.setLayoutY(coord.y * height);
        getChildren().add(imageView);
        imageViews.add(imageView);

        for (int i = 1 ; i < data.size() ; i++){
            Image t_actor = new Image(getClass().getResourceAsStream("img/body.png"));
            ImageView t_imageView = new ImageView(actor);
            imageView.setLayoutX(coord.x * width);
            imageView.setLayoutY(coord.y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }

        lastDirection = direction;
    }

    /**
     * 像左移动
     */
    public void moveLeft() {
        direction = Direction.Left;

        /**
         * 清空原有的imageView
         * */
        for (ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }
        imageViews.clear();

        Coord coord = data.get(0);
        coord.x = coord.x - 1;
        data.remove(data.size()-1);
        data.add(0,coord);

        Image actor = new Image(getClass().getResourceAsStream("img/left.png"));
        ImageView imageView = new ImageView(actor);
        imageView.setLayoutX(coord.x * width);
        imageView.setLayoutY(coord.y * height);
        getChildren().add(imageView);
        imageViews.add(imageView);

        for (int i = 1 ; i < data.size() ; i++){
            Image t_actor = new Image(getClass().getResourceAsStream("img/body.png"));
            ImageView t_imageView = new ImageView(actor);
            imageView.setLayoutX(coord.x * width);
            imageView.setLayoutY(coord.y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }

        lastDirection = direction;

    }

    /**
     * 像右移动
     */
    public void moveRight() {
        direction = Direction.Right;


        /**
         * 清空原有的imageView
         * */
        for (ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }
        imageViews.clear();

        Coord coord = data.get(0);
        coord.x = coord.x + 1;
        data.remove(data.size()-1);
        data.add(0,coord);

        Image actor = new Image(getClass().getResourceAsStream("img/right.png"));
        ImageView imageView = new ImageView(actor);
        imageView.setLayoutX(coord.x * width);
        imageView.setLayoutY(coord.y * height);
        getChildren().add(imageView);
        imageViews.add(imageView);

        for (int i = 1 ; i < data.size() ; i++){
            Image t_actor = new Image(getClass().getResourceAsStream("img/body.png"));
            ImageView t_imageView = new ImageView(actor);
            imageView.setLayoutX(coord.x * width);
            imageView.setLayoutY(coord.y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }

        lastDirection = direction;
    }

    /**
     * 像上移动
     */
    public void moveUp() {
        direction = Direction.Up;

        /**
         * 清空原有的imageView
         * */
        for (ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }
        imageViews.clear();

        Coord coord = data.get(0);
        coord.y = coord.y - 1;
        data.remove(data.size()-1);
        data.add(0,coord);

        Image actor = new Image(getClass().getResourceAsStream("img/up.png"));
        ImageView imageView = new ImageView(actor);
        imageView.setLayoutX(coord.x * width);
        imageView.setLayoutY(coord.y * height);
        getChildren().add(imageView);
        imageViews.add(imageView);

        for (int i = 1 ; i < data.size() ; i++){
            Image t_actor = new Image(getClass().getResourceAsStream("img/body.png"));
            ImageView t_imageView = new ImageView(actor);
            imageView.setLayoutX(coord.x * width);
            imageView.setLayoutY(coord.y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }

        lastDirection = direction;
    }

    /**
     * 人物停止
     * */
    public void stop(){

    }

    public void addKeyCode(KeyCode keyCode){
        directions.clear();
        directions.add(keyCode);
    }

    public void removeKeyCode(KeyCode keyCode){

    }
}

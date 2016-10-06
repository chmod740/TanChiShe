package sample;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUPENG on 2016/10/6.
 */
public class FoodSprite extends Parent {
    private List<ImageView>imageViews = new ArrayList<>();
    private List<Coord>coords = new ArrayList<>();

    private int width;
    private int height;

    public FoodSprite(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void addFood(Coord coord){
        //remove
        for(ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }

        coords.add(coord);

        for (Coord element : coords){
            Image t_actor = new Image(getClass().getResourceAsStream("img/snake_food.png"));
            ImageView t_imageView = new ImageView(t_actor);
            t_imageView.setLayoutX(element.x * width);
            t_imageView.setLayoutY(element.y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }
    }

    public void removeFood(Coord coord){
        for(ImageView imageView : imageViews){
            getChildren().remove(imageView);
        }

        for (int i = 0 ; i < coords.size() ; i ++ ){
            if (coords.get(i).x == coord.x && coords.get(i).y == coord.y){
                coords.remove(i);
            }
        }

        for (Coord element : coords){
            Image t_actor = new Image(getClass().getResourceAsStream("img/snake_food.png"));
            ImageView t_imageView = new ImageView(t_actor);
            t_imageView.setLayoutX(element.x * width);
            t_imageView.setLayoutY(element.y * height);
            getChildren().add(t_imageView);
            imageViews.add(t_imageView);
        }
    }

}

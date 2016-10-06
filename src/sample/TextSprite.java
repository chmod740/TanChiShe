package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by HUPENG on 2016/10/6.
 */
public class TextSprite extends Parent{
    private Text text = new Text();

    public void add(String s){
        try {
            getChildren().remove(text);
        }catch (Exception e){

        }
        text.setText(s);
        text.setLayoutX(200);
        text.setLayoutY(300);
        text.setFont(Font.font ("Verdana", 30));
        text.setFill(Color.RED);
        getChildren().add(text);
    }

    public void remove(){
        try {
            getChildren().remove(text);
        }catch (Exception e){

        }
    }
}

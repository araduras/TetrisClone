package Util;

import View.Style;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.jetbrains.annotations.NotNull;

public class Util {
    static final int defaultSliderWidth = 500;
    static final int defaultSliderHeight = 5;


    public static Label placeHolderLabel(){
        Label label = new Label("placeHolderText");
        label.setStyle(Style.LARGE_TEXT_STYLE);
        return label;

    }

   public static Button defaultBackBtn(){
       Button button = new Button("Back");
       button.setStyle("");
       return button;
       }

       public static Slider defaultSlider() {
           Slider slider = new Slider();
           slider.setMaxSize(defaultSliderWidth, defaultSliderHeight);
           return slider;
       }
   }



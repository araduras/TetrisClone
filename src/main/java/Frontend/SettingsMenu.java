package Frontend;


import Backend.Style;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;


import static Frontend.PauseMenu.pauseMenu;
import static Frontend.Tetris.gameMusic;

public class SettingsMenu {
    Slider musicVolumeSlider;
    Slider soundEffectVolumeSlider;
    Button backBtn;
    static VBox settingsMenu;
    final int sliderWidth = 500;
    final int sliderHeight = 5;

     SettingsMenu(){
         settingsMenuSetup();
         musicVolumeSliderSetup();
         soundEffectsVolumeSliderSetup();
         settingsMenu.getChildren().addAll(backBtn, musicVolumeSlider, soundEffectVolumeSlider);

    }

    private void settingsMenuSetup(){
        settingsMenu = new VBox();

        settingsMenu.setMinHeight(200);
        settingsMenu.setMinWidth(400);
        settingsMenu.setStyle(Style.settingsMenuStyle);
        settingsMenu.setFocusTraversable(true);
        settingsMenu.setVisible(false);
        settingsMenu.managedProperty().bind(settingsMenu.visibleProperty());

        settingsMenu.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ESCAPE ){
                pauseMenu.setVisible(true);
                settingsMenu.setVisible(false);
                pauseMenu.requestFocus();
            }
        });

        backBtn = new Button("Back");
        backBtn.setOnAction(event -> {
            pauseMenu.setVisible(true);
            settingsMenu.setVisible(false);
            pauseMenu.requestFocus();
        });




    }
   private void musicVolumeSliderSetup(){
       Label musicVolumeSliderLabel = new Label("Music");
       musicVolumeSlider  = new Slider();
       musicVolumeSlider.setMaxSize(sliderWidth,sliderHeight);
       musicVolumeSlider.setMin(0.0);
       musicVolumeSlider.setMax(1.0);
       musicVolumeSlider.setValue(gameMusic.getMusicVolume());
       musicVolumeSlider.valueProperty().addListener(
               (observable, oldValue, newValue) ->{
                   gameMusic.setMusicVolume(newValue.doubleValue());
               });
   }



    private void soundEffectsVolumeSliderSetup(){
        Label soundEffectsVolumeSliderLabel = new Label("Sound effects");
        soundEffectVolumeSlider= new Slider();
        soundEffectVolumeSlider.setMaxSize(sliderWidth,sliderHeight);
        soundEffectVolumeSlider.setMin(0.0);
        soundEffectVolumeSlider.setMax(1.0);
        soundEffectVolumeSlider.setValue(gameMusic.getMusicVolume());
        soundEffectVolumeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) ->{
                    gameMusic.setSoundEffectsVolume(newValue.doubleValue());
                });
    }
}

package View.Menus;

import Util.Util;
import View.Utils.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;


public class SettingsMenu {
    Slider musicVolumeSlider;
    Slider soundEffectVolumeSlider;
    Button backBtn;
    VBox musicVolumeColumn;
    VBox soundEffectVolumeColumn;
    public VBox settingsMenu;

   public SettingsMenu
            (
                    Consumer<Double> onMusicVolumeAdjust,
                    Consumer<Double> onSoundEffectVolumeAdjust,
                    Runnable onBackBtn,
                    double defaultMusicVolume,
                    double defaultSoundEffectsVolume
            ) {

        backBtn = Util.defaultBackBtn();
        backBtn.setOnAction(event -> {
            onBackBtn.run();
        });

        settingsMenuSetup();

        musicVolumeSlider = Util.defaultSlider();
        musicVolumeSlider.setValue(defaultMusicVolume);
        musicVolumeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    onMusicVolumeAdjust.accept(newValue.doubleValue());
                });


        soundEffectVolumeSlider = Util.defaultSlider();
        soundEffectVolumeSlider.setValue(defaultSoundEffectsVolume);
        soundEffectVolumeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    onSoundEffectVolumeAdjust.accept(newValue.doubleValue());
                });


        columnSetup();
        settingsMenu.getChildren().addAll(musicVolumeColumn, soundEffectVolumeColumn, backBtn);
    }

    private void settingsMenuSetup() {
        settingsMenu = new VBox(20);
        settingsMenu.setMinHeight(200);
        settingsMenu.setMinWidth(400);
        settingsMenu.setStyle(Style.settingsMenuStyle);
        settingsMenu.setAlignment(Pos.CENTER);
        settingsMenu.setFocusTraversable(true);
        settingsMenu.setVisible(false);

        //managedProperty = whether it takes space on screen


    }

    private void columnSetup() {

        musicVolumeColumn = new VBox(5);
        musicVolumeColumn.setAlignment(Pos.CENTER);
        Label musicVolumeSliderLabel = new Label("Music");
        musicVolumeColumn.getChildren().addAll(musicVolumeSliderLabel, musicVolumeSlider);

        soundEffectVolumeColumn = new VBox(5);
        soundEffectVolumeColumn.setAlignment(Pos.CENTER);
        Label soundEffectVolumeSliderLabel = new Label("Sound Effects");
        soundEffectVolumeColumn.getChildren().addAll(soundEffectVolumeSliderLabel, soundEffectVolumeSlider);
    }


}

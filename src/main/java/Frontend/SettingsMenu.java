package Frontend;

import Backend.Style;
import Backend.Util;
import javafx.geometry.Pos;
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
    VBox musicVolumeColumn;
    VBox soundEffectVolumeColumn;
    static VBox settingsMenu;

    SettingsMenu() {
        settingsMenuSetup();
        musicVolumeSliderSetup();
        soundEffectsVolumeSliderSetup();
        columSetup();
        settingsMenu.getChildren().addAll( musicVolumeColumn, soundEffectVolumeColumn,backBtn);


    }

    private void settingsMenuSetup() {
        settingsMenu = new VBox(20);
        settingsMenu.setMinHeight(200);
        settingsMenu.setMinWidth(400);
        settingsMenu.setStyle(Style.settingsMenuStyle);
        settingsMenu.setAlignment(Pos.CENTER);
        settingsMenu.setFocusTraversable(true);
        settingsMenu.setVisible(false);
        settingsMenu.managedProperty().bind(settingsMenu.visibleProperty());

        settingsMenu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pauseMenu.setVisible(true);
                settingsMenu.setVisible(false);
                pauseMenu.requestFocus();
            }
        });



        backBtn = Util.defaultBackBtn();
        backBtn.setOnAction(event -> {
            pauseMenu.setVisible(true);
            settingsMenu.setVisible(false);
            pauseMenu.requestFocus();

        });
    }
    private void columSetup(){

        musicVolumeColumn = new VBox(5);
        musicVolumeColumn.setAlignment(Pos.CENTER);
        Label musicVolumeSliderLabel = new Label("Music");
        musicVolumeColumn.getChildren().addAll(musicVolumeSliderLabel,musicVolumeSlider);

        soundEffectVolumeColumn = new VBox(5);
        soundEffectVolumeColumn.setAlignment(Pos.CENTER);
        Label  soundEffectVolumeSliderLabel = new Label("Music");
        soundEffectVolumeColumn.getChildren().addAll( soundEffectVolumeSliderLabel, soundEffectVolumeSlider);
    }

    private void musicVolumeSliderSetup() {

        musicVolumeSlider = Util.defaultSlider();
        musicVolumeSlider.setValue(gameMusic.getMusicVolume());
        musicVolumeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    gameMusic.setMusicVolume(newValue.doubleValue());
                });
    }


    private void soundEffectsVolumeSliderSetup() {
        Label soundEffectsVolumeSliderLabel = new Label("Sound effects");
        soundEffectVolumeSlider = Util.defaultSlider();
        soundEffectVolumeSlider.setValue(gameMusic.getMusicVolume());
        soundEffectVolumeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    gameMusic.setSoundEffectsVolume(newValue.doubleValue());
                });
    }
}

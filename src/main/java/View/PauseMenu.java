package View;

import Controller.GameController;
import Controller.RefreshGameUI;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import static View.SettingsMenu.settingsMenu;
import static View.Tetris.gameMusic;
import static View.Tetris.gameStackPane;

public class PauseMenu {
    public static final VBox pauseMenu = new VBox(10);

    public PauseMenu(){
            pauseMenu.setAlignment(Pos.CENTER);
            pauseMenu.setStyle("-fx-background-color: rgba(34, 34, 34, 0.75);");
            pauseMenu.setFocusTraversable(true);

            pauseMenu.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE){
                    GameController.animationTimer.start();
                    GameController.isPaused = false;
                    Tetris.pauseMenuStackPane.setVisible(false);
                    gameMusic.resumeMusic();
                    gameStackPane.requestFocus();
                }
            });

            //ResumeBtn
            Button resumeBtn = new Button("Resume");
            resumeBtn.setOnAction(event -> {
                GameController.animationTimer.start();
                GameController.isPaused = false;
                Tetris.pauseMenuStackPane.setVisible(false);
                gameMusic.resumeMusic();
                gameStackPane.requestFocus();
            });

            //SettingsBtn
            Button settingsBtn = new Button("Settings");

            settingsBtn.setOnAction(event -> {
                pauseMenu.setVisible(false);
                settingsMenu.setVisible(true);
                settingsMenu.requestFocus();
            });

            //RestartBtn
            Button restartBtn = new Button("Restart");
            restartBtn.setOnAction(event -> {
                Tetris.board.boardClear();
                Tetris.refreshUI();
            });

            //QuitBtn
            Button quitBtn = new Button("Quit");
            quitBtn.setOnAction(event -> {
                System.exit(0);
            });

        pauseMenuBtnSetup(restartBtn);
        pauseMenuBtnSetup(quitBtn);
        pauseMenuBtnSetup(resumeBtn);
        pauseMenuBtnSetup(settingsBtn);

        pauseMenu.getChildren().addAll(resumeBtn, settingsBtn, restartBtn, quitBtn);
        pauseMenu.managedProperty().bind(pauseMenu.visibleProperty());

    }
    private void pauseMenuBtnSetup(Button pauseMenuBtn) {
        pauseMenuBtn.setStyle(Style.DEFAULT_BUTTON_STYLE);
        pauseMenuBtn.setMinWidth(250);
        pauseMenuBtn.setMinHeight(100);
        pauseMenuBtn.setMinWidth(200);
        pauseMenuBtn.setFocusTraversable(false);
    }
}

package View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;



public class PauseMenu {
    public VBox pauseMenu;

    public PauseMenu(Runnable onResume,  Runnable onSettings, Runnable onRestart, Runnable onQuit){
            this.pauseMenu = new VBox(10);
            pauseMenu.setAlignment(Pos.CENTER);
            pauseMenu.setStyle("-fx-background-color: rgba(34, 34, 34, 0.75);");
            pauseMenu.setFocusTraversable(true);

            pauseMenu.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE){
                    onResume.run();
                }
            });

            //ResumeBtn
            Button resumeBtn = new Button("Resume");
            resumeBtn.setOnAction(event -> {
                onResume.run();

            });

            //SettingsBtn
            Button settingsBtn = new Button("Settings");
            settingsBtn.setOnAction(event -> {
                onSettings.run();

            });

            //RestartBtn
            Button restartBtn = new Button("Restart");
            restartBtn.setOnAction(event -> {
                onRestart.run();

            });

            //QuitBtn
            Button quitBtn = new Button("Quit");
            quitBtn.setOnAction(event -> {
                onQuit.run();

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

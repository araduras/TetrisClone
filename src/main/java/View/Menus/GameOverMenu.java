package View.Menus;

import View.Utils.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameOverMenu {
    public VBox gameOverMenu;



    public GameOverMenu(Runnable onRestart, Runnable onQuit){
       this.gameOverMenu = new VBox(10);
        gameOverMenu.setMinHeight(200);
        gameOverMenu.setMinWidth(400);
        gameOverMenu.setStyle(Style.gameOverMenuStyle);
        gameOverMenu.setAlignment(Pos.CENTER);
        gameOverMenu.setFocusTraversable(true);
        gameOverMenu.setVisible(false);

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

        gameOverMenuBtnSetup(restartBtn);
        gameOverMenuBtnSetup(quitBtn);
        gameOverMenu.getChildren().addAll(restartBtn,quitBtn);
    }
    private void gameOverMenuBtnSetup(Button gameOverMenuBtn){
        gameOverMenuBtn.setStyle(Style.DEFAULT_BUTTON_STYLE);
        gameOverMenuBtn.setMinWidth(250);
        gameOverMenuBtn.setMinHeight(100);
        gameOverMenuBtn.setMinWidth(200);
        gameOverMenuBtn.setFocusTraversable(false);
    }

}

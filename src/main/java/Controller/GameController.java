package Controller;

import Model.Board;
import Model.Time;
import Util.Sounds;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;

public class GameController {
    long lastUpdate = 0;
    public static GameState currentGameState = GameState.DEFAULT;
    public static AnimationTimer animationTimer;
    public static Sounds gameMusic = new Sounds();
    public static boolean isPaused = false;

    public static long CURRENT_GAME_SPEED = Time.DEFAULT_GAME_SPEED;
    Board board;
    RefreshGameUI ui;

    public GameController(Board board, RefreshGameUI ui) {
        this.board = board;
        this.ui = ui;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long timeSinceLastUpdate = now - lastUpdate;
                if (timeSinceLastUpdate >= CURRENT_GAME_SPEED) {
                    board.movePieceDown();
                    ui.refreshUI();
                    if (board.isGameOver) {
                        this.stop();
                    }
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
        gameMusic.playMusic(Sounds.DEFAULT_THEME);
        setGameState(GameState.In_Game);
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT -> board.DEFAULT_MOVE_PIECE_LEFT();
            case RIGHT -> board.DEFAULT_MOVE_PIECE_RIGHT();
            case UP -> board.rotatePiece();
            case DOWN -> board.movePieceDown();
            case ESCAPE -> escapeHandler();
        }
        ui.refreshUI();
    }
    private void escapeHandler() {
        if (getGameState() == GameState.In_Game) {
            animationTimer.stop();
            gameMusic.pauseMusic();
            ui.setPauseMenuVisible(true);
            isPaused = true;
        } else if (getGameState() == GameState.Paused) {
            resumeGame();
            isPaused = false;
        } else if (getGameState() == GameState.Settings) {
            closeSettingsMenu();
        }
    }

    //PauseMenu
    public void resumeGame() {
        GameController.animationTimer.start();
        GameController.isPaused = false;
        ui.setPauseMenuVisible(false);
        ui.setPauseMenuOverlayVisible(false);
        gameMusic.resumeMusic();
    }
    public void restartBoard() {
        this.board.boardClear();
        ui.refreshUI();
    }
    public void quitGame() {
        System.exit(0);
    }
    public void openSettingsMenu() {
        ui.setSettingsMenuVisible(true);
    }
    public void closeSettingsMenu() {
        ui.setSettingsMenuVisible(false);
    }



    //SettingsMenu
    public void adjustMusicVolume(double volume) {
        gameMusic.setMusicVolume(volume);
    }
    public void adjustSoundEffectsVolume(double volume) {
        gameMusic.setSoundEffectsVolume(volume);
    }
    public void settingsMenuBackBtn(){
        closeSettingsMenu();
    }
    public double getMusicVolume(){
        return gameMusic.getMusicVolume();
    }
    public double getSoundEffectsVolume(){
        return gameMusic.getSoundEffectsVolume();
    }


    public GameState getGameState() {
        return currentGameState;
    }
    public void setGameState(GameState gameState) {
        currentGameState = gameState;
    }

}

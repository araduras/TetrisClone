package Controller;

import Model.Board;
import Model.Tetromino;
import Model.Time;
import Util.Sounds;
import Util.Util;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;

public class GameController {
    long lastUpdate = 0;
    public static GameState currentGameState = GameState.DEFAULT;
    public AnimationTimer animationTimer;
    public static Sounds gameMusic = new Sounds();
    private boolean pieceCanBeHeld;


    public static long CURRENT_GAME_SPEED = Time.DEFAULT_GAME_SPEED;
    Board board;
    RefreshGameUI ui;

    public GameController(Board board, RefreshGameUI ui) {
        setGameState(GameState.In_Game);

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
            case LEFT -> {
                if (getGameState() == GameState.In_Game) {
                    board.DEFAULT_MOVE_PIECE_LEFT();
                }
            }


            case RIGHT -> {
                if (getGameState() == GameState.In_Game) {
                    board.DEFAULT_MOVE_PIECE_RIGHT();
                }
            }

            case UP -> {
                if (getGameState() == GameState.In_Game && board.rotatePiece()) {
                    gameMusic.playSoundEffect_Rotate();
                }
            }
            case DOWN -> {
                if (getGameState() == GameState.In_Game) {
                    board.movePieceDown();
                }
            }
            case SPACE -> board.hardDrop();
            case C -> {
                if (board.holdPiece()){
                    gameMusic.playSoundEffect_Rotate();
                }

            }





            case ESCAPE -> escapeHandler();

        }
        ui.refreshUI();
    }

    private void escapeHandler() {
        if (getGameState() == GameState.In_Game) {
            animationTimer.stop();
            gameMusic.pauseMusic();
            ui.setPauseMenuVisible(true);
            setGameState(GameState.Paused);

        } else if (getGameState() == GameState.Paused) {
            resumeGame();

        } else if (getGameState() == GameState.Settings) {
            closeSettingsMenu();
        }
    }

    //PauseMenu
    public void resumeGame() {
        ui.setPauseMenuVisible(false);
        ui.setPauseMenuOverlayVisible(false);
        setGameState(GameState.In_Game);
        this.animationTimer.start();
        gameMusic.resumeMusic();
    }

    public void restartBoard() {
        this.board.boardClear();
        animationTimer.start();
        ui.refreshUI();
    }

    public void quitGame() {
        System.exit(0);
    }

    public void openSettingsMenu() {
        ui.setSettingsMenuVisible(true);
        setGameState(GameState.Settings);
    }

    public void closeSettingsMenu() {
        ui.setSettingsMenuVisible(false);
        setGameState(GameState.Paused);
    }


    //SettingsMenu
    public void adjustMusicVolume(double volume) {
        gameMusic.setMusicVolume(volume);
    }

    public void adjustSoundEffectsVolume(double volume) {
        gameMusic.setSoundEffectsVolume(volume);
    }

    public void settingsMenuBackBtn() {
        closeSettingsMenu();
    }

    public double getMusicVolume() {
        return gameMusic.getMusicVolume();
    }

    public double getSoundEffectsVolume() {
        return gameMusic.getSoundEffectsVolume();
    }


    public GameState getGameState() {
        return currentGameState;
    }

    public void setGameState(GameState gameState) {
        currentGameState = gameState;
    }

    public Tetromino getPieceInHold(){
        return board.holdPieceList.getFirst();
    }


    public Util.BoardSize getBoardSize(){
        return board.getBoardSize();
    }


    public Tetromino getBoardElement(int y, int x) {
        int[][] currentPieceMatrix = board.currentPieceMatrix;
        int currentPieceMatrixHeight = currentPieceMatrix.length;
        int currentPieceMatrixWidth = currentPieceMatrix[0].length;
        if(y - board.currentY >= 0 && y - board.currentY < currentPieceMatrixHeight
                && (x - board.currentX >= 0 && x - board.currentX < currentPieceMatrixWidth)
                && (currentPieceMatrix[y - board.currentY][x - board.currentX] == 1))
        {
            return board.currentPiece;
        }
        else {return board.getBoardElement(y,x);}


    }
    public int[][] getCurrentPieceMatrix(){
        return board.currentPieceMatrix;
    }
    public boolean isHoldListNull(){
        return board.holdPieceList.isEmpty();
    }
public int getHoldPieceSize(){
        return board.holdPieceList.getFirst().getShapeMatrix()[0].length;
}

    public boolean getHoldPieceMatrixAt(int y, int x) {
        if (y < board.holdPieceList.getFirst().getShapeMatrix()[0].length){
            int[][] holdPieceMatrix = board.holdPieceList
                    .getFirst()
                    .getShapeMatrix(0);
            System.out.println(Arrays.deepToString(holdPieceMatrix));

            return holdPieceMatrix[y][x] == 1;
        }
        else return false;
    }
}

package Controller;

import Model.Board;
import Model.Score;
import Model.Tetromino;
import Model.Time;
import Util.Sounds;
import Util.Util;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;

public class GameController {
    int currentScore = 0;
    long lastUpdate = 0;
    public static GameState currentGameState = GameState.DEFAULT;
    public AnimationTimer animationTimer;
    public static Sounds gameMusic = new Sounds();
    int lockDelayResetCount = 0;
    boolean lockIsDelayed = false;
    public static Time gameSpeed = new Time();
    int level = 0;
    Score score = new Score();
    long CURRENT_GAME_SPEED = gameSpeed.gameSpeed(level);
    Board board;
    RefreshGameUI ui;

    long lockDelay = 0;
    public GameController(Board board, RefreshGameUI ui) {
        setGameState(GameState.In_Game);

        this.board = board;
        this.ui = ui;


        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (board.isGameOver) {
                    this.stop();
                    setGameState(GameState.Game_Over);
                    ui.setGameOverMenuVisible(true);
                }
                long timeSinceLastUpdate = now - lastUpdate;
                if (timeSinceLastUpdate >= CURRENT_GAME_SPEED) {
                    if (board.movePieceDownByOne(board.pieceReachedBottomOrOtherPiece())) {
                        ui.refreshUI();
                        lockDelay = 0;

                    } else {
                        if (lockDelay == 0) {
                        lockDelay = now + gameSpeed.getLockDelay(level);
                        lockDelayResetCount = 0;
                        lockIsDelayed = true;
                        }
                    }


                    if (board.getLevel() >= level) {
                        level = board.getLevel();
                        CURRENT_GAME_SPEED = gameSpeed.gameSpeed(level);
                    }
                    lastUpdate = now;

                }
                if (lockDelay!= 0 && lockDelay <= now) {
                    board.lockPieceToBoard();
                    currentScore += score.getScoreForRowClear(board.rowClear(),level);
                    board.currentPieceHoldable = true;
                    board.newPieceSpawnLoop();
                    lockDelayResetCount = 0;
                    lockDelay = 0;
                    lockIsDelayed = false;
                    ui.refreshUI();
                }
            }
        };
        gameMusic.playMusic(Sounds.DEFAULT_THEME);
        setGameState(GameState.In_Game);
    }
    public void startGameLoop(){this.animationTimer.start();}
    public void handleKeyPress(KeyEvent event) {
        if (getGameState() == GameState.Game_Over){
            event.consume();
            return;
        }
        boolean actionSuccessful = false;
        switch (event.getCode()) {
            case LEFT -> {
                if (getGameState() == GameState.In_Game
                        && board.DEFAULT_MOVE_PIECE_LEFT()
                        || board.lockDelayedPieceLeftMovement(lockIsDelayed)) {
                    actionSuccessful = true;
                }
            }
            case RIGHT -> {
                if (getGameState() == GameState.In_Game
                        && board.DEFAULT_MOVE_PIECE_RIGHT()
                        || board.lockDelayedPieceRightMovement(lockIsDelayed)) {
                    actionSuccessful = true;

                }
            }
            case UP -> {
                if (getGameState() == GameState.In_Game && board.rotatePiece()) {
                    gameMusic.playSoundEffect_Rotate();
                    actionSuccessful = true;

                }
            }
            case DOWN -> {
                if (getGameState() == GameState.In_Game) {
                   if( board.movePieceDownByOne(board.pieceReachedBottomOrOtherPiece())){
                    currentScore += score.getScoreForDownwardMove(1,level);
                }}
            }
            case SPACE -> {
                currentScore += score.getScoreForHardDrop(board.rowsClearedWithHardDrop,level);
                currentScore += score.getScoreForHardDrop(board.hardDrop(), level);

            }


            case C -> {
                if (board.holdPiece()) {
                    gameMusic.playSoundEffect_Rotate();
                }

            }
            case ESCAPE -> escapeHandler();
        }
        if (lockDelay != 0
                && actionSuccessful
                && lockDelayResetCount < board.getMaxLockDelayResetCount()){
        lockDelay = gameSpeed.getLockDelay(level)+System.nanoTime();
        lockDelayResetCount++;
        ui.refreshUI();
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
        this.board.boardClearAndRestart();
        setLevel(0);
        currentScore = 0;
        animationTimer.stop();
        animationTimer.start();
        ui.refreshUI();
    }
    public void restartBoardOnGameOver() {
        restartBoard();
        ui.setGameOverMenuVisible(false);
        setGameState(GameState.In_Game);
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

    public Tetromino getPieceInHold() {
        return board.holdPieceList.getFirst();
    }

    public Tetromino getNextPieceInHold(int nextPiece) {
        return board.upComingPieces.get(nextPiece);
    }


    public Util.BoardSize getBoardSize() {
        return board.getBoardSize();
    }

    public Tetromino getBoardElement(int y, int x) {
        int[][] currentPieceMatrix = board.currentPieceMatrix;
        int currentPieceMatrixHeight = currentPieceMatrix.length;
        int currentPieceMatrixWidth = currentPieceMatrix[0].length;
        if (y - board.currentY >= 0 && y - board.currentY < currentPieceMatrixHeight
                && (x - board.currentX >= 0 && x - board.currentX < currentPieceMatrixWidth)
                && (currentPieceMatrix[y - board.currentY][x - board.currentX] == 1)) {
            return board.currentPiece;
        } else {
            return board.getBoardElement(y, x);
        }
    }


    public boolean isHoldListNull() {
        return board.holdPieceList.isEmpty();
    }

    public int getHoldPieceSize() {
        return board.holdPieceList.getFirst().getShapeMatrix()[0].length;
    }

    public int getNextPieceSize(int nextPiece) {
        return board.upComingPieces.get(nextPiece).getShapeMatrix().length;
    }

    public boolean getHoldPieceMatrixAt(int y, int x) {
        if (y < board.holdPieceList.getFirst().getShapeMatrix()[0].length) {
            int[][] holdPieceMatrix = board.holdPieceList
                    .getFirst()
                    .getShapeMatrix(0);
            return holdPieceMatrix[y][x] == 1;
        } else return false;
    }

    public boolean getNextPieceMatrixAt(int y, int x, int nextPiece) {
        if (y < board.upComingPieces.get(nextPiece).getShapeMatrix()[0].length) {
            int[][] nextPieceMatrix = board.upComingPieces
                    .get(nextPiece)
                    .getShapeMatrix(0);
            return nextPieceMatrix[y][x] == 1;
        } else return false;
    }

    public int getLevel() {
        return board.getLevel();
    }
    public void setLevel(int level) {
        board.setLevel(level);
    }
    public int getScore(){
        return currentScore;
    }

}

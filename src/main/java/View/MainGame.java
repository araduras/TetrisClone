package View;


import Controller.GameController;
import Controller.RefreshGameUI;
import Model.Board;
import View.Menus.GameOverMenu;
import View.Menus.PauseMenu;
import View.Menus.SettingsMenu;
import View.Utils.Sizes;
import Util.Util;
import View.Utils.GridUtils;
import View.Utils.Style;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainGame extends Application implements RefreshGameUI {
    Label levelLabel;
    Label currentLevelLabel;
    Label scoreLabel;
    Label currentScoreLabel;
    static int DEFAULT_IN_GAME_BOX_SPACING = 10;
    static int DEFAULT_IN_GAME_COLUMN_SPACING = 1;
    GameController controller;
    BorderPane mainLayout;
    PauseMenu pauseMenuComponent;
    SettingsMenu settingsMenuComponent;
    GameOverMenu gameOverMenuComponent;
    GridPane holdPieceBoxGridPane;
    public Board localBoard;
    int BOARD_Y_SIZE;
    int BOARD_X_SIZE;
    GridPane nextPieceBoxGridPane;
    private static VBox leftColumn;
    private static VBox rightColumn;
    private static StackPane gameStackPane;
    private static StackPane gameOverStackPane;
    private static StackPane pauseMenuStackPane;

    private static Rectangle[][] boardGridCells;
    private Rectangle[][] holdPieceBoxGridCells;
    private Rectangle[][] nextPieceBoxGridCells;
    public static final VBox holdPieceBox = new VBox(DEFAULT_IN_GAME_BOX_SPACING);
    public static final VBox nextPieceBox = new VBox(DEFAULT_IN_GAME_BOX_SPACING);
    public static final VBox scoreBox = new VBox(DEFAULT_IN_GAME_BOX_SPACING);
    private static final GridPane gridPane = new GridPane();
    private static final StackPane firstLayerBackgroundStackPane = new StackPane();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            firstLayerBackgroundStackPane.setStyle(Style.DEFAULT_GRAY_COLOR);
            gameSetup(primaryStage);
            firstLayerBackgroundStackPane.getChildren().add(mainLayout);
            Scene scene = new Scene(firstLayerBackgroundStackPane, 650, 650);

            primaryStage.setScene(scene);

            primaryStage.setFullScreen(true);

            primaryStage.show();


            scene.setOnKeyPressed(event ->
                    controller.handleKeyPress(event));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameSetup(Stage primaryStage) {
        localBoard = new Board();
        this.controller = new GameController(localBoard, this);

        gameStackPane = new StackPane();
        gameOverStackPane = new StackPane();
        pauseMenuStackPane = new StackPane();

        this.pauseMenuComponent = new PauseMenu(
                controller::resumeGame,
                controller::openSettingsMenu,
                controller::restartBoard,
                controller::quitGame
        );
        this.settingsMenuComponent = new SettingsMenu(
                controller::adjustMusicVolume,
                controller::adjustSoundEffectsVolume,
                controller::settingsMenuBackBtn,
                controller.getMusicVolume(),
                controller.getSoundEffectsVolume()
        );
        this.gameOverMenuComponent = new GameOverMenu(
                controller::restartBoardOnGameOver,
                controller::quitGame
        );


        Util.BoardSize boardSize = controller.getBoardSize();
        BOARD_Y_SIZE = boardSize.BOARD_Y_SIZE();
        BOARD_X_SIZE = boardSize.BOARD_X_SIZE();

        pauseMenuStackPane.getChildren().addAll(
                pauseMenuComponent.pauseMenu,
                settingsMenuComponent.settingsMenu);
        pauseMenuStackPane.setVisible(false);

        gameOverStackPane.getChildren().add(gameOverMenuComponent.gameOverMenu);
        gameOverStackPane.setVisible(false);

        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        boxesRightSideSetup();
        boxesLeftSideSetup();
        leftColumnSetup();
        rightColumnSetup();
        mainLayoutSetup(leftColumn, rightColumn);
        gameGridSetup();

        gameStackPane.getChildren().addAll(gridPane, pauseMenuStackPane, gameOverMenuComponent.gameOverMenu);
        controller.startGameLoop();
    }

    private void mainLayoutSetup(VBox leftColumn, VBox rightColumn) {
        this.mainLayout = new BorderPane();
        mainLayout.setCenter(gameStackPane);
        mainLayout.setLeft(leftColumn);
        mainLayout.setRight(rightColumn);
        BorderPane.setMargin(leftColumn, new Insets(0, 30, 0, 0));
        BorderPane.setMargin(rightColumn, new Insets(0, 0, 0, 30));
        mainLayout.setMaxWidth(680);
        mainLayout.setPrefWidth(680);
    }

    private void leftColumnSetup() {
        //Left column
        leftColumn = new VBox(DEFAULT_IN_GAME_COLUMN_SPACING);
        leftColumn.setAlignment(Pos.TOP_LEFT);
        leftColumn.setStyle(Style.DEFAULT_GRAY_COLOR);
        leftColumn.setPadding(new Insets(20));
        leftColumn.setPrefSize(Sizes.idealColumnWidth, Sizes.idealColumHeight);
        leftColumn.setMaxSize(Sizes.idealColumnWidth, Sizes.idealColumHeight);


        Label holdLabel = new Label("HOLD");
        holdLabel.setStyle(Style.LARGE_TEXT_STYLE);
        holdLabel.setAlignment(Pos.CENTER);

        levelLabel = new Label("Level");
        levelLabel.setStyle(Style.LARGE_TEXT_STYLE);
        levelLabel.setAlignment(Pos.CENTER);

        currentLevelLabel = new Label(String.valueOf(controller.getLevel()));
        currentLevelLabel.setStyle(Style.LARGE_TEXT_STYLE);
        currentLevelLabel.setAlignment(Pos.CENTER);

        scoreLabel = new Label("Score");
        scoreLabel.setStyle(Style.LARGE_TEXT_STYLE);
        scoreLabel.setAlignment(Pos.CENTER);

        currentScoreLabel = new Label("0");
        currentScoreLabel.setStyle(Style.LARGE_TEXT_STYLE);
        currentScoreLabel.setAlignment(Pos.CENTER);
        currentScoreLabel.setText(String.valueOf(controller.getScore()));

        leftColumn.getChildren().addAll(
                holdLabel,
                holdPieceBox,
                scoreBox,
                levelLabel,
                currentLevelLabel,
                scoreLabel,
                currentScoreLabel);
    }

    private void rightColumnSetup() {
        //Right column
        rightColumn = new VBox(DEFAULT_IN_GAME_COLUMN_SPACING);
        rightColumn.setAlignment(Pos.TOP_RIGHT);
        rightColumn.setStyle(Style.DEFAULT_GRAY_COLOR);
        rightColumn.setPrefSize(Sizes.idealColumnWidth, Sizes.idealColumHeight);
        rightColumn.setMaxSize(Sizes.idealColumnWidth, Sizes.idealColumHeight);
        rightColumn.setPadding(new Insets(20));

        Label nextLabel = new Label("Next");
        nextLabel.setStyle(Style.LARGE_TEXT_STYLE);
        nextLabel.setAlignment(Pos.CENTER);

        rightColumn.getChildren().addAll(nextLabel, nextPieceBox);
    }
    private void boxesRightSideSetup() {
        nextPieceBox.setMaxSize(100, 100);
        nextPieceBoxGridPane = new GridPane();
        nextPieceBoxGridCells =
                GridUtils.gridBuilder(
                        nextPieceBoxGridPane,
                        15,
                        6,
                        30,
                        30,
                        Style.baseGridStyleWithBorder);
        scoreBox.setMaxSize(100, 100);
        nextPieceBox.setAlignment(Pos.CENTER_LEFT);
        nextPieceBox.getChildren().add(nextPieceBoxGridPane);
    }
    private void boxesLeftSideSetup() {
        holdPieceBox.setMaxSize(100, 100);
        holdPieceBoxGridPane = new GridPane();
        holdPieceBoxGridCells =
                GridUtils.gridBuilder(
                        holdPieceBoxGridPane,
                        6,
                        7,
                        30,
                        30,
                        Style.baseGridStyleWithBorder);
        holdPieceBox.setAlignment(Pos.CENTER_RIGHT);
        holdPieceBox.getChildren().add(holdPieceBoxGridPane);

    }
    private void gameGridSetup() {
        boardGridCells =
                GridUtils.gridBuilder(
                        gridPane,
                        BOARD_Y_SIZE,
                        BOARD_X_SIZE,
                        50,
                        50,
                        Style.baseGridStyleWithBorder
                );
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle(Style.DEFAULT_GRAY_COLOR);
    }
    public void refreshUI() {
        //Board
        for (int y = 0; y < BOARD_Y_SIZE; y++) {
            for (int x = 0; x < BOARD_X_SIZE; x++) {
                if (!controller.getBoardElement(y,x).name().equals("EMPTY")){
                    boardGridCells[y][x].setStyle(controller.getBoardElement(y, x).getStyle());
                }
                else{
                    boardGridCells[y][x].setStyle(Style.baseGridStyleWithBorder);

                }

            }
        }
        //Hold
        int holdGridOffsetY = 2;
        int holdGridOffsetX = 2;
        if (!controller.isHoldListNull()) {
            for (int i = 0; i < holdPieceBoxGridCells.length; i++) {
                for (int j = 0; j < holdPieceBoxGridCells[0].length; j++) {
                    holdPieceBoxGridCells[i][j].setStyle(Style.baseGridStyleWithBorder);
                }
            }
            for (int y = 0; y < controller.getHoldPieceSize(); y++) {
                for (int x = 0; x < controller.getHoldPieceSize(); x++) {
                    if (controller.getHoldPieceMatrixAt(y, x)) {
                        holdPieceBoxGridCells[holdGridOffsetY + y][holdGridOffsetX + x]
                                .setStyle(controller.getPieceInHold().getStyle());
                    } else {
                        holdPieceBoxGridCells[holdGridOffsetY + y][holdGridOffsetX + x]
                                .setStyle(Style.baseGridStyleWithBorder);
                    }
                }
            }
        }
        //NextPiece
        int nextPieceBoxGridOffsetX = 1;
        int drawnPieceEndedAt = 0;
        GridUtils.gridClear(nextPieceBoxGridCells);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < nextPieceBoxGridCells[0].length; j++) {
                nextPieceBoxGridCells[drawnPieceEndedAt][j].setStyle(Style.baseGridStyleWithBorder);
            }
            drawnPieceEndedAt += 1;
            drawnPieceEndedAt = GridUtils.pieceOnGridRenderer(
                    nextPieceBoxGridCells,
                    drawnPieceEndedAt,
                    nextPieceBoxGridOffsetX,
                    controller.getNextPieceInHold(i));
        }
        //Level
        currentLevelLabel.setText(String.valueOf(controller.getLevel()));
        //Score
        currentScoreLabel.setText(String.valueOf(controller.getScore()));

    }

    @Override
    public void setPauseMenuVisible(boolean visible) {
        pauseMenuComponent.pauseMenu.setVisible(visible);
        if (visible) {
            setPauseMenuOverlayVisible(true);
            pauseMenuComponent.pauseMenu.requestFocus();
        } else {
            gameStackPane.requestFocus();
        }
    }
    @Override
    public void setPauseMenuOverlayVisible(boolean visible) {
        pauseMenuStackPane.setVisible(visible);
    }
    @Override
    public void setSettingsMenuVisible(boolean visible) {
        settingsMenuComponent.settingsMenu.setVisible(visible);
        if (visible) {
            settingsMenuComponent.settingsMenu.requestFocus();
        } else {
            pauseMenuComponent.pauseMenu.requestFocus();
        }
    }
    @Override
    public void setGameOverMenuVisible(boolean visible){
        gameOverStackPane.setVisible(visible);
        gameOverMenuComponent.gameOverMenu.setVisible(visible);
    }




}
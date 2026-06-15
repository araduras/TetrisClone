package View;

import Controller.GameController;
import Controller.RefreshGameUI;
import Model.Board;
import Model.Sizes;
import Util.Util;
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

import java.util.Arrays;


public class Tetris extends Application implements RefreshGameUI {
    static int DEFAULT_IN_GAME_BOX_SPACING = 10;
    static int DEFAULT_IN_GAME_COLUMN_SPACING = 1;

    GameController controller;
    BorderPane mainLayout;
    PauseMenu pauseMenuComponent;
    SettingsMenu settingsMenuComponent;
    GridPane holdPieceBoxGridPane;
    public Board localBoard;
    int BOARD_Y_SIZE;
    int BOARD_X_SIZE;
    GridPane nextPieceBoxGridPane;
    private static VBox leftColumn;
    private static VBox rightColumn;
    private static StackPane gameStackPane;
    private static StackPane pauseMenuStackPane;
    private static Rectangle[][] boardGridCells;
    private static Rectangle[][] holdPieceBoxGridCells;
    private static Rectangle [][] nextPieceBoxGridCells;
    public static final VBox holdPieceBox = new VBox(DEFAULT_IN_GAME_BOX_SPACING);
    public static final VBox nextPieceBox = new VBox(DEFAULT_IN_GAME_BOX_SPACING);
    public static final VBox scoreBox = new VBox(DEFAULT_IN_GAME_BOX_SPACING);
    private static final GridPane gridPane = new GridPane();
    private static final StackPane firstLayerBackgroundStackPane = new StackPane();



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            firstLayerBackgroundStackPane.setStyle(Style.DEFAULT_GRAY_COLOR);
            gameSetup(primaryStage);
            firstLayerBackgroundStackPane.getChildren().add(mainLayout);
            Scene scene = new Scene(firstLayerBackgroundStackPane, 650, 650);

            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setFullScreen(true);

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


        Util.BoardSize boardSize = controller.getBoardSize();
        BOARD_Y_SIZE = boardSize.BOARD_Y_SIZE();
        BOARD_X_SIZE = boardSize.BOARD_X_SIZE();

        pauseMenuStackPane.getChildren().addAll(pauseMenuComponent.pauseMenu, settingsMenuComponent.settingsMenu);
        pauseMenuStackPane.setVisible(false);

        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        boxesRightSideSetup();
        boxesLeftSideSetup();
        leftColumnSetup();
        rightColumnSetup();
        mainLayoutSetup(leftColumn, rightColumn);
        gridSetup();

        gameStackPane.getChildren().addAll(gridPane, pauseMenuStackPane);
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

        Label holdTitle = new Label("HOLD");
        holdTitle.setStyle(Style.LARGE_TEXT_STYLE);
        holdTitle.setAlignment(Pos.CENTER);

        leftColumn.getChildren().addAll(holdTitle, holdPieceBox, scoreBox);
    }

    private void rightColumnSetup() {
        //Right column
        rightColumn = new VBox(DEFAULT_IN_GAME_COLUMN_SPACING);
        rightColumn.setAlignment(Pos.TOP_RIGHT);
        rightColumn.setStyle(Style.DEFAULT_GRAY_COLOR);
        rightColumn.setPrefSize(Sizes.idealColumnWidth, Sizes.idealColumHeight);
        rightColumn.setMaxSize(Sizes.idealColumnWidth, Sizes.idealColumHeight);
        rightColumn.setPadding(new Insets(20));

        Label label = new Label("Next");
        label.setStyle(Style.LARGE_TEXT_STYLE);
        label.setAlignment(Pos.CENTER);

        rightColumn.getChildren().addAll(label, nextPieceBox);
    }

    private void boxesRightSideSetup() {
        nextPieceBox.setMaxSize(100, 100);
        nextPieceBoxGridPane = new GridPane();
        nextPieceBoxGridCells = new Rectangle[20][6];
        scoreBox.setMaxSize(100, 100);
        for (int i = 0; i < nextPieceBoxGridCells.length; i++) {
            for (int j = 0; j < nextPieceBoxGridCells[0].length; j++) {
                Rectangle baseGrid = new Rectangle(30, 30);
                baseGrid.setStyle(Style.baseGridStyleWithBorder);
                nextPieceBoxGridPane.add(baseGrid,j,i);
                nextPieceBoxGridCells[i][j] = baseGrid;
            }
        }
        nextPieceBox.setAlignment(Pos.CENTER_LEFT);
        nextPieceBox.getChildren().add(nextPieceBoxGridPane);
    }

    private void boxesLeftSideSetup() {
        holdPieceBox.setMaxSize(100, 100);
        holdPieceBoxGridPane = new GridPane();
         holdPieceBoxGridCells = new Rectangle[6][7];

        for (int i = 0; i < holdPieceBoxGridCells.length; i++) {
            for (int j = 0; j < holdPieceBoxGridCells[0].length; j++) {
                Rectangle baseGrid = new Rectangle(30, 30);
                baseGrid.setStyle(Style.baseGridStyleWithBorder);
                holdPieceBoxGridPane.add(baseGrid,j,i);
                holdPieceBoxGridCells[i][j] = baseGrid;
            }
        }

        holdPieceBox.setAlignment(Pos.CENTER_RIGHT);
        holdPieceBox.getChildren().add(holdPieceBoxGridPane);

    }

    private void gridSetup() {
        boardGridCells = new Rectangle[BOARD_Y_SIZE][BOARD_X_SIZE];
        for (int i = 0; i < BOARD_Y_SIZE; i++) {
            for (int j = 0; j < BOARD_X_SIZE; j++) {
                Rectangle baseGrid = new Rectangle(30, 30);
                baseGrid.setStyle(Style.baseGridStyleWithBorder);
                gridPane.add(baseGrid, j, i);
                boardGridCells[i][j] = baseGrid;
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle(Style.DEFAULT_GRAY_COLOR);
    }


    public void refreshUI() {
        //Board
        for (int y = 0; y < BOARD_Y_SIZE; y++) {
            for (int x = 0; x < BOARD_X_SIZE; x++) {
                boardGridCells[y][x].setStyle(controller.getBoardElement(y, x).getStyle());
            }
        }
        //Hold
        int holdGridOffsetY = 2;
        int holdGridOffsetX = 2;
        if(!controller.isHoldListNull()){
            for (int i = 0; i < holdPieceBoxGridCells.length; i++) {
                for (int j = 0; j < holdPieceBoxGridCells[0].length; j++) {
                    holdPieceBoxGridCells[i][j].setStyle(Style.baseGridStyleWithBorder);
                }
            }
            for (int y = 0; y < controller.getHoldPieceSize(); y++) {
                for (int x = 0; x < controller.getHoldPieceSize(); x++) {
                    if (controller.getHoldPieceMatrixAt(y,x)){
                        holdPieceBoxGridCells[holdGridOffsetY+y][holdGridOffsetX+x]
                                .setStyle(controller.getPieceInHold().getStyle());
                    }
                    else {holdPieceBoxGridCells[holdGridOffsetY+y][holdGridOffsetX+x]
                            .setStyle(Style.baseGridStyleWithBorder);}
                }
            }
        }
        //NextPiece


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


}
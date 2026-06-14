package View;

import Controller.GameController;
import Controller.RefreshGameUI;
import Model.Board;
import Model.Sizes;
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



public class Tetris extends Application implements RefreshGameUI {

    GameController controller;
    BorderPane mainLayout;
    PauseMenu pauseMenuComponent;
    SettingsMenu settingsMenuComponent;

    static VBox leftColumn;
    static VBox rightColumn;
    public static Board board;
    public static StackPane gameStackPane;
    public static StackPane pauseMenuStackPane;
    private static Rectangle[][] boardGridCells;

    public static final VBox holdPieceBox = new VBox(30);
    public static final VBox nextPieceBox = new VBox(30);
    public static final VBox scoreBox = new VBox(30);
    private static final GridPane gridPane = new GridPane();
    private static final StackPane firstLayerBackgroundStackPane = new StackPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            firstLayerBackgroundStackPane.setStyle(Style.DEFAULT_GRAY_COLOR);
            gameSetup(primaryStage);
            firstLayerBackgroundStackPane.getChildren().add(mainLayout);
            Scene scene = new Scene(firstLayerBackgroundStackPane, 650, 650);

            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setFullScreen(true);

            scene.setOnKeyPressed(event -> {
                controller.handleKeyPress(event);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameSetup(Stage primaryStage) {
        board = new Board();
        controller = new GameController(board, this);

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
        leftColumn = new VBox(10);
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
        rightColumn = new VBox(10);
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
        scoreBox.setMaxSize(100, 100);


    }
    private void boxesLeftSideSetup() {
        holdPieceBox.setMaxSize(100, 100);
        GridPane holdPieceBoxGridPane = new GridPane();
        Rectangle[][] holdPieceBoxGridCells = new Rectangle[6][6];


        for (int i = 0; i < holdPieceBoxGridCells.length; i++) {
            for (int j = 0; j < holdPieceBoxGridCells[0].length; j++) {
                Rectangle baseGrid = new Rectangle(40, 40);
                baseGrid.setStyle(Style.baseGridStyleWithBorder);
                holdPieceBoxGridPane.add(baseGrid,i,j);
                holdPieceBoxGridCells[i][j] = baseGrid;
            }
        }

        holdPieceBox.setAlignment(Pos.CENTER);
holdPieceBox.getChildren().add(holdPieceBoxGridPane);

    }

    private void gridSetup() {
        boardGridCells = new Rectangle[Board.BOARD_Y_SIZE][Board.BOARD_X_SIZE];
        for (int i = 0; i < Board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_X_SIZE; j++) {
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
        if (board == null || board.currentPieceMatrix == null) {
            return;
        }
        int[][] currentPieceMatrix = board.currentPieceMatrix;
        int currentPieceMatrixHeight = currentPieceMatrix.length;
        int currentPieceMatrixWidth = currentPieceMatrix[0].length;
        for (int i = 0; i < Board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_X_SIZE; j++) {
                if (!board.getBoardElement(i, j).name().equals("EMPTY")) {
                    //Locked on piece
                    boardGridCells[i][j].setStyle(board.getBoardElement(i, j).getStyle());
                } else if (
                        (i - board.currentY >= 0 && i - board.currentY < currentPieceMatrixHeight)
                                && (j - board.currentX >= 0 && j - board.currentX < currentPieceMatrixWidth)
                                && (currentPieceMatrix[i - board.currentY][j - board.currentX] == 1)
                ) {
                    //Active falling piece
                    boardGridCells[i][j].setStyle(board.currentPiece.getStyle());
                } else {
                    //Empty cells
                    boardGridCells[i][j].setStyle(Style.emptyGridCellStyle);

                }
            }
        }

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
package Frontend;
import Backend.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Tetris extends Application {
    public static boolean isPaused = false;
    public static long CURRENT_GAME_SPEED = Time.DEFAULT_GAME_SPEED;
    long lastUpdate = 0;

    BorderPane mainLayout;
    VBox pauseMenu;
    VBox settingsMenu;

    static VBox leftColumn;
    static VBox rightColumn;
    public static Board board;
    public static StackPane gameStackPane;
    public static StackPane pauseMenuStackPane;
    private static Rectangle[][] gridCells;
    public static AnimationTimer animationTimer;

    public static final VBox holdPieceBox = new VBox(30);
    public static final VBox nextPieceBox = new VBox(30);
    public static final VBox scoreBox = new VBox(30);

    public static Sounds gameMusic = new Sounds();
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
                if (event.getCode() == KeyCode.ESCAPE) {
                    if (!isPaused){
                        animationTimer.stop();
                        isPaused = true;
                        pauseMenuStackPane.setVisible(true);
                        gameMusic.pauseMusic();
                        pauseMenu.requestFocus();
                    }
                }
                if (isPaused) {
                    return;
                }

                //Keys
                if (event.getCode() == KeyCode.LEFT) {
                    board.DEFAULT_MOVE_PIECE_LEFT();
                    refreshUI();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    board.DEFAULT_MOVE_PIECE_RIGHT();
                    refreshUI();
                } else if (event.getCode() == KeyCode.DOWN) {
                    board.movePieceDown();
                    refreshUI();
                }
                else if(event.getCode() == KeyCode.UP){
                    board.rotatePiece();
                    refreshUI();
                    gameMusic.playSoundEffect_Rotate();
                }

            });


            animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long timeSinceLastUpdate = now - lastUpdate;
                    if (timeSinceLastUpdate >= CURRENT_GAME_SPEED) {
                        board.movePieceDown();
                        refreshUI();
                        if (board.isGameOver) {
                            this.stop();
                        }
                        lastUpdate = now;
                    }
                }
            };

            animationTimer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameSetup(Stage primaryStage){

        board = new Board();
        gameStackPane = new StackPane();
        pauseMenuStackPane = new StackPane();
        new PauseMenu();
        new SettingsMenu();

        this.pauseMenu = PauseMenu.pauseMenu;
        this.settingsMenu = SettingsMenu.settingsMenu;

        pauseMenuStackPane.getChildren().addAll(pauseMenu,settingsMenu);
        pauseMenuStackPane.setVisible(false);
        gameMusic.playMusic(Sounds.DEFAULT_THEME);

        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        boxLeftRightSetup();
        leftColumnSetup();
        rightColumnSetup();
        mainLayoutSetup(leftColumn,rightColumn);
        gridSetup();

        gameStackPane.getChildren().addAll(gridPane, pauseMenuStackPane);
    }
    private void mainLayoutSetup(VBox leftColumn, VBox rightColumn){
        //WindowSetup
        this.mainLayout = new BorderPane();
        mainLayout.setCenter(gameStackPane);
        mainLayout.setLeft(leftColumn);
        mainLayout.setRight(rightColumn);
        BorderPane.setMargin(leftColumn, new Insets(0,30,0,0));
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

        leftColumn.getChildren().addAll(holdTitle,holdPieceBox,scoreBox);
    }

    private void rightColumnSetup(){
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
    private void boxLeftRightSetup(){
        nextPieceBox.setMaxSize(1,1);
        scoreBox.setMaxSize(1,1);
        holdPieceBox.setMaxSize(1,1);

    }
    private void gridSetup(){
        gridCells = new Rectangle[Board.BOARD_Y_SIZE][Board.BOARD_X_SIZE];
        for (int i = 0; i < Board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_X_SIZE; j++) {
                Rectangle baseGrid = new Rectangle(30, 30);
                baseGrid.setStyle(Style.baseGridStyle);
                gridPane.add(baseGrid, j, i);
                gridCells[i][j] = baseGrid;
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle(Style.DEFAULT_GRAY_COLOR);


    }
    public static void refreshUI() {
        if (board == null || board.currentPieceMatrix == null) {
            return;
        }
        int[][] currentPieceMatrix = board.currentPieceMatrix;
        int currentPieceMatrixHeight = currentPieceMatrix.length;
        int currentPieceMatrixWidth = currentPieceMatrix[0].length;
        for (int i = 0; i < Board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_X_SIZE; j++) {
                if (!board.getBoardElement(i, j).name().equals("EMPTY")) {
<<<<<<< HEAD
                    gridCells[i][j].setStyle("-fx-fill: red; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
=======
                    //Locked on piece
<<<<<<< HEAD:src/Tetris.java
                    gridCells[i][j].setStyle(board.getBoardElement(i,j).getStyle());
>>>>>>> 880831d (Music and Style added)
=======
                    gridCells[i][j].setStyle(board.getBoardElement(i, j).getStyle());
>>>>>>> ee359ac (File structure revamp, music and sound effect settings added):src/main/java/Frontend/Tetris.java
                } else if (
                        (i - board.currentY >= 0 && i - board.currentY < currentPieceMatrixHeight)
                                && (j - board.currentX >= 0 && j - board.currentX < currentPieceMatrixWidth)
<<<<<<< HEAD
                                && (board.currentPieceMatrix[i - board.currentY][j - board.currentX] == 1)
=======
                                && (currentPieceMatrix[i - board.currentY][j - board.currentX] == 1)
>>>>>>> d217f6a (Rotation nearly solved, before MVC transformation)
                ) {
<<<<<<< HEAD
                    gridCells[i][j].setStyle("-fx-fill: red; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
=======
                    //Active falling piece
                    gridCells[i][j].setStyle(board.currentPiece.getStyle());
>>>>>>> 880831d (Music and Style added)
                } else {
                    //Empty cells
                    gridCells[i][j].setStyle(Style.emptyGridCellStyle);

                }
            }
        }

    }
}
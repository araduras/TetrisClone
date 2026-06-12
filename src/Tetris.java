import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;



public class Tetris extends Application {
    AnimationTimer animationTimer;
    private boolean isPaused = false;
    private static long CURRENT_GAME_SPEED = Time.DEFAULT_GAME_SPEED;
    long lastUpdate = 0;
    private Board board;
    GridPane gridPane = new GridPane();
    BorderPane mainLayout;



    @Contract(" -> new")
    private @NotNull Scene sceneSetup() {
        this.board = new Board();
        this.gridCells = new Rectangle[board.BOARD_Y_SIZE][board.BOARD_X_SIZE];
        this.mainLayout = new BorderPane();
        //Column stuff
        int columWidth = 250;
        //Left column
        VBox leftColumn = new VBox(10);
        leftColumn.setAlignment(Pos.TOP_LEFT);
        leftColumn.setStyle(Style.COLOR_GRIDPANE);
        leftColumn.setPadding(new javafx.geometry.Insets(20));
        Label holdTitle = new Label("HOLD");
        leftColumn.setMinWidth(columWidth);
        holdTitle.setStyle
                (
                "-fx-font-family: 'Comic Sans MS';" +
                " -fx-font-size: 55px; " +
                "-fx-background-color: #222222; " +
                "-fx-text-fill: #ffffff;" +
                "-fx-padding: 10px 20px 10px 20px;"
                 );

        leftColumn.getChildren().add(holdTitle);

        //Right column
        VBox rightColumn = new VBox(10);
        rightColumn.setAlignment(Pos.TOP_RIGHT);
        rightColumn.setStyle(Style.COLOR_GRIDPANE);
        rightColumn.setMinWidth(columWidth);
        rightColumn.setPadding(new javafx.geometry.Insets(20));
        Label asd = new Label("asd");
        asd.setStyle
                (
                        "-fx-font-family: 'Comic Sans MS';" +
                                " -fx-font-size: 55px; " +
                                "-fx-background-color: #222222; " +
                                "-fx-text-fill: #ffffff;" +
                                "-fx-padding: 10px 20px 10px 20px;"
                );

        rightColumn.getChildren().add(asd);


        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle(Style.COLOR_GRIDPANE);


        mainLayout.setCenter(this.gridPane);
        mainLayout.setLeft(leftColumn);
        mainLayout.setRight(rightColumn);
        // Stage = Window,
        // Scene = the canvas inside it,
        // GridPane organizes individual Rectangle shapes into a visual 10x20 board layout.
        for (int i = 0; i < board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < board.BOARD_X_SIZE; j++) {
                Rectangle baseGrid = new Rectangle(30, 30);
                baseGrid.setStyle(Style.baseGridStyle);
                gridPane.add(baseGrid, j, i);
                gridCells[i][j] = baseGrid;
            }
        }

        return new Scene(mainLayout, 650, 650);

    }



    private Rectangle[][] gridCells;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Music gameAudio = new Music();
        gameAudio.playSong("Tetris 99");
        gameAudio.setVolume(0.1);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        try {
            Scene scene = sceneSetup();
            scene.setOnKeyPressed(event -> {
                 if (event.getCode() == KeyCode.ESCAPE) {
                    if (isPaused){
                        animationTimer.start();
                        isPaused = false;

                    }
                    else {
                        animationTimer.stop();
                        isPaused = true;
                    }
                    return;
                }
                if (isPaused){
                    return;
                }

                if (event.getCode() == KeyCode.LEFT){
                    board.movePieceLeft();
                    refreshUI();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    board.movePieceRight();
                    refreshUI();
                } else if (event.getCode() == KeyCode.DOWN) {
                    board.movePieceDown();
                    refreshUI();
                } else if (event.getCode() == KeyCode.UP) {
                    board.rotatePiece();
                    refreshUI();
                }


            });
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setFullScreen(true);
             this.animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long timeSinceLastUpdate = now - lastUpdate;
                    if (timeSinceLastUpdate >= CURRENT_GAME_SPEED) {
                        board.movePieceDown();
                        refreshUI();
                        if (board.isGameOver){
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

    public void setGameSpeed(long score) {

    }

    public long getGameSpeed() {
        return CURRENT_GAME_SPEED;
    }

    public void refreshUI() {
        if (board == null || board.currentPieceMatrix == null) {
            return;
        }
        int[][] currentPieceMatrix = board.currentPieceMatrix;
        int currentPieceMatrixHeight = currentPieceMatrix.length;
        int currentPieceMatrixWidth = currentPieceMatrix[0].length;
        for (int i = 0; i < board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < board.BOARD_X_SIZE; j++) {
                if (!board.getBoardElement(i, j).name().equals("EMPTY")) {
<<<<<<< HEAD
                    gridCells[i][j].setStyle("-fx-fill: red; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
=======
                    //Locked on piece
                    gridCells[i][j].setStyle(board.getBoardElement(i,j).getStyle());
>>>>>>> 880831d (Music and Style added)
                } else if (
                        (i - board.currentY >= 0 && i - board.currentY < currentPieceMatrixHeight)
                                && (j - board.currentX >= 0 && j - board.currentX < currentPieceMatrixWidth)
                                && (board.currentPieceMatrix[i - board.currentY][j - board.currentX] == 1)
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
                    //gridCells[i][j].setStyle("-fx-fill: #ffdf00; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
                }
            }
        }

    }



    public static void main(String[] args) {
        launch(args);

    }
}


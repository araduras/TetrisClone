import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Tetris extends Application {
    private static long CURRENT_GAME_SPEED = Time.DEFAULT_GAME_SPEED;
    long lastUpdate = 0;
    private Board board;
    GridPane gridPane = new GridPane();

    private Scene sceneSetup() {
        this.board = new Board();
        this.gridCells = new Rectangle[board.BOARD_Y_SIZE][board.BOARD_X_SIZE];

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #222222;");
        // Stage = Window,
        // Scene = the canvas inside it,
        // GridPane organizes individual Rectangle shapes into a visual 10x20 board layout.
        for (int i = 0; i < board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < board.BOARD_X_SIZE; j++) {
                Rectangle baseGrid = new Rectangle(30, 30);
                baseGrid.setStyle("-fx-fill: #333333; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
                gridPane.add(baseGrid, j, i);
                gridCells[i][j] = baseGrid;
            }
        }
        return new Scene(gridPane, 350, 650);

    }

    private Rectangle[][] gridCells;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Scene scene = sceneSetup();
            scene.setOnKeyPressed(event -> {
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
            AnimationTimer animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long timeSinceLastUpdate = now - lastUpdate;
                    if (timeSinceLastUpdate >= CURRENT_GAME_SPEED) {
                        board.movePieceDown();
                        refreshUI();
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
                    gridCells[i][j].setStyle("-fx-fill: red;");
                } else if (
                        (i - board.currentY >= 0 && i - board.currentY < currentPieceMatrixHeight)
                                && (j - board.currentX >= 0 && j - board.currentX < currentPieceMatrixWidth)
                                && (board.currentPiece.getShapeMatrix()[i - board.currentY][j - board.currentX] == 1)
                ) {
                    gridCells[i][j].setStyle("-fx-fill: red;");
                } else {
                    gridCells[i][j].setStyle("-fx-fill: #333333; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}


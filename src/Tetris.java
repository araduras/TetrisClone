import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class Tetris extends Application {
    private Board board;
    private long lastUpdate = 0;
    private long oneMillion = 1000000;
    private Rectangle[][] gridCells;
    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            this.board = new Board();
            this.gridCells = new Rectangle[board.BOARD_Y_SIZE][board.BOARD_X_SIZE];

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setStyle("-fx-background-color: #222222;");



            // The Stage is the outer window frame, the Scene is the canvas inside it,
            // and the GridPane organizes individual Rectangle shapes into a visual 10x20 board layout.

            for (int i = 0; i < board.BOARD_Y_SIZE; i++) {
                for (int j = 0; j < board.BOARD_X_SIZE; j++) {

                    Rectangle baseGrid = new Rectangle(30,30);
                    baseGrid.setStyle("-fx-fill: #333333; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
                    gridPane.add(baseGrid, j, i);
                    gridCells[i][j] = baseGrid;
                }
            }

            Scene scene = new Scene(gridPane, 350, 650);
            primaryStage.setScene(scene);

            primaryStage.show();
            primaryStage.setFullScreen(true);
            AnimationTimer animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (now-lastUpdate >= oneMillion){

                    }
                    refreshUI();
                    board.movePieceDown();
                }
            };
            animationTimer.start();







        }





        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void refreshUI(){
        if (board == null || board.currentPieceMatrix == null){
            return;
        }
        int [][] currentPieceMatrix = board.currentPieceMatrix;
        int currentPieceMatrixHeight = currentPieceMatrix.length;
        int currentPieceMatrixWidth = currentPieceMatrix[0].length;
        for (int i = 0; i < board.BOARD_Y_SIZE; i++) {
            for (int j = 0; j < board.BOARD_X_SIZE; j++) {
                if (!board.getBoardElement(i,j).name().equals("EMPTY")){
                    gridCells[i][j].setStyle("-fx-fill: red;");
                } else if (
                        (i - board.currentY >=0 && i - board.currentY < currentPieceMatrixHeight)
                    &&  (j - board.currentX >= 0 && j - board.currentX < currentPieceMatrixWidth)
                    &&  (board.currentPiece.getShapeMatrix()[i - board.currentY ][j - board.currentX ]==1)
                ) {
                    gridCells[i][j].setStyle("-fx-fill: red;");
                }
                else{
                    gridCells[i][j].setStyle("-fx-fill: #333333; -fx-stroke: #555555; -fx-stroke-width: 0.5;");
                }

            }
        }
    }

public static void main(String[] args)  {
        launch(args);

    }
}


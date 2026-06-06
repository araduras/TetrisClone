import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Board {
    static final int BOARD_Y_SIZE = 20;
    static final int BOARD_X_SIZE = 10;
    static final int STARTY = 0;
    static final int STARTX = 4;
    public int currentY;
    public int currentX;
    Tetromino currentPiece;
    int[][] currentPieceMatrix;
    boolean isGameOver = false;

    final Tetromino[][] board =
            new Tetromino[BOARD_Y_SIZE][BOARD_X_SIZE];

    Board() {
        boardInit();
        newPieceSpawner();

    }

    public Tetromino getBoardElement(int y, int x) {
        return board[y][x];
    }

    public void setBoardElement(int y, int x, Tetromino tetr) {
        board[y][x] = tetr;
    }

    public void boardInit() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = Tetromino.EMPTY;
            }
        }
    }


    public void printBoardOnConsole() {

        for (int i = 0; i < BOARD_Y_SIZE; i++) {
            for (int j = 0; j < BOARD_X_SIZE; j++) {
                int isPointerOnCurrentPiece_Y = i - currentY;
                int isPointerOnCurrentPiece_X = j - currentX;
                int currentPieceMatrixHeight = currentPieceMatrix.length;
                int currentPieceMatrixWidth = currentPieceMatrix[0].length;
                if (
                        (isPointerOnCurrentPiece_Y >= 0 && isPointerOnCurrentPiece_Y < currentPieceMatrixHeight)
                                && (isPointerOnCurrentPiece_X >= 0 && isPointerOnCurrentPiece_X < currentPieceMatrixWidth)

                ) {

                    int currentPieceMatrixCurrentElement =
                            currentPieceMatrix[isPointerOnCurrentPiece_Y][isPointerOnCurrentPiece_X];
                    if (currentPieceMatrixCurrentElement == 1) {
                        System.out.print("X" + " ");
                    } else if (currentPieceMatrixCurrentElement == 0) {
                        System.out.print(". ");
                    }

                } else {
                    System.out.print(getBoardElement(i, j).getTetrominoSymbol() + " ");

                }
            }
            System.out.println();
        }
    }

    private void selectCurrentPieceRandomly() {
        Random rnd = new Random();
        Tetromino[] randomPiece = Tetromino.values();
        Tetromino[] pieces = Tetromino.values();
        currentPiece = pieces[rnd.nextInt(pieces.length - 1)];

        currentPieceMatrix = currentPiece.getShapeMatrix();

    }


    public record currentPieceYX(int Y, int X) {
    }

    public currentPieceYX getCurrentPieceYX() {
        return new currentPieceYX(currentY, currentX);
    }

    public void setCurrentPieceMatrix(int[][] currentPieceMatrix) {
        this.currentPieceMatrix = currentPieceMatrix;
    }

    /**
     * For downward movement
     *
     * @param Y Increment Y value
     */
    public void changeCurrentPieceY(int Y) {
        currentY += Y;
    }

    public void changeCurrentPieceX(int X) {
        currentX += X;
    }

    public void movePieceDown() {
        if (!pieceReachedBottomOrOtherPiece()) {
            changeCurrentPieceY(1);
        } else {
            lockPieceToBoard();
            newPieceSpawner();
        }
    }


    private boolean pieceCanBeMovedLeft() {
        for (int i = 0; i < currentPieceMatrix.length; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                if (currentPieceMatrix[i][j] == 1) {
                    int targetX = currentX + j - 1;
                    if (targetX == -1) {
                        return false;
                    }
                    if (!getBoardElement(currentY + i, targetX).name().equals("EMPTY")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean pieceCanBeMovedRight() {
        for (int i = 0; i < currentPieceMatrix.length; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                if (currentPieceMatrix[i][j] == 1) {
                    int targetX = currentX + j + 1;
                    if (targetX == BOARD_X_SIZE) {
                        return false;
                    }
                    if (!getBoardElement(currentY + i, targetX).name().equals("EMPTY")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean pieceCanBeRotated(int @NotNull [][] rotatedPiece) {

        for (int i = 0; i < rotatedPiece.length; i++) {
            for (int j = 0; j < rotatedPiece[0].length; j++) {
                int targetCoordinateY = currentY + i;
                int targetCoordinateX = currentX + j;
                if ((
                        rotatedPiece[i][j] == 1 &&
                                (((targetCoordinateY == BOARD_Y_SIZE) || (targetCoordinateY  < 0))
                                        || ((targetCoordinateX == BOARD_X_SIZE) || (targetCoordinateX  < 0)))
                )) {
                    return false;
                }
                if (rotatedPiece[i][j] == 1 &&
                        !getBoardElement(currentY + i, currentX + j).name().equals("EMPTY")) {
                    return false;
                }

            }
        }

        return true;
    }

    public void movePieceLeft() {
        if (!pieceReachedBottomOrOtherPiece()
                && pieceCanBeMovedLeft()) {
            changeCurrentPieceX(-1);
        }
    }

    public void movePieceRight() {
        if (!pieceReachedBottomOrOtherPiece()
                && pieceCanBeMovedRight()) {
            changeCurrentPieceX(+1);
        }
    }


    public void rotatePiece() {
        int[][] rotatedPiece = new int[currentPieceMatrix[0].length][currentPieceMatrix.length];
        for (int i = 0; i < currentPieceMatrix.length; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                rotatedPiece[j][i] = currentPieceMatrix[i][j];
            }
        }

        for (int i = 0; i < rotatedPiece.length; i++) {
            int left = 0;
            int right = rotatedPiece[i].length - 1;

            while (left < right) {

                int temp = rotatedPiece[i][left];
                rotatedPiece[i][left] = rotatedPiece[i][right];
                rotatedPiece[i][right] = temp;


                left++;
                right--;
            }
        }

        if (pieceCanBeRotated(rotatedPiece)) {

            setCurrentPieceMatrix(rotatedPiece);
        }
    }


    private boolean pieceReachedBottomOrOtherPiece() {
        int matrixSize = currentPieceMatrix.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                if (currentPieceMatrix[i][j] == 1) {
                    if (currentY + i >= BOARD_Y_SIZE - 1
                            || !getBoardElement(currentY + i + 1, currentX + j).name().equals("EMPTY")
                    ) {
                        return true;
                    }
                }
            }
        }
        return false;


    }

    private void lockPieceToBoard() {
        for (int i = 0; i < currentPieceMatrix.length; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                if (currentPieceMatrix[i][j] == 1) {

                    setBoardElement(currentY + i, currentX + j, currentPiece);
                }
            }
        }

    }

    private void newPieceSpawner() {

        selectCurrentPieceRandomly();
        currentY = STARTY;
        currentX = STARTX;
        isGameOver();


    }

    private void isGameOver(){
        for (int i = 0; i < currentPieceMatrix.length; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                if (currentPieceMatrix[i][j] == 1
                && !getBoardElement(i+currentY, j+currentX ).name().equals("EMPTY")) {
                    isGameOver = true;
                }
            }
        }

    }
}

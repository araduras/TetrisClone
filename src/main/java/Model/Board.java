package Model;

import Util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static Model.Sizes.BOARD_X_SIZE;
import static Model.Sizes.BOARD_Y_SIZE;


public class Board {

    public static final int STARTY = 0;
    public static final int STARTX = 4;
    int rowsCleared = 0;
    int level = 0;
    private int rotationState = 2;
    public int currentY;
    public int currentX;
    public Tetromino currentPiece;
    public int[][] currentPieceMatrix;
    public boolean isGameOver = false;
    boolean currentPieceHoldable = true;
    boolean isFirstPieceOfTheGame;
    public ArrayList<Tetromino> holdPieceList = new ArrayList<>();
    ArrayList<Tetromino> randomPieces = Arrays.stream(Tetromino.values())
            .filter(piece -> !piece.equals(Tetromino.EMPTY))
            .collect(Collectors.toCollection(ArrayList::new));
    public ArrayList<Tetromino> upComingPieces = new ArrayList<>();
    final Tetromino[][] board =
            new Tetromino[BOARD_Y_SIZE][BOARD_X_SIZE];

    public Board() {
        boardInit();
        newPieceSpawnLoop();
    }

    public Util.BoardSize getBoardSize() {
        return new Util.BoardSize(BOARD_Y_SIZE, BOARD_X_SIZE);
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

    private void randomPiecesListHandler() {
        if (upComingPieces.size() <= 5) {
            for (int i = 0; i < 2; i++) {
                Collections.shuffle(randomPieces);
                upComingPieces.addAll(randomPieces);
            }
        }
        currentPiece = upComingPieces.getFirst();
        upComingPieces.remove(upComingPieces.getFirst());
    }

    public void changeCurrentPieceY(int Y) {
        currentY += Y;
    }

    public void changeCurrentPieceX(int X) {
        currentX += X;
    }

    private boolean isRowFull(int row) {
        for (int i = 0; i < board[0].length; i++) {
            if (getBoardElement(row, i).name().equals("EMPTY")) {
                return false;
            }
        }
        return true;
    }

    private boolean isElementOnBoardEmpty(int y, int x) {
        return getBoardElement(y, x).name().equals("EMPTY");
    }

    public void rowClear() {
        for (int i = board.length - 1; i >= 0; i--) {
            if (isRowFull(i)) {
                for (int j = 0; j < board[0].length; j++) {
                    setBoardElement(i, j, Tetromino.EMPTY);
                }
                for (int row = i - 1; row >= 0; row--) {
                    for (int j = 0; j < board[0].length; j++) {
                        if (!getBoardElement(row, j).name().equals("EMPTY")) {
                            setBoardElement(row + 1, j, getBoardElement(row, j));
                            setBoardElement(row, j, Tetromino.EMPTY);
                        }
                    }
                }
                rowsCleared++;
                i++;
            }
        }
        if (rowsCleared > 0 && rowsCleared/10 > level ) {
            level++;
        }
    }

    public void movePieceDown() {
        if (!pieceReachedBottomOrOtherPiece()) {
            changeCurrentPieceY(1);
        } else {
            lockPieceToBoard();
            rowClear();
            currentPieceHoldable = true;
            newPieceSpawnLoop();
        }
    }

    private boolean pieceCanBeMovedLeft(int @NotNull [][] pieceToBeMovedLeft) {
        for (int i = 0; i < pieceToBeMovedLeft.length; i++) {
            for (int j = 0; j < pieceToBeMovedLeft[0].length; j++) {
                if (pieceToBeMovedLeft[i][j] == 1) {
                    int targetX = currentX + j - 1;
                    if ((targetX < 0)
                            || targetX >= BOARD_X_SIZE
                            || !getBoardElement(currentY + i, targetX).name().equals("EMPTY")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean pieceCanBeMovedRight(int @NotNull [][] pieceToBeMovedRight) {
        for (int i = 0; i < pieceToBeMovedRight.length; i++) {
            for (int j = 0; j < pieceToBeMovedRight[0].length; j++) {
                if (pieceToBeMovedRight[i][j] == 1) {
                    int targetX = currentX + j + 1;
                    if (targetX == BOARD_X_SIZE) {
                        return false;
                    }
                    if ((targetX >= BOARD_X_SIZE)
                            || targetX < 0
                            ||!getBoardElement(currentY + i, targetX).name().equals("EMPTY")) {
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
                                (((targetCoordinateY >= BOARD_Y_SIZE) || (targetCoordinateY < 0))
                                        || ((targetCoordinateX >= BOARD_X_SIZE) || (targetCoordinateX < 0)))
                )) {
                    return false;
                }

                if (rotatedPiece[i][j] == 1 &&
                        !isElementOnBoardEmpty(targetCoordinateY, targetCoordinateX)) {
                    return false;
                }

            }
        }

        return true;
    }

    public void DEFAULT_MOVE_PIECE_LEFT() {
        if (!pieceReachedBottomOrOtherPiece()
                && pieceCanBeMovedLeft(currentPieceMatrix)) {
            changeCurrentPieceX(-1);

        }
    }

    public void DEFAULT_MOVE_PIECE_RIGHT() {
        if (!pieceReachedBottomOrOtherPiece()
                && pieceCanBeMovedRight(currentPieceMatrix)) {
            changeCurrentPieceX(+1);
        }
    }

    public void hardDrop() {
        while (!pieceReachedBottomOrOtherPiece()) {
            changeCurrentPieceY(1);
        }
        lockPieceToBoard();
        rowClear();
        currentPieceHoldable = true;
        newPieceSpawnLoop();
    }


    public boolean rotatePiece() {
        int nextState = (rotationState + 1) % 4;
        if (pieceCanBeRotated(currentPiece.getShapeMatrix(nextState))) {
            this.currentPieceMatrix = currentPiece.getShapeMatrix(nextState);
            rotationState = nextState;
            return true;
        }

        if (pieceCanBeMovedLeft(currentPiece.getShapeMatrix(nextState))
                && pieceCanBeRotatedWithLeftShifting(currentPiece.getShapeMatrix(nextState))) {
            rotatePieceWithLeftShifting(nextState);
            return true;

        } else if (pieceCanBeMovedRight(currentPiece.getShapeMatrix(nextState))
                && pieceCanBeRotatedWithRightShifting(currentPiece.getShapeMatrix(nextState))) {
            rotatePieceWithRightShifting(nextState);
            return true;
        } else return false;
    }

    private void rotatePieceWithLeftShifting(int nextState) {
        currentX -= 1;
        this.currentPieceMatrix = currentPiece.getShapeMatrix(nextState);
        rotationState = nextState;

    }

    private void rotatePieceWithRightShifting(int nextState) {
        currentX += 1;
        this.currentPieceMatrix = currentPiece.getShapeMatrix(nextState);
        rotationState = nextState;

    }

    private boolean pieceCanBeRotatedWithLeftShifting(int @NotNull [][] rotatedPiece) {
        int leftShifting = -1;
        for (int i = 0; i < rotatedPiece.length; i++) {
            for (int j = 0; j < rotatedPiece[0].length; j++) {
                int targetCoordinateY = currentY + i;
                int targetCoordinateX = currentX + leftShifting + j;
                if ((
                        rotatedPiece[i][j] == 1 &&
                                (((targetCoordinateY >= BOARD_Y_SIZE) || (targetCoordinateY < 0))
                                        || ((targetCoordinateX >= BOARD_X_SIZE) || (targetCoordinateX < 0)))
                )) {
                    return false;
                }

                if (rotatedPiece[i][j] == 1 &&
                        !isElementOnBoardEmpty(targetCoordinateY, targetCoordinateX)) {
                    return false;
                }

            }
        }

        return true;
    }

    private boolean pieceCanBeRotatedWithRightShifting(int @NotNull [][] rotatedPiece) {
        int rightShifting = 1;
        for (int i = 0; i < rotatedPiece.length; i++) {
            for (int j = 0; j < rotatedPiece[0].length; j++) {
                int targetCoordinateY = currentY + i;
                int targetCoordinateX = currentX + rightShifting + j;
                if ((
                        rotatedPiece[i][j] == 1 &&
                                (((targetCoordinateY >= BOARD_Y_SIZE) || (targetCoordinateY < 0))
                                        || ((targetCoordinateX >= BOARD_X_SIZE) || (targetCoordinateX < 0)))
                )) {
                    return false;
                }

                if (rotatedPiece[i][j] == 1 &&
                        !isElementOnBoardEmpty(targetCoordinateY, targetCoordinateX)) {
                    return false;
                }

            }
        }

        return true;
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

    public boolean holdPiece() {
        if (currentPieceHoldable) {
            if (holdPieceList.isEmpty()) {
                holdPieceList.add(currentPiece);
                currentPieceHoldable = false;
                newPieceSpawnLoop();
                return true;
            } else {
                holdPieceList.add(currentPiece);
                currentPieceHoldable = false;
                newPieceSpawnLoop(holdPieceList.getFirst());
                holdPieceList.remove(holdPieceList.getFirst());
                return true;
            }
        } else return false;
    }

    private void newPieceSpawnLoop() {
        randomPiecesListHandler();
        currentPieceMatrix = currentPiece.getShapeMatrix(0);
        rotationState = 0;
        currentY = STARTY;
        currentX = STARTX;
        isGameOver();

    }

    private void newPieceSpawnLoop(Tetromino piece) {
        currentPiece = piece;
        currentPieceMatrix = currentPiece.getShapeMatrix(0);
        rotationState = 0;
        currentY = STARTY;
        currentX = STARTX;
        isGameOver();
    }

    private void isGameOver() {
        for (int i = 0; i < currentPieceMatrix.length; i++) {
            for (int j = 0; j < currentPieceMatrix[0].length; j++) {
                if (currentPieceMatrix[i][j] == 1
                        && !getBoardElement(i + currentY, j + currentX).name().equals("EMPTY")) {
                    isGameOver = true;
                }
            }
        }
    }


    public void boardClear() {
        isGameOver = false;
        isFirstPieceOfTheGame = true;
        boardInit();
        newPieceSpawnLoop();
    }

    public int getLevel() {
        return level;
    }
}

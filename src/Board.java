import java.util.Random;

public class Board {
    static final int BOARD_Y_SIZE = 10;
    static final int BOARD_X_SIZE = 10;
    static final int STARTY = 0;
    static final int STARTX = 4;
    int currentY;
    int currentX;
    Tetromino currentPiece;

    static final Tetromino [][] board =
            new Tetromino[BOARD_Y_SIZE][BOARD_X_SIZE];
    Board(){
        boardInit();
        selectCurrentPieceRandomly();
        currentY = STARTY;
        currentX = STARTX;




    }

    public Tetromino boardElementGetter(int y, int x){
        return board[y][x];
    }
    public void boardElementSetter(int y, int x, Tetromino tetr){
         board[y][x] = tetr;
    }
    private static void boardInit(){
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = Tetromino.EMPTY;
            }
        }
    }
    //prints rows by columns
    // 1  2  3
    //x1 y1 z1
    //x2 y2 z2
    //x3 y3 z3

    public void printBoardOnConsole(){
        for (int i = 0; i < BOARD_Y_SIZE; i++) {
            for (int j = 0; j < BOARD_X_SIZE; j++) {
                int isPointerOnCurrentPiece_Y= i - currentY;
                int isPointerOnCurrentPiece_X= j - currentX;
                int currentPieceMatrixHeight = currentPiece.getShapeMatrix().length;
                int currentPieceMatrixWidth = currentPiece.getShapeMatrix()[0].length;
                if (
                        (isPointerOnCurrentPiece_Y>=0 && isPointerOnCurrentPiece_Y<currentPieceMatrixHeight)
                    &&  (isPointerOnCurrentPiece_X>=0 && isPointerOnCurrentPiece_X<currentPieceMatrixWidth)

                )
                {
                    // 0 or 1
                    int currentPieceMatrixCurrentElement =
                            currentPiece.getShapeMatrix()[isPointerOnCurrentPiece_Y][isPointerOnCurrentPiece_X];
                    if (currentPieceMatrixCurrentElement == 1) {
                        System.out.print("X"+" ");
                    }
                    else if ( currentPieceMatrixCurrentElement == 0){
                        System.out.print(". ");
                    }

                }else{
                    System.out.print(boardElementGetter(i,j).getTetrominoSymbol()+ " ");

            }}
            System.out.println();
        }
    }
    private void selectCurrentPieceRandomly(){
        Random rnd = new Random();
        Tetromino[] randomPiece = Tetromino.values();
        currentPiece = randomPiece[rnd.nextInt(1,randomPiece.length)-1];
    }


}

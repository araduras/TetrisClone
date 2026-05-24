public class Board {
    static final int BOARD_Y_SIZE = 10;
    static final int BOARD_X_SIZE = 10;
    static final Tetromino [][] board =
            new Tetromino[BOARD_Y_SIZE][BOARD_X_SIZE];
    Board(){
        boardInit();
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
    public void printBoardOnConsole(){
        for (int i = 0; i < BOARD_Y_SIZE; i++) {
            for (int j = 0; j < BOARD_X_SIZE; j++) {
                System.out.print(boardElementGetter(i,j).getTetrominoSymbol()+" ");
            }
            System.out.println();
        }
    }



}

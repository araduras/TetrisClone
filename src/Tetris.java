

public class Tetris  {

public static void main(String[] args) {
        Board board = new Board();

        try{
            while(true){

            board.movePieceDown();
            board.printBoardOnConsole();
            System.out.println();
            Thread.sleep(1000);

        }
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }



    }
}
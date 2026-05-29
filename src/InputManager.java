import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
public class InputManager implements NativeKeyListener {

    private Board board;
    InputManager(Board board){
        this.board = board;
    }
    public void startListening(){
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        }
        catch (NativeHookException nativeHookException){

        }
    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_LEFT){
                board.movePieceLeft();
                board.printBoardOnConsole();

            } else if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_RIGHT) {
                board.movePieceRight();
                board.printBoardOnConsole();

            } else if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_DOWN) {
                board.movePieceDown();
                board.printBoardOnConsole();

            }


    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}

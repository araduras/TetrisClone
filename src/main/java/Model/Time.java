package Model;

import static Controller.GameController.CURRENT_GAME_SPEED;


public class Time {

    public static long oneSecond = 1_000_000_000L;
    public static long halfSecond = 500_000_000L;
    public static final long DEFAULT_GAME_SPEED = oneSecond;


    public long getCurrentGameSpeed() {
        return CURRENT_GAME_SPEED;
    }
    public void setGameSpeed(long speed) {
        CURRENT_GAME_SPEED = speed;
    }
}

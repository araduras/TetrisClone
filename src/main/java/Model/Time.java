package Model;

public class Time {
    //(0.8 − (level − 1) × 0.007) ^ (level − 1)
    public long gameSpeed(int level){
        return (long) (Math.pow((0.8 - (level - 1) * 0.007), (level - 1)) * 1_000_000_000L);
    }

    public static long oneSecond = 1_000_000_000L;
    public static long halfSecond = 500_000_000L;
    public long DEFAULT_GAME_SPEED = oneSecond;



}

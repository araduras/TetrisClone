package Model;

public class Time {
    public static long oneSecond = 1_000_000_000L;
    public static long halfSecond = 500_000_000L;
    private final long DEFAULT_LOCK_DELAY = 500_000_000L;


    public long gameSpeed(int level){
        return (long) (Math.pow((0.8 - (level - 1) * 0.007), (level - 1)) * 1_000_000_000L);
    }
    public long getLockDelay(int level){
        if (level<=19){
            return DEFAULT_LOCK_DELAY;
        } else if (level<=25) {
            return DEFAULT_LOCK_DELAY - 50_000_000L * level;
        } else if (level<=30) {
            return 200_000_000L - 10_000_000L * level;
        }
        else return 150_000_000L;
    }



}

package Model;

public class Score {
    int levelMultiplier = 1;
    int currentScore = 0;
    final int doubleRowClearMultiplier = 4;
    final int tripleRowClearMultiplier = 16;
    final int quadRowClearMultiplier = 64;


    private int getLevelMultiplierForRowsCleared(int rowsCleared) {
        switch (rowsCleared) {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return doubleRowClearMultiplier;
            }
            case 3 -> {
                return tripleRowClearMultiplier;
            }
            case 4 -> {
                return quadRowClearMultiplier;
            }
        }
        return 0;
    }

    public int getScoreForHardDrop(int rowsTraveled, int currentLevel) {
        if (currentLevel == 0) {
            return rowsTraveled * 10;
        } else return rowsTraveled * 10 * currentLevel;
    }

    public int getScoreForDownwardMove(int downwardMoveCount, int currentLevel) {
        if (currentLevel == 0) {
            return downwardMoveCount * 10;
        } else return downwardMoveCount * 10 * currentLevel;
    }

    public int getScoreForRowClear(int rowsCleared, int currentLevel) {
        if (currentLevel == 0) {
            return getLevelMultiplierForRowsCleared(rowsCleared) * 1000;
        } else return getLevelMultiplierForRowsCleared(rowsCleared) * 1000 * currentLevel;
    }

}

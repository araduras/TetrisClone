public enum Tetromino {
    BLOCK(new int[][]
            {
                    {1, 1},
                    {1, 1},
            }, "X"
    ),

    L_SHAPE(new int[][]
            {
                    {1, 0, 0},
                    {1, 0, 0},
                    {1, 1, 0}
            }, "X"
    ),
    REVERSE_L_SHAPE(new int[][]
            {
                    {0, 1, 0},
                    {0, 1, 0},
                    {1, 1, 0}
            }, "X"
    ),
    LEFT_ZIGZAG(new int[][]
            {
                    {1, 1, 0},
                    {0, 1, 1},
                    {0, 0, 0}
            }, "X"
    ),
    RIGHT_ZIGZAG(new int[][]
            {
                    {0, 1, 1},
                    {1, 1, 0},
                    {0, 0, 0}
            }, "X"
    ),
    T_SHAPE(new int[][]
            {
                    {1, 1, 1},
                    {0, 1, 0},
                    {0, 0, 0}
            }, "X"
    ),
    I_SHAPE(new int[][]
            {
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0}
            }, "X"
    ),
    EMPTY(new int[][]{
            {0}
    }, "."
    );


    private int[][] shapeMatrix;
    private final String symbol;

    private Tetromino(int[][] shapeMatrix, String symbol) {
        this.shapeMatrix = shapeMatrix;
        this.symbol = symbol;
    }

    public String getTetrominoSymbol() {
        return this.symbol;
    }
    public int[][] getShapeMatrix() {
        return this.shapeMatrix;
    }
    public void setShapeMatrix(int[][] shapeMatrix){
        this.shapeMatrix = shapeMatrix;
    }

}

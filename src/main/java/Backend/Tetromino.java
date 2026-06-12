package Backend;

public enum Tetromino {
    BLOCK(new int[][]
            {
                    {1, 1},
                    {1, 1},
            }, "X",
            Style.COLOR_BLOCK
    ),

    L_SHAPE(new int[][]
            {
                    {1, 0, 0},
                    {1, 0, 0},
                    {1, 1, 0}
            }, "X",
            Style.COLOR_L_SHAPE
    ),
    REVERSE_L_SHAPE(new int[][]
            {
                    {0, 1, 0},
                    {0, 1, 0},
                    {1, 1, 0}
            }, "X",
            Style.COLOR_REVERSE_L_SHAPE
    ),
    LEFT_ZIGZAG(new int[][]
            {
                    {1, 1, 0},
                    {0, 1, 1},
                    {0, 0, 0}
            }, "X",
            Style.COLOR_LEFT_ZIGZAG
    ),
    RIGHT_ZIGZAG(new int[][]
            {
                    {0, 1, 1},
                    {1, 1, 0},
                    {0, 0, 0}
            }, "X",
            Style.COLOR_RIGHT_ZIGZAG
    ),
    T_SHAPE(new int[][]
            {
                    {1, 1, 1},
                    {0, 1, 0},
                    {0, 0, 0}
            }, "X",
            Style.COLOR_T_SHAPE
    ),
    I_SHAPE(new int[][]
            {
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0}
            }, "X",
            Style.COLOR_I_SHAPE
    ),
    EMPTY(new int[][]{
            {0}
    }, ".",
            Style.COLOR_EMPTY
    );


    private int[][] shapeMatrix;
    private final String symbol;
    private final String colorStyle;
    private Tetromino(int[][] shapeMatrix, String symbol, String colorStyle) {
        this.shapeMatrix = shapeMatrix;
        this.symbol = symbol;
        this.colorStyle = colorStyle;
    }

    public String getTetrominoSymbol() {
        return this.symbol;
    }
    public int[][] getShapeMatrix() {
        return this.shapeMatrix;
    }
    public String getStyle(){return "-fx-fill: " + this.colorStyle + "; " + Style.DEFAULT_BORDER_STYLE;}
    public void setShapeMatrix(int[][] shapeMatrix){
        this.shapeMatrix = shapeMatrix;
    }

}

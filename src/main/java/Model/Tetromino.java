package Model;

import View.Style;

public enum Tetromino {
    BLOCK(new int[][][]
            {
                    {
                            {1, 1},
                            {1, 1},
                    },
                    {
                            {1, 1},
                            {1, 1},
                    },
                    {
                            {1, 1},
                            {1, 1},
                    },
                    {
                            {1, 1},
                            {1, 1},
                    },

            },
            Style.COLOR_BLOCK
    ),

    L_SHAPE(new int[][][]
            {
                    {
                            {0, 0, 1},
                            {1, 1, 1},
                            {0, 0, 0}
                    },
                    {
                            {0, 1, 0},
                            {0, 1, 0},
                            {0, 1, 1}
                    },
                    {
                            {0, 0, 0},
                            {1, 1, 1},
                            {1, 0, 0}
                    },
                    {
                            {1, 1, 0},
                            {0, 1, 0},
                            {0, 1, 0}
                    },

            },


            Style.COLOR_L_SHAPE
    ),
    REVERSE_L_SHAPE(new int[][][]
            {
                    {
                    {1, 0, 0},
                    {1, 1, 1},
                    {0, 0, 0}
            },
                    {
                            {0, 1, 1},
                            {0, 1, 0},
                            {0, 1, 0}
                    },
                    {
                            {0, 0, 0},
                            {1, 1, 1},
                            {0, 0, 1}
                    },

                    {
                            {0, 1, 0},
                            {0, 1, 0},
                            {1, 1, 0}
                    }
            },
            Style.COLOR_REVERSE_L_SHAPE
    ),
    LEFT_ZIGZAG(new int[][][]
            {
                    {
                            {1, 1, 0},
                            {0, 1, 1},
                            {0, 0, 0}
                    },
                    {
                            {0, 0, 1},
                            {0, 1, 1},
                            {0, 1, 0}
                    },
                    {
                            {1, 1, 0},
                            {0, 1, 1},
                            {0, 0, 0}
                    },
                    {
                            {0, 0, 1},
                            {0, 1, 1},
                            {0, 1, 0}
                    }

            },
            Style.COLOR_LEFT_ZIGZAG
    ),
    RIGHT_ZIGZAG(new int[][][]
            {
                    {
                            {0, 1, 1},
                            {1, 1, 0},
                            {0, 0, 0}
                    },

                    {
                            {0, 1, 0},
                            {0, 1, 1},
                            {0, 0, 1}
                    },
                    {
                            {0, 1, 1},
                            {1, 1, 0},
                            {0, 0, 0}
                    },

                    {
                            {0, 1, 0},
                            {0, 1, 1},
                            {0, 0, 1}
                    }


            },
            Style.COLOR_RIGHT_ZIGZAG
    ),
    T_SHAPE(new int[][][]
            {
                    {
                            {0, 1, 0},
                            {1, 1, 1},
                            {0, 0, 0}
                    },
                    {
                            {0, 1, 0},
                            {0, 1, 1},
                            {0, 1, 0}
                    },
                    {
                            {0, 0, 0},
                            {1, 1, 1},
                            {0, 1, 0}
                    },
                    {
                            {0, 1, 0},
                            {1, 1, 0},
                            {0, 1, 0}
                    },



            },
            Style.COLOR_T_SHAPE
    ),
    I_SHAPE(new int[][][]
            {
                    {
                            {0, 0, 0, 0},
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                    },
                    {
                            {0, 0, 1, 0},
                            {0, 0, 1, 0},
                            {0, 0, 1, 0},
                            {0, 0, 1, 0}
                    },

                    {
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {1, 1, 1, 1},
                            {0, 0, 0, 0}
                    },
                    {
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0}
                    }
            },
            Style.COLOR_I_SHAPE
    ),
    EMPTY(new int[][][]{
            {{0}}
    },
            Style.COLOR_EMPTY
    );


    private final int[][][] shapeMatrix;
    private final String colorStyle;


    private Tetromino(int[][][] shapeMatrix, String colorStyle) {
        this.shapeMatrix = shapeMatrix;
        this.colorStyle = colorStyle;

    }

    public int[][] getShapeMatrix(int rotationState) {
        return this.shapeMatrix[rotationState];
    }

    public String getStyle() {
        return "-fx-fill: " + this.colorStyle + "; " + Style.DEFAULT_BORDER_STYLE;
    }

}


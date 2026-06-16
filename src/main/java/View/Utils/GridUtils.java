package View.Utils;

import Model.Tetromino;
import View.Style;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class GridUtils {
    /**
     * Builds a visible grid to be displayed
     * @return Rectangle[][]
     */
    public static Rectangle[][] gridBuilder(GridPane gridPane, int gridHeightY, int gridWidthX, int gridRectangleHeight, int gridRectangleWidth, String gridStyle) {
        Rectangle[][] gridToBuild = new Rectangle[gridHeightY][gridWidthX];
        for (int i = 0; i < gridToBuild.length; i++) {
            for (int j = 0; j < gridToBuild[0].length; j++) {
                Rectangle baseGrid = new Rectangle(gridRectangleWidth, gridRectangleHeight);
                baseGrid.setStyle(gridStyle);
                gridPane.add(baseGrid, j, i);
                gridToBuild[i][j] = baseGrid;
            }
        }
        return gridToBuild;
    }

    public static int pieceOnGridRenderer(Rectangle[][] gridToDrawOn, int startY, int startX, Tetromino tetr) {
        for (int i = 0; i < tetr.getShapeMatrix()[0].length; i++) {
            for (int k = 0; k < tetr.getShapeMatrix()[0][0].length; k++) {
                if (tetr.getShapeMatrix()[0][i][k] == 1) {
                    gridToDrawOn[startY + i][startX + k].setStyle(tetr.getStyle());
                } else {
                    gridToDrawOn[startY + i][startX + k].setStyle(Style.baseGridStyleWithBorder);
                }
            }
        }
        return startY + tetr.getShapeMatrix()[0].length;
    }

    public static void gridClear(Rectangle[][] gridToClear) {
        for (int i = 0; i < gridToClear.length; i++) {
            for (int j = 0; j < gridToClear[0].length; j++) {
                gridToClear[i][j].setStyle(Style.baseGridStyleWithBorder);
            }
        }
    }
}

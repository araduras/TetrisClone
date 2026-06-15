package View;

public class Style {
    //Shapes
    public static final String COLOR_BLOCK = "#ffdf00"; // Yellow
    public static final String COLOR_EMPTY = "-fx-fill: #333333; "; // Grey
    public static final String COLOR_I_SHAPE = "#00FFFF"; //Cyan
    public static final String COLOR_T_SHAPE = "#800080"; //Purple
    public static final String COLOR_L_SHAPE = "#FF7F00"; //Orange
    public static final String COLOR_REVERSE_L_SHAPE = "#0000FF"; //Blue
    public static final String COLOR_LEFT_ZIGZAG = "#00FF00"; //Green
    public static final String COLOR_RIGHT_ZIGZAG = "#FF0000"; //Red
    public static final String COLOR_DEFAULT = "#f00000"; // Red

    public static final String BASE_GRID_STYLE = "-fx-fill: #333333; "; //Dark grey
    public static final String DEFAULT_BORDER_STYLE = "-fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-stroke-type: inside;";
    public static final String RETRO_BORDER_STYLE = "";
    public static final String DEFAULT_GRAY_COLOR = "-fx-background-color: #222222;";
    public static final String LARGE_TEXT_STYLE =

            "-fx-font-family: 'Comic Sans MS';" +
                    " -fx-font-size: 55px; " +
                    "-fx-background-color: #222222; " +
                    "-fx-text-fill: #ffffff;" +
                    "-fx-padding: 10px 20px 10px 20px;";

    public static final String DEFAULT_BUTTON_STYLE =
            "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #ffffff;" +
                    "-fx-background-color: #444444;" +
                    "-fx-background-radius: 5px;" +
                    "-fx-padding: 10px 20px 10px 20px;" +
                    "-fx-cursor: hand;";


    public static final String settingsMenuStyle =
            "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-color: #444444;" +
                    "-fx-cursor: hand;";

    public static String baseGridStyleWithBorder = BASE_GRID_STYLE + DEFAULT_BORDER_STYLE;
    public static String emptyGridCellStyle = BASE_GRID_STYLE + DEFAULT_BORDER_STYLE;

}

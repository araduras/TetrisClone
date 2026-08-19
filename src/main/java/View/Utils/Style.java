package View.Utils;

public class Style {
    //Defaults
    public static final String COLOR_BLOCK = "-fx-fill: rgba(255, 223, 0, 1.0);";       // Yellow
    public static final String COLOR_EMPTY = "-fx-fill: rgba(51, 51, 51, 1.0);";      // Grey
    public static final String COLOR_I_SHAPE = "-fx-fill: rgba(0, 255, 255, 1.0);";    // Cyan
    public static final String COLOR_T_SHAPE = "-fx-fill: rgba(128, 0, 128, 1.0);";   // Purple
    public static final String COLOR_L_SHAPE = "-fx-fill: rgba(255, 127, 0, 1.0);";   // Orange
    public static final String COLOR_REVERSE_L_SHAPE = "-fx-fill: rgba(0, 0, 255, 1.0);"; // Blue
    public static final String COLOR_LEFT_ZIGZAG = "-fx-fill: rgba(0, 255, 0, 1.0);";  // Green
    public static final String COLOR_RIGHT_ZIGZAG = "-fx-fill: rgba(255, 0, 0, 1.0);"; // Red
    public static final String COLOR_DEFAULT = "-fx-fill: rgba(240, 0, 0, 1.0);";
    public static final double DEFAULT_OPACITY = 0.5;
    // UI & Grid Styles
    public static final String BASE_GRID_STYLE = "-fx-fill: rgba(51, 51, 51, 1.0); "; // Dark grey
    public static final String DEFAULT_BORDER_STYLE = "-fx-stroke: rgba(0, 0, 0, 1.0); -fx-stroke-width: 0.5; -fx-stroke-type: inside;";
    public static final String GHOST_BORDER_STYLE = "-fx-stroke: rgba(0, 0, 0, 0.4); -fx-stroke-width: 0.5; -fx-stroke-type: inside;";
    public static final String RETRO_BORDER_STYLE = "";
    public static final String DEFAULT_GRAY_COLOR = "-fx-background-color: rgba(34, 34, 34, 1.0);";

    public static final String LARGE_TEXT_STYLE =
            "-fx-font-family: 'Comic Sans MS';" +
                    " -fx-font-size: 55px; " +
                    "-fx-background-color: rgba(34, 34, 34, 1.0); " +
                    "-fx-text-fill: rgba(255, 255, 255, 1.0);" +
                    "-fx-padding: 10px 20px 10px 20px;";

    public static final String DEFAULT_BUTTON_STYLE =
            "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: rgba(255, 255, 255, 1.0);" +
                    "-fx-background-color: rgba(68, 68, 68, 1.0);" +
                    "-fx-background-radius: 5px;" +
                    "-fx-padding: 10px 20px 10px 20px;" +
                    "-fx-cursor: hand;";

    public static final String settingsMenuStyle =
            "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-color: rgba(68, 68, 68, 1.0);" +
                    "-fx-cursor: hand;";

    public static final String gameOverMenuStyle =
            "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-color: rgba(68, 68, 68, 1.0);" +
                    "-fx-cursor: hand;";

    public static String baseGridStyleWithBorder = BASE_GRID_STYLE + DEFAULT_BORDER_STYLE;
    public static String emptyGridCellStyle = BASE_GRID_STYLE + DEFAULT_BORDER_STYLE;

    /**
     * Converts any standard "rgba(r, g, b, 1.0)" color string into a semi-transparent
     * version for rendering ghost pieces.
     *
     * @param rgbaColor The original full-opacity RGBA string.
     * @param Opacity Desired opacity (e.g., 0.35 for ghost blocks).
     * @return Translucent JavaFX CSS fill style.
     */
    public static String toGhostStyle(String rgbaColor, double Opacity) {
        if (rgbaColor == null || !rgbaColor.contains("1.0")) {
            return "-fx-fill: rgba(255, 255, 255, " + Opacity + "); " + GHOST_BORDER_STYLE;
        }
        String ghostFill = rgbaColor.replace("1.0", String.valueOf(Opacity));
        return ghostFill + "; " + GHOST_BORDER_STYLE;
    }
}
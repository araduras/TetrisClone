package Backend;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class Util {
    public static @NotNull Label getLoremIpsum() {
        Label loremIpsum = new Label(
                "Lorem ipsum dolor sit amet," +
                        " consectetur adipiscing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex " +
                        "ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
                        "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
                        "qui officia deserunt mollit anim id est laborum.");
        loremIpsum.setStyle(
                "-fx-font-family: 'Comic Sans MS';" +
                        " -fx-font-size: 55px; " +
                        "-fx-background-color: #222222; " +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-padding: 10px 20px 10px 20px;"
        );
        loremIpsum.setWrapText(true);
        return loremIpsum;
    }
}

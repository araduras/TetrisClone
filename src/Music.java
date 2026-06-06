import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {

    public MediaPlayer mediaPlayer;


    public void playSong(String filename) {
        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }


            String audioPath = getClass().getResource("/" + filename + ".mp3").toExternalForm();
            Media media = new Media(audioPath);

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            mediaPlayer.play();


        } catch (Exception e) {
            System.out.println("Could not load music file: " + filename);
            e.printStackTrace();
        }
    }
    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }
}
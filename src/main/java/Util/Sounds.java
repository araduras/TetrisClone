package Util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

public class Sounds {
    private final double DEFAULT_MUSIC_VOLUME = 0.1;
    private final double DEFAULT_SOUND_EFFECTS_VOLUME = 0.1;
    public final static String DEFAULT_THEME = "Tetris 99";
    private ErrorHandler errorHandler = new ErrorHandler();
    public MediaPlayer mediaPlayer;
    public static double musicVolume;
    public static double soundEffectsVolume;
    String rotateSoundEffectFilename;
    AudioClip soundEffect_Rotate;



    public Sounds(){
        setMusicVolume(DEFAULT_MUSIC_VOLUME);
        setSoundEffectsVolume(DEFAULT_SOUND_EFFECTS_VOLUME);
        rotateSoundEffectFilename = "vineboom.mp3";
        soundEffectsSetter();
    }

    private void soundEffectsSetter(){
        try {
            String audioPath = getClass().getResource("/" + rotateSoundEffectFilename).toExternalForm();
            soundEffect_Rotate = new AudioClip(audioPath);
            soundEffect_Rotate.setVolume(soundEffectsVolume);
        }
        catch (Exception e){
            System.out.println(errorHandler.ERROR_MESSAGE_FILE_READ_ERROR);
            e.printStackTrace();
        }
    }
    public void playSoundEffect_Rotate(){
        soundEffect_Rotate.setVolume(getSoundEffectsVolume());
        soundEffect_Rotate.play();
    }

    public void playMusic(String filename) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            String audioPath = getClass().getResource("/" + filename + ".mp3").toExternalForm();
            Media media = new Media(audioPath);

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(musicVolume);
            mediaPlayer.play();

        } catch (Exception e) {
            System.out.println(errorHandler.ERROR_MESSAGE_FILE_READ_ERROR + filename);
            e.printStackTrace();
        }
    }


    public void setMusicVolume(double volume){
        musicVolume = volume;
    }
    public double getMusicVolume(){
        return musicVolume;
    }

    public double getSoundEffectsVolume(){
        return soundEffectsVolume;
    }
    public void setSoundEffectsVolume(double volume){
        soundEffectsVolume = volume;
    }

    public void pauseMusic(){
        this.mediaPlayer.pause();
    }
    public void resumeMusic(){
        this.mediaPlayer.setVolume(getMusicVolume());
        this.mediaPlayer.play();
    }
}
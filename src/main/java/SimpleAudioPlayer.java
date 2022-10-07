
// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

    public class SimpleAudioPlayer {

        // to store current position
        Long currentFrame;
        Clip clip;

        // current status of clip
        String status;

        AudioInputStream audioInputStream;
        static String filePath ="src/main/resources/audio/mixkit-short-laser-gun-shot-1670.wav";

        // constructor to initialize streams and clip
        public SimpleAudioPlayer()
                throws UnsupportedAudioFileException,
                IOException, LineUnavailableException
        {
            // create AudioInputStream object
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

            // create clip reference
            clip = AudioSystem.getClip();

            // open audioInputStream to the clip
            clip.open(audioInputStream);

            // clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void play() {
            //start the clip
            clip.start();
           // status = "play";
        }
        // Method to stop the audio
        public void stop() throws UnsupportedAudioFileException,
                IOException, LineUnavailableException
        {
            currentFrame = 0L;
            clip.stop();
            clip.close();
        }
        // Method to reset audio stream
        public void reset() throws UnsupportedAudioFileException, IOException,
                LineUnavailableException
        {
            audioInputStream = AudioSystem.getAudioInputStream(
                    new File(filePath).getAbsoluteFile());
            clip.open(audioInputStream);
            //clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        // Method to restart the audio
        public void restart() throws IOException, LineUnavailableException,
                UnsupportedAudioFileException
        {
            clip.stop();
            clip.close();
            reset();
            currentFrame = 0L;
            clip.setMicrosecondPosition(0);
            this.play();
        }
    }

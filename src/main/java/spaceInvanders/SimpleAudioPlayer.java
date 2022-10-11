package spaceInvanders;
// Java program to play an Audio
// file using Clip Object

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SimpleAudioPlayer {

    // to store current position
    Long currentFrame;
    Clip clip, clip2;

    // current status of clip
    String status;

    AudioInputStream audioInputStream;
    AudioInputStream audioInputStream2;
    static String filePathSpaceShipBullet = "src/main/resources/audio/mixkit-short-laser-gun-shot-1670.wav";
    static String filePathGameBackgroundMusic = "src/main/resources/audio/John Williams - Battle of the Heroes (audio) (online-audio-converter.com).wav";

    // constructor to initialize streams and clip
    public SimpleAudioPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePathSpaceShipBullet).getAbsoluteFile());
        audioInputStream2 = AudioSystem.getAudioInputStream(new File(filePathGameBackgroundMusic).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();
        clip2 = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip2.open(audioInputStream2);

        //clip2.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() {
        //start the clip
        clip.start();
        status = "play";
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        currentFrame = 0L;
        clip.stop();
        clip.close();
        status = "stop";
    }


    // Method to reset audio stream
    public void reset() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePathSpaceShipBullet).getAbsoluteFile());
        clip.open(audioInputStream);
    }

    // Method to restart the audio
    public void restart() {
        clip.stop();
        //clip.close();
        //reset();
        //currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
        status = "restart";
    }

    public void play2() {
        //start the clip
        clip2.start();
        status = "play";
        clip2.loop(Clip.LOOP_CONTINUOUSLY);
    }
}

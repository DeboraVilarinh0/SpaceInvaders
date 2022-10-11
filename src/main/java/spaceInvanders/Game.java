package spaceInvanders;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Game {

    TerminalScreen screen;
    private final Arena arena;
    boolean running = true;
    long shotTimer = 1000;
    long moveTimer = 100;
    long shotNumb = 1;
    long powerUpTimer = 5000;
    boolean playedLevelTwo = false;
    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();

    Game(int Width, int Height) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(true,
                AWTTerminalFontConfiguration.BoldMode.EVERYTHING, changeFont());
        TerminalSize terminalSize = new TerminalSize(Width, Height);
        DefaultTerminalFactory TerminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        TerminalFactory.setTerminalEmulatorFontConfiguration(cfg);
        Terminal terminal = TerminalFactory.createTerminal();

        arena = new Arena(Width, Height);

        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen();           // screens must be started
        screen.doResizeIfNecessary();   // resize screen if necessary
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        int FPS = 30;
        int frameTime = 1100 / FPS;
        long lastMonsterMovement = 0;
        long lastMonsterMovement2 = 0;
        long powerUpActivated = 0;
        long powerUp1Activated = 0;
        long powerUp2Activated = 0;
        long powerUp3Activated = 0;


        while (true) {
            long startTime = System.currentTimeMillis();
            long startTime2 = System.currentTimeMillis();
            long startTime3 = System.currentTimeMillis();
            long startTime4 = System.currentTimeMillis();
            long startTime5 = System.currentTimeMillis();
            long startTime6 = System.currentTimeMillis();
            long startTime7 = System.currentTimeMillis();
            audioPlayer.play2();
            draw();
            KeyStroke key = screen.pollInput();
            if (key != null) {

                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    screen.close();
                    running = false;
                }

                arena.processKey(key);
                if (key.getKeyType() == KeyType.EOF) {
                    break;
                }
            }

            if (startTime - lastMonsterMovement > moveTimer) {
                arena.moveMonsters();
                if (!arena.getIsInvincible()) {
                    arena.verifySpaceShipCollision();
                }
                arena.moveBullets();
                arena.verifyMonsterCollision();
                arena.verifyCollisionBetweenBullets();
                arena.verifyPowerUpCollision();
                arena.cleanBullet();
                draw();

                lastMonsterMovement = startTime;
            }

            if (startTime2 - lastMonsterMovement2 > shotTimer) {
                arena.shootBullet(shotNumb);
                lastMonsterMovement2 = startTime2;
            }

            if (startTime3 - powerUpActivated > powerUpTimer) {
                arena.CreatePowerUps();
                powerUpActivated = startTime3;

            }
            if (startTime4 - powerUp1Activated > powerUpTimer) {
                arena.setShootFaster(3);
                powerUp1Activated = startTime4;
                System.out.println("FAST SHOOTING OFF");
            }

            if (startTime5 - powerUp2Activated > powerUpTimer) {
                arena.setIsInvincible(false);
                powerUp2Activated = startTime5;
                System.out.println("INVENCIBILITY OFF");
            }

            if (startTime6 - powerUp3Activated > powerUpTimer) {
                arena.setFireMultipleBullets(false);
                powerUp3Activated = startTime6;
                System.out.println("MULTIPLE SHOTS OFF");
            }


            switch (arena.isMonsterEmpty()) {
                case 2 -> {
                    shotTimer = 300;
                    shotNumb = 3;
                    moveTimer = 60;
                    arena.CreateMonsters(25, 5);
                    System.out.println("ENTREI NO 2");
                    playedLevelTwo = true;
                }
                case 3 -> {
                    System.out.println("entrei no 3");
                    shotTimer = 10;
                    shotNumb = 5;
                    moveTimer = 30;
                    arena.CreateMonsters(30, 6);
                }
                case 4 -> {
                    System.out.println("GG");
                    System.exit(0);

                    arena.CreateMonsters(30, 6);
                }
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;
            if (sleepTime > 0) try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public Font changeFont() {
        File fontFile = new File("src/main/resources/fonts/Square-Regular.ttf");
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(font.getSize() * 30F);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        return font;
    }
}




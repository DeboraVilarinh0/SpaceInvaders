import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;

public class Game {

    TerminalScreen screen;
    private final Arena arena;
    boolean running = true;
    int shotTimer = 1000;
    int moveTimer = 100;
    int shotNumb = 1;
    boolean playedLevelTwo = false;
    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();


    Game(int Width, int Height) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        TerminalSize terminalSize = new TerminalSize(Width, Height);
        DefaultTerminalFactory TerminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
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

        int FPS = 20;
        int frameTime = 1000 / FPS;
        long lastMonsterMovement = 0;
        long lastMonsterMovement2 = 0;


        while (true) {
            long startTime = System.currentTimeMillis();
            long startTime2 = System.currentTimeMillis();
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
                arena.verifyBadGuysCollision();
                arena.moveBullets();
                arena.verifyBulletCollisionEnemy();
                arena.verifyCollisionBetweenBullets();
                arena.cleanBullet();
                draw();

                lastMonsterMovement = startTime;
            }

            if (startTime2 - lastMonsterMovement2 > shotTimer) {
                arena.shootBullet(shotNumb);
                draw();
                lastMonsterMovement2 = startTime2;
            }

            switch (arena.isBadGuysEmpty()){
                case 2:shotTimer = 300;
                    shotNumb = 3;
                    moveTimer = 60;
                    arena.setMonsters(arena.CreateMonsters(25, 5));
                    System.out.println("ENTREI NO 2");
                    playedLevelTwo = true;break;

                case 3:System.out.println("entrei no 3");
                    shotTimer = 10;
                    shotNumb = 5;
                    moveTimer = 30;
                    arena.setMonsters(arena.CreateMonsters(30, 6));break;
                case 4: System.out.println("GG");
                System.exit(0);
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;
            if (sleepTime > 0) try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
    }
}




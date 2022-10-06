import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {

    TerminalScreen screen;
    private final Arena arena;
    boolean running = true;

    Game(int Width, int Height) throws IOException {

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

    public void run() throws IOException {

        int FPS = 20;
        int frameTime = 1000 / FPS;
        long lastMonsterMovement = 0;

        while (true) {
            long startTime = System.currentTimeMillis();

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

            if (startTime - lastMonsterMovement > 100) {
                arena.moveBadGuys();
                arena.verifyBadGuysCollision();
                arena.moveBullets();
                arena.verifyBulletCollision();
                draw();
                lastMonsterMovement = startTime;
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


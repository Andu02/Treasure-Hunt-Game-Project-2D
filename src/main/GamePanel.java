package main;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 pixels tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 pixels tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxScreenRow;

    // TILES
    TileManager tileM = new TileManager(this);

    // CONTROLS
    KeyHandler keyH = new KeyHandler(this);

    // SOUND
    Sound music = new Sound();
    Sound se = new Sound();

    // TIME
    Thread gameThread;

    // COLLISION
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    // PLAYER
    public Player player = new Player(this, keyH);

    // FPS
    int FPS = 60;

    // OBJECTS
    public AssetManager assetManager = new AssetManager(this);
    public SuperObject[] obj = new SuperObject[10];

    // UI
    public UI ui = new UI(this);

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {

        assetManager.setObject();

        playMusic(0);

        gameState = playState;

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void stopGameThread() {

        gameThread = null;

    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        if(gameState == playState) {
            player.update();
        }
        if(gameState == pauseState){

        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();

        }
        // TILE
        tileM.draw(g2);

        // OBJECT
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        // DEBUG
        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.red);
            g2.drawString("Draw Time: " + passed, 10, 550);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();

    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

}

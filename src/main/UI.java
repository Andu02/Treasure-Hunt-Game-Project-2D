package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B, messageFont;
    BufferedImage keyImage, chestImage, bootsImage;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    boolean firstMessageOn = true;
    int firstMessageCounter = 0;

    public boolean gameFinished = false;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        messageFont = new Font("Arial", Font.ITALIC, 30);

        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

        OBJ_Chest chest = new OBJ_Chest(gp);
        chestImage = chest.image;

        OBJ_Boots boots = new OBJ_Boots(gp);
        bootsImage = boots.image;

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }


    public void draw(Graphics2D g2) {

        String text;
        int x;
        int y;

        if(firstMessageOn) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            text = "COLLECT 2 TREASURES TO WIN";
            x = getCenterTextX(text, g2);
            y = gp.screenHeight / 2 + gp.tileSize * 2;
            g2.drawString(text, x, y);

            firstMessageCounter++;

            if(firstMessageCounter > 300) {
                firstMessageOn = false;
            }

        }

        if(gameFinished) {

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            text = "YOU WON!";
            x = getCenterTextX(text, g2);
            y = gp.screenHeight/2 - (gp.tileSize*2);
            g2.drawString(text, x, y);

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            text = "You finished in " + decimalFormat.format(playTime) + " seconds!";
            x = getCenterTextX(text, g2);
            y = gp.screenHeight/2 - (gp.tileSize);
            g2.drawString(text, x, y);

            gp.stopGameThread();

        } else {
            if (gp.gameState == gp.pauseState) {

                g2.setFont(arial_80B);
                g2.setColor(Color.white);

                text = "PAUSED";
                x = getCenterTextX(text, g2);
                y = gp.screenHeight / 2 - (gp.tileSize);
                g2.drawString(text, x, y);
            }

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            // KEY UI
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString(" x " + gp.player.hasKey, 70, 58);

            // CHEST UI
            g2.drawImage(chestImage, gp.tileSize / 2, (gp.tileSize * 3) / 2 + 5, gp.tileSize, gp.tileSize, null);
            g2.drawString(" x " + gp.player.hasCoins, 70, 106);

            // BOOTS UI
            g2.drawImage(bootsImage, gp.tileSize / 2, (gp.tileSize * 5) / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString(" x " + gp.player.hasBoots, 70, 154);

            // TIME
            if(gp.gameState == gp.playState) {
                playTime += (double) 1 / 60;
            }
            g2.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 11 + 20, 65);
            
            // MESSAGE
            if (messageOn) {
                g2.setFont(messageFont);
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }

    public int getCenterTextX (String text, Graphics2D g2) {

        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return gp.screenWidth/2 - textLength/2;

    }

}

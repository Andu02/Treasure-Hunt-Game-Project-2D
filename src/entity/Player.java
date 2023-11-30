package entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtiliyTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // OBJECTS
    public int hasKey = 0;
    public int hasCoins = 0;
    public int hasBoots = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        collisionArea = new Rectangle(8, 16, 32, 32);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 28;
        worldY = gp.tileSize * 26;
        speed = 3;
        direction = "down";

    }

    public void getPlayerImage() {

            up1 = setup("player_up1");
            up2 = setup("player_up2");
            down1 = setup("player_down1");
            down2 = setup("player_down2");
            left1 = setup("player_left1");
            left2 = setup("player_left2");
            right1 = setup("player_right1");
            right2 = setup("player_right2");


    }

    public BufferedImage setup(String imageName) {
        UtiliyTool uTool = new UtiliyTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;

    }



    public void update() {

        if(keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if(!collisionOn) {
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject (int i) {
        if(i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(2);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(1);
                        gp.obj[i].collisionArea = new Rectangle(0, 0, 0, 0);
                        gp.obj[i].collision = false;
                        gp.ui.showMessage("You unlocked the door!");
                        try {
                            gp.obj[i].image = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/opened_door.png"))), gp.tileSize, gp.tileSize);
                        } catch (Exception e) {
                            e.printStackTrace();
                        };
                        hasKey--;
                    } else if (hasKey == 0) {
                        gp.ui.showMessage("Locked! You need a key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(3);
                    hasBoots++;
                    speed += 1;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got extra speed!");
                    break;
                case "Chest":
                    if(hasKey > 0) {
                        gp.playSE(4);
                        gp.obj[i].collisionArea = new Rectangle(0, 0, 0, 0);
                        hasCoins++;
                        hasKey--;
                        gp.ui.showMessage("You got a treasure!");
                        try {
                            gp.obj[i].image = uTool.scaleImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/opened_chest.png"))), gp.tileSize, gp.tileSize);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(hasCoins == 2) {
                            gp.ui.gameFinished = true;
                            gp.stopGameThread();
                            gp.stopMusic();
                            gp.playSE(5);
                        }
                    } else if (hasKey == 0) {
                        gp.ui.showMessage("Locked! You need a key!");
                    }
                    break;
            }
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);

    }

}

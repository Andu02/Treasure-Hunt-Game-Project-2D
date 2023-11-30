package entity;

import jdk.jshell.execution.Util;
import main.UtiliyTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionArea;
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    public boolean collisionOn = false;

    UtiliyTool uTool = new UtiliyTool();

}

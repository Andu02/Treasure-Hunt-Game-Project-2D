package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetManager {

    GamePanel gp;

    public AssetManager(GamePanel gp) {

        this.gp = gp;

    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 33 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 28 * gp.tileSize;
        gp.obj[1].worldY = 33 * gp.tileSize;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].worldX = 28 * gp.tileSize;
        gp.obj[2].worldY = 36 * gp.tileSize;

        gp.obj[3] = new OBJ_Boots(gp);
        gp.obj[3].worldX = 26 * gp.tileSize;
        gp.obj[3].worldY = 36 * gp.tileSize;

        gp.obj[4] = new OBJ_Key(gp);
        gp.obj[4].worldX = 51 * gp.tileSize;
        gp.obj[4].worldY = 18 * gp.tileSize;

        gp.obj[5] = new OBJ_Chest(gp);
        gp.obj[5].worldX = 8 * gp.tileSize;
        gp.obj[5].worldY = 32 * gp.tileSize;

        gp.obj[6] = new OBJ_Key(gp);
        gp.obj[6].worldX = 49 * gp.tileSize;
        gp.obj[6].worldY = 30 * gp.tileSize;

    }

}

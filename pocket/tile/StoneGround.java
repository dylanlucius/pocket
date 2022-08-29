package pocket.tile;

import pocket.system.Screen;

public class StoneGround extends Tile {
    
    public StoneGround(){
        name = "Stone Ground";
        icon = Screen.spritesheet.getSprite(9, 15);
        bgColor = Screen.DARK_GRAY;
        iconColor = Screen.GRAY;

        ground = true;
        wall = false;
    }
}

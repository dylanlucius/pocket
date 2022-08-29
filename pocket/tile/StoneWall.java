package pocket.tile;

import pocket.system.Screen;

public class StoneWall extends Tile {
    
    public StoneWall(){
        name = "Stone Wall";
        icon = Screen.spritesheet.getSprite(11, 2);
        bgColor = Screen.DARK_GRAY;
        iconColor = Screen.GRAY;

        ground = false;
        wall = true;
    }
}

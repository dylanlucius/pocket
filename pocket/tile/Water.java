package pocket.tile;

import pocket.system.Screen;

public class Water extends Tile {
    
    public Water(){
        name = "Water";
        icon = Screen.spritesheet.getSprite(7, 15);
        bgColor = Screen.BLUE;
        iconColor = Screen.WHITE;

        ground = false;
        wall = true;
    }
}

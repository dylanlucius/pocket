package pocket.tile;

import pocket.system.Screen;

public class Lava extends Tile {
    
    public Lava(){
        name = "Lava";
        icon = Screen.spritesheet.getSprite(7, 15);
        bgColor = Screen.RED;
        iconColor = Screen.ORANGE;

        ground = true;
        wall = false;
    }
}

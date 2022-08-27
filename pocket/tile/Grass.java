package pocket.tile;

import pocket.system.Screen;

public class Grass extends Tile {
    
    public Grass(){
        icon = Screen.spritesheet.getSprite(11, 3);
        bgColor = Screen.DARK_GREEN;
        iconColor = Screen.GREEN;
    }
}

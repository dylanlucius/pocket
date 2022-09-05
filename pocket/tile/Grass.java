package pocket.tile;

import pocket.system.*;
import pocket.world.*;

public class Grass extends Tile {
    
    public Grass(){
        name = "Grass";
        icon = Screen.spritesheet.getSprite(11, 3);
        secondary = Screen.spritesheet.getSprite(9, 15);
        bgColor = Screen.DARK_GREEN;
        iconColor = Screen.GREEN;

        ground = true;
        wall = false;

        secondaryFlag = World.d2.roll(1);

    }

    // @Override
    public void drawIcon(int x, int y){
        switch( secondaryFlag ){
            default:
                icon.draw(x, y, iconColor);
                break;
            case 2:
                secondary.draw(x, y, iconColor);
                break;
        }
    }
}

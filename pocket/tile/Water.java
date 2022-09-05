package pocket.tile;

import pocket.system.Screen;
import pocket.world.World;

public class Water extends Tile {
    
    public Water(){
        name = "Water";
        icon = Screen.spritesheet.getSprite(7, 15);
        secondary = Screen.spritesheet.getSprite(14, 7);  
        bgColor = Screen.DARK_BLUE;
        iconColor = Screen.BLUE;

        ground = true;
        wall = false;
    }

    public void drawIcon(int x, int y){
        switch( World.d4.roll(1) ){
            default:
                secondary.draw(x, y, iconColor);
                break;
            case 3:
                icon.draw(x, y, iconColor);
                break;
        }
    }
}

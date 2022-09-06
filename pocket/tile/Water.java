package pocket.tile;

import pocket.system.*;

public class Water extends Fluid {

    public Water(){
        name = "Water";
        icon = Screen.spritesheet.getSprite(7, 15);
        secondary = Screen.spritesheet.getSprite(14, 7);  
        bgColor = Screen.DARK_BLUE;
        iconColor = Screen.BLUE;

        ground = true;
        wall = false;

    }

    
}

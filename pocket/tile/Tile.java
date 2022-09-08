package pocket.tile;

import org.newdawn.slick.*;

import pocket.world.*;
import pocket.system.*;

public abstract class Tile {
    public String name;
    public transient Image icon, secondary;
    public Color bgColor, iconColor;
    public boolean ground, wall, fluid, fire;
    public int secondaryFlag, isSecondary;
    public Counter motionCycle;


    public Space space;

    public void behavior(){

    }

    public void drawIcon(int x, int y){
        icon.draw(x, y, iconColor);
    }
    
}

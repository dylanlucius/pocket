package pocket.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import pocket.world.*;

public abstract class Tile {
    public String name;
    public Image icon;
    public Color bgColor, iconColor;
    public boolean ground, wall, fluid, fire;

    public Space space;

    public void behavior(){

    }
    
}

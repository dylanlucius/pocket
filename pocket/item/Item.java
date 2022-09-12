package pocket.item;

import org.newdawn.slick.*;
import pocket.system.*;
import pocket.creature.*;

public abstract class Item {
    public String name;
    public transient Image icon;
    public Color color;
    public Creature holder;
    public boolean inUse;

    public Item(){
        name = "Pickup";
        icon = Screen.spritesheet.getSprite(15, 3); // default is question mark
        color = Screen.WHITE;
        inUse = false;
    }

    // pickup

    public void behavior(){
        if(holder != null){
            // System.out.println("item action should be happening");
            action();
        }
    }

    public void draw(int x, int y){
        icon.draw(x, y, color);
    }

    public void action(){
        
    }
}

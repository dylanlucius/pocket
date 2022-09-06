package pocket.pickup;

import org.newdawn.slick.*;
import pocket.system.*;
import pocket.creature.*;

public abstract class Pickup {
    public String name;
    public Image icon;
    public Color color;
    public Entity holder;

    public Pickup(){
        name = "Pickup";
        icon = Screen.spritesheet.getSprite(15, 3); // default is question mark
        color = Screen.WHITE;
    }

    public void behavior(){
        if(holder != null){
            System.out.println("item action should be happening");
            action();
        }
    }

    public void draw(int x, int y){
        icon.draw(x, y, color);
    }

    public void action(){
        
    }
}

package pocket.item;

import pocket.system.*;

public class Sword extends Item {
    public Sword(){
        name = "Sword";
        icon = Screen.spritesheet.getSprite(15, 2);
        color = Screen.GRAY;
        
    }

    public void action(){
        if(!inUse){
            holder.damage += 5;
            inUse = true;
        }
    }
}

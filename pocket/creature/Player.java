package pocket.creature;

import pocket.system.*;
import pocket.world.*;
import pocket.item.*;

public class Player extends Creature {
    
    public Player(){
        
        name = "Player";
        avatar = Screen.spritesheet.getSprite(0, 4);
        color = Screen.WHITE;

        baseFoodchain = 60;

        hp = World.d20.roll(2) + 20;

        items.add(new Sword());
        items.add(new Shield());

    }
}

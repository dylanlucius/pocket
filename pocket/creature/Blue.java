package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Blue extends Creature {
    
    public Blue(){
        name = "Blue";
        color = Screen.BLUE;

        team = 3;
        baseSpeed = 3;

        foodchain = 99;

        hp = World.d100.roll(2) + 50;

    }
}

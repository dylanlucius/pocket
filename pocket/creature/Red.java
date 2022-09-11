package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Red extends Creature {
    
    public Red(){

        name = "Red";
        color = Screen.PINK;

        team = 2;

        foodchain = 99;

        hp = World.d100.roll(2) + 50;

    }
}

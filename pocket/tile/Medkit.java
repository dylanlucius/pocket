package pocket.tile;

import java.util.Random;

import pocket.system.*;
import pocket.world.*;
import pocket.creature.*;

public class Medkit extends Tile {
    
    transient Random random = new Random();

    public Medkit(){

        name = "Medkit";
        icon = Screen.spritesheet.getSprite(11, 2);
        bgColor = Screen.WHITE;
        iconColor = Screen.RED;

        ground = true;
        wall = false;

        
    }

    public void behavior(){ 
        if(space.creatures != null && space.creatures.size() > 0){
            Creature target = space.creatures.get(0);
            int health = World.d8.roll(3);

                System.out.println("\n" + target.name + " (HP: " + target.hp + ") was healed " + health + " by Medkit");
                target.hp += (health);
                System.out.println(target.name + " new HP: " + target.hp);    

        }
    }
}

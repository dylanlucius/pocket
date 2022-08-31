package pocket.tile;

import java.util.Random;

import pocket.creature.Participant;
import pocket.system.Screen;
import pocket.world.*;

public class Medkit extends Tile {
    
    Random random = new Random();

    public Medkit(){

        name = "Medkit";
        icon = Screen.spritesheet.getSprite(11, 2);
        bgColor = Screen.WHITE;
        iconColor = Screen.RED;

        ground = true;
        wall = false;

        
    }

    public void behavior(){ 
        if(space.entities != null && space.entities.size() > 0){
            Participant target = (Participant) space.entities.get(0);
            int health = World.d8.roll(3);

                System.out.println("\n" + target.name + " (HP: " + target.hp + ") was healed " + health + " by Medkit");
                target.hp += (health);
                System.out.println(target.name + " new HP: " + target.hp);    

        }
    }
}

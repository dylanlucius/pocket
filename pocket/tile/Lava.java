package pocket.tile;

import java.util.Random;

import pocket.creature.Participant;
import pocket.system.Screen;
import pocket.world.*;

public class Lava extends Tile {
    
    Random random = new Random();

    public Lava(){

        name = "Lava";
        icon = Screen.spritesheet.getSprite(7, 15);
        bgColor = Screen.RED;
        iconColor = Screen.ORANGE;

        ground = true;
        wall = false;

        
    }

    public void behavior(){ 
        if(space.entities != null && space.entities.size() > 0){
            Participant target = (Participant) space.entities.get(0);
            int damage = World.d8.roll(3);

                System.out.println("\n" + target.name + "(HP: " + target.hp + " was hurt " + damage + " by lava!");
                target.hp -= damage;
                System.out.println(target.name + " remaining HP: " + target.hp);    

        }
    }
}
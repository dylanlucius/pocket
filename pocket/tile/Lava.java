package pocket.tile;

import java.util.Random;

import pocket.system.*;
import pocket.world.*;
import pocket.creature.*;

public class Lava extends Fluid {
    
    Random random = new Random();

    public Lava(){

        name = "Lava";
        icon = Screen.spritesheet.getSprite(7, 15);
        secondary = Screen.spritesheet.getSprite(14, 7);  
        bgColor = Screen.RED;
        iconColor = Screen.ORANGE;

        ground = true;
        wall = false;

        motionCycle = new Counter(12);

        
    }

    public void behavior(){ 
        motionCycle.update();
        
        if(space.creatures != null && space.creatures.size() > 0 && space.creatures.get(0).name != "Lava Shark"){
            Creature target = space.creatures.get(0);
            int damage = World.d8.roll(3);

                System.out.println("\n" + target.name + "(HP: " + target.hp + " was hurt " + damage + " by lava!");
                target.hp -= damage;
                System.out.println(target.name + " remaining HP: " + target.hp);    

        }
    }

}
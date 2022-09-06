package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Rodent extends LandAnimal {
    public Rodent(){
        name                = "Rodent";
        avatar              = Screen.spritesheet.getSprite(2, 7);
        color               = Screen.GRAY;

        baseFoodchain = 25;
        baseSpeed = 5;

        hp = 2;
    }

    public void roll(Entity target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d2.roll(1);
                targetTemp = target.hp;
                target.hp -=  damage;
                Main.log.add(nickname + " (" + name  + ") did " + damage + " damage to " + target.nickname + " (" + target.name  + ")" );
                Main.log.add("");        
                    
                if(target.hp - damage < 0){
                    Main.log.add(nickname + " (" + name  + ") killed " + target.nickname + " (" + target.name  + ")" );
                    Main.log.add("");        
                }            
            }
        }
    }
}

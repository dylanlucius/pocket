package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Dog extends LandAnimal {
    public Dog(){
        name                = "Dog";
        avatar              = Screen.spritesheet.getSprite(4, 6);
        color               = Screen.BROWN;

    assignNickname();

        baseFoodchain = 49;
        baseSpeed = 3;

        hp = World.d8.roll(1) + 4;
    }

    public void roll(Entity target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d6.roll(1) + 6;
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
package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Tiger extends BigCat {
    
    public Tiger(){
        name                = "Tiger";
        avatar              = Screen.spritesheet.getSprite(4, 5);
        color               = Screen.ORANGE;

        baseFoodchain = 60;
        baseSpeed = 6;

        hp = World.d20.roll(1) + 30;
    }

    public void roll(Creature target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d20.roll(2) + 10;
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

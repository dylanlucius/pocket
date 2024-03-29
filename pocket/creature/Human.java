package pocket.creature;

import pocket.world.*;
import pocket.system.*;

public abstract class Human extends LandAnimal {

    public Human(){
        baseFoodchain = 50;
    }

    public void roll(Creature target){  
        // if main counter is at Top of Cycle
        if( counter.trigger ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d6.roll(2) + 6;
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
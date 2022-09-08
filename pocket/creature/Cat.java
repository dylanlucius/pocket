package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Cat extends BigCat {
    
    public Cat(){
        name                = "Cat";
        avatar              = Screen.spritesheet.getSprite(3, 6);
        color               = Screen.DARK_GRAY;

        assignNickname();

        baseFoodchain = 48;
        baseSpeed = 3;

        hp = World.d4.roll(1) + 2;
    }

    public void roll(Creature target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d2.roll(1) + 2;
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

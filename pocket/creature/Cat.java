package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Cat extends LandAnimal {
    
    public Cat(){
        name                = "Cat";
        avatar              = Screen.spritesheet.getSprite(3, 6);
        color               = Screen.DARK_GRAY;

        baseFoodchain = 50;
        baseSpeed = 3;

        hp = World.d4.roll(1) + 2;
    }

    public void roll(Entity target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d2.roll(1) + 2;
                targetTemp = target.hp;
                target.hp -=  damage;
                Main.log.add(target.name + " #" + target.number + " (HP: " + targetTemp + ") --> (HP: " + target.hp + ") -" + damage);
                Main.log.add("");
            }
        }
    }

}

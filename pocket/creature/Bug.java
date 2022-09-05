package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Bug extends LandAnimal {
    public Bug(){
        name                = "Bug";
        avatar              = Screen.spritesheet.getSprite(9, 15);
        color               = Screen.rainbow[random.nextInt(6)];

        baseFoodchain = 5;
        baseSpeed = 2;

        hp = 1;
    }

    @Override
    public void behavior(){

        // make them lowest on the foodchain if they're in the water
        if(this.space.tile != null){
            if(this.space.tile.name == "Water"){
                foodchain = 1;
            }
            else {
                foodchain = baseFoodchain;
            }
        }
        
        lifecheck();
    
        if(!attacking){
            move();
        }
        
        attack();
        
    }

    // zero damage
    public void roll(Entity target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = 0;
                targetTemp = target.hp;
                target.hp -=  damage;
                Main.log.add(target.name + " #" + target.number + " (HP: " + targetTemp + ") --> (HP: " + target.hp + ") -" + damage);
                Main.log.add("");
            }
        }
    }
}

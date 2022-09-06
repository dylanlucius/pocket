package pocket.tile;

import pocket.system.*;
import pocket.world.*;

public class Fluid extends Tile {
    
    int isSecondary;
    Counter motionCycle;

    public Fluid(){
        isSecondary = Dice.random.nextInt(2);
        motionCycle = new Counter(8);
    }

    public void drawIcon(int x, int y){

        if( !Main.paused && motionCycle.over() ){
            switch( World.d4.roll(1) ){
                default:
                    secondary.draw(x, y, iconColor);
                    isSecondary = 1;
                    break;
                case 3:
                    icon.draw(x, y, iconColor);
                    isSecondary = 0;
                    break;
            } 
        }
        else {
            switch(isSecondary){
                default:
                    icon.draw(x, y, iconColor);

                    break;

                case 1:
                    secondary.draw(x, y, iconColor);
                    break;
            }

        }
    }
}

package pocket.creature;

import pocket.system.*;
import pocket.world.*;
import pocket.item.*;

public class Player extends Creature {
    
    public static int direction;

    public Player(){
        
        name = "Player";
        avatar = Screen.spritesheet.getSprite(0, 4);
        color = Screen.WHITE;

        baseFoodchain = 60;

        hp = World.d20.roll(2) + 20;

        items.add(new Sword());
        items.add(new Shield());

    }

    public void move(){

        movementModifier();
    
        // MOVEMENT
        //  1 2 3 4 5 6 7 8
        //                ^      <-- threshold   |  executes 1 time
        
        //if(counter.step >= movethreshold){
            // choose random of 4 directions
            switch(direction){
    
                // no input
                default:
                    break;

                // up
                case 0:{
                    // if the space above them exists, a tile exists on it, the tile is a ground type and has no creatures on it
                    if(space.up != null && space.up.tile != null && space.up.tile.ground && space.up.creatures.size() <= 0){
                        World.placeCreature(space.up.tagX, space.up.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.up;
                        direction = 99;
                    }
                    break;    
                }
    
                // left
                case 1:{
                    if(space.left != null && space.left.tile != null && space.left.tile.ground && space.left.creatures.size() <= 0){
                        World.placeCreature(space.left.tagX, space.left.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.left;
                        direction = 99;
                    }
                    break;
                }
                
                // right
                case 2:{
                    if(space.right != null && space.right.tile != null && space.right.tile.ground && space.right.creatures.size() <= 0){
                        World.placeCreature(space.right.tagX, space.right.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.right;
                        direction = 99;
                    }
                    break;
                }
    
                // down
                case 3:{
                    if(space.down != null && space.down.tile != null && space.down.tile.ground && space.down.creatures.size() <= 0){
                        World.placeCreature(space.down.tagX, space.down.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.down;
                        direction = 99;
                    }
                    break;
                }
            }     
        //}      
     }
     
}

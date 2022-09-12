package pocket.creature;

import pocket.system.*;
import pocket.world.*;
import pocket.item.*;

public class PlayerTwo extends Creature {
    
    public static int direction;
    public static boolean moving;

    public PlayerTwo(){
        
        name = "Player Two";
        avatar = Screen.spritesheet.getSprite(0, 4);
        color = Screen.RED;

        baseFoodchain = 60;

        hp = World.d20.roll(2) + 20;

        items.add(new Sword());
        items.get(0).holder = this;
        items.add(new Shield());
        items.get(1).holder = this;

        direction = 99;
    }

    public void move(){

        movementModifier();

            // choose random of 4 directions
            switch(direction){
    
                // no input
                default:
                    moving = false;
                    break;

                // up
                case 0:{
                    moving = true;
                    // if the space above them exists, a tile exists on it, the tile is a ground type and has no creatures on it
                    if(space.up != null && space.up.tile != null && space.up.tile.ground && space.up.creatures.size() <= 0){                        
                        World.placeCreature(space.up.tagX, space.up.tagY, this);
                        space = space.up;
                        World.clearcreatures(space.down.tagX, space.down.tagY);
                    }
                    else if(space.up != null && space.up.tile != null && space.up.tile.ground && space.up.creatures.size() > 0){                        
                        roll(space.up.creatures.get(0));
                    }
                    direction = 99;
                    break;    
                }
    
                // left
                case 1:{
                    moving = true;
                    if(space.left != null && space.left.tile != null && space.left.tile.ground && space.left.creatures.size() <= 0){
                        World.placeCreature(space.left.tagX, space.left.tagY, this);
                        space = space.left;
                        World.clearcreatures(space.right.tagX, space.right.tagY);

                    }
                    else if(space.left != null && space.left.tile != null && space.left.tile.ground && space.left.creatures.size() > 0){                        
                        roll(space.left.creatures.get(0));
                    }
                    direction = 99; 
                    break;
                }
                
                // right
                case 2:{
                    moving = true;

                    if(space.right != null && space.right.tile != null && space.right.tile.ground && space.right.creatures.size() <= 0){
                        World.placeCreature(space.right.tagX, space.right.tagY, this);
                        space = space.right;
                        World.clearcreatures(space.left.tagX, space.left.tagY);


                    }
                    else if(space.right != null && space.right.tile != null && space.right.tile.ground && space.right.creatures.size() > 0){                        
                        roll(space.right.creatures.get(0));
                    }
                    
                    direction = 99;
                    break;
                }
    
                // down
                case 3:{
                    moving = true;
                    if(space.down != null && space.down.tile != null && space.down.tile.ground && space.down.creatures.size() <= 0){
                        
                        World.placeCreature(space.down.tagX, space.down.tagY, this);
                        space = space.down;
                        World.clearcreatures(space.up.tagX, space.up.tagY);

                    }
                    else if(space.down != null && space.down.tile != null && space.down.tile.ground && space.down.creatures.size() > 0){                        
                        roll(space.down.creatures.get(0));
                    }
                    direction = 99;
                    break;
                }
            }     
     }

     public void roll(Creature target){  
            // if random roll of "d20" is equal to or larger than enemy AC
        if( World.d20.roll(1) > target.ac){;
            targetTemp = target.hp;
            target.hp -=  damage;
            // Main.log.add(target.name + " #" + target.number + " (HP: " + targetTemp + ") --> (HP: " + target.hp + ") -" + damage);
            // Main.log.add("");

            Main.log.add(nickname + " (" + name  + ") did " + damage + " damage to " + target.nickname + " (" + target.name  + ")" );
            Main.log.add("");        

            
            if(target.hp - damage < 0){
                Main.log.add(nickname + " (" + name  + ") killed " + target.nickname + " (" + target.name  + ")" );
                Main.log.add("");        
            }

        }
    }
    
}

package pocket.creature;

import pocket.world.*;
import pocket.system.*;

public class LavaShark extends Shark {
    
    public LavaShark(){
        name = "Lava Shark";
        avatar = Screen.spritesheet.getSprite(14, 1);
        color = Screen.DARK_PURPLE;

        baseFoodchain = 60;

        hp = World.d20.roll(2) + 20;
        
    }

    @Override
    public void move(){

        // MOVEMENT MODIFIERS
        // if the space they're on has a tile
        if(this.space.tile != null){
            // if they're a fish and it's not water
            if(space.tile.name != "Lava"){
            }
            // by default
            else {
            }
        }
    
        // MOVEMENT -- fish can move from land to water, but won't move from water to land, they're also slower out of water
        if(space.tile != null && space.tile.name == "Lava"){
                //  1/10 chance to move at all
            if(random.nextInt(10) == 9){
                // choose random of 4 directions
                switch(random.nextInt(4)){
        
                    // up
                    default:{
                        // execute a random number of times between 1 and 8
                            // if the space above them exists
                            if(this.space.up != null){
                                // if a tile exists on it
                                if(this.space.up.tile != null){
                                    // if the tile is a ground type
                                    if(this.space.up.tile.ground && this.space.up.tile.name == "Lava"){
                                        // if it has no creatures on it
                                        if(this.space.up.creatures.size() <= 0){
                                            World.placeCreature(this.space.up.tagX, this.space.up.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.up;
                                        }       
                                    }
                                }
                            }        
                        break;    
                    }
        
                    // left
                    case 1:{
                            if(this.space.left != null){
                                if(this.space.left.tile != null){
                                    if(this.space.left.tile.ground && this.space.left.tile.name == "Lava"){
                                        if(this.space.left.creatures.size() <= 0){
                                            World.placeCreature(this.space.left.tagX, this.space.left.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.left;
                                        }      
                                    }
                                }
                            }        
                        break;
                    }
                    
                    // right
                    case 2:{
                            if(this.space.right != null){
                                if(this.space.right.tile != null){
                                    if(this.space.right.tile.ground && this.space.right.tile.name == "Lava"){
                                        if(this.space.right.creatures.size() <= 0){
                                            World.placeCreature(this.space.right.tagX, this.space.right.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.right;
                                        }      
                                    }
                                }
                            }        
                        break;
                    }
        
                    // down
                    case 3:{
    
                            if(this.space.down != null){
                                if(this.space.down.tile != null){
                                    if(this.space.down.tile.ground && this.space.down.tile.name == "Lava"){
                                        if(this.space.down.creatures.size() <= 0){
                                            World.placeCreature(this.space.down.tagX, this.space.down.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.down;
                                        }       
                                    }
                                }
                            }        
                        break;
                    }
        
                    
                }     
            }
        }
        else if(space.tile != null){
            if(random.nextInt(10) == 9){
                // choose random of 4 directions
                switch(random.nextInt(4)){
        
                    // up
                    default:{
                        // execute a random number of times between 1 and 8
                            // if the space above them exists
                            if(this.space.up != null){
                                // if a tile exists on it
                                if(this.space.up.tile != null){
                                    // if the tile is a ground type
                                    if(this.space.up.tile.ground){
                                        // if it has no creatures on it
                                        if(this.space.up.creatures.size() <= 0){
                                            World.placeCreature(this.space.up.tagX, this.space.up.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.up;
                                        }       
                                    }
                                }
                            }
        
                        break;    
                    }
        
                    // left
                    case 1:{
                            if(this.space.left != null){
                                if(this.space.left.tile != null){
                                    if(this.space.left.tile.ground){
                                        if(this.space.left.creatures.size() <= 0){
                                            World.placeCreature(this.space.left.tagX, this.space.left.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.left;
                                        }      
                                    }
                                }
                            }
                        break;
                    }
                    
                    // right
                    case 2:{
                            if(this.space.right != null){
                                if(this.space.right.tile != null){
                                    if(this.space.right.tile.ground){
                                        if(this.space.right.creatures.size() <= 0){
                                            World.placeCreature(this.space.right.tagX, this.space.right.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.right;
                                        }      
                                    }
                                }
                            }   
                        break;
                    }
        
                    // down
                    case 3:{
                            if(this.space.down != null){
                                if(this.space.down.tile != null){
                                    if(this.space.down.tile.ground){
                                        if(this.space.down.creatures.size() <= 0){
                                            World.placeCreature(this.space.down.tagX, this.space.down.tagY, this);
                                            World.clearcreatures(this.space.tagX, this.space.tagY);
                                            this.space = this.space.down;
                                        }       
                                    }
                                }
                            }
                        break;
                    }
        
                    
                }     
            }
        }

              
     }
    
    @Override
    public void behavior(){

    // make them lower on the foodchain if they're out of the water
    if(this.space.tile != null){
        if(this.space.tile.name == "Lava"){
            foodchain = baseFoodchain;
        }
        else {
            foodchain = 5;
        }
    }

    if(lifecheck()){
        
        resolveHunger();

        if(!attacking){
            move();
        }
        
        attack();

        itemAll();

        runitems();

    }
    
}

}

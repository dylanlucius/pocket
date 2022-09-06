package pocket.creature;

import pocket.system.*;
import pocket.world.*;

public class Fish extends Entity {

    public Fish(){
        name = "Fish";
        avatar = Screen.spritesheet.getSprite(0, 14);
        color = Screen.GREEN;

        baseSpeed = 2;

        baseFoodchain = 20;

        hp = 2;
        
    }
      
    @Override
    public void move(){

        movementModifier();
    
        // MOVEMENT -- fish can move from land to water, but won't move from water to land, they're also slower out of water
        if(space.tile != null && space.tile.name == "Water"){
                //  1/10 chance to move at all
            if(random.nextInt(10) == 9){
                // choose random of 4 directions
                switch(random.nextInt(4)){
        
                    // up
                    default:{
                        // execute a random number of times between 1 and 8
                        for(int i = 0; i < speed ; i++){
                            // if the space above them exists
                            if(this.space.up != null){
                                // if a tile exists on it
                                if(this.space.up.tile != null){
                                    // if the tile is a ground type
                                    if(this.space.up.tile.ground && this.space.up.tile.name == "Water"){
                                        // if it has no entities on it
                                        if(this.space.up.entities.size() <= 0){
                                            World.placeEntity(this.space.up.tagX, this.space.up.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.up;
                                        }       
                                    }
                                }
                            }
                        }   
        
                        break;    
                    }
        
                    // left
                    case 1:{
                        for(int i = 0; i < speed; i++){
                            if(this.space.left != null){
                                if(this.space.left.tile != null){
                                    if(this.space.left.tile.ground && this.space.left.tile.name == "Water"){
                                        if(this.space.left.entities.size() <= 0){
                                            World.placeEntity(this.space.left.tagX, this.space.left.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.left;
                                        }      
                                    }
                                }
                            }
                        }   
        
                        break;
                    }
                    
                    // right
                    case 2:{
                        for(int i = 0; i < speed; i++){
                            if(this.space.right != null){
                                if(this.space.right.tile != null){
                                    if(this.space.right.tile.ground && this.space.right.tile.name == "Water"){
                                        if(this.space.right.entities.size() <= 0){
                                            World.placeEntity(this.space.right.tagX, this.space.right.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.right;
                                        }      
                                    }
                                }
                            }
                        }   
        
                        break;
                    }
        
                    // down
                    case 3:{
        
                        for(int i = 0; i < speed; i++){
                            if(this.space.down != null){
                                if(this.space.down.tile != null){
                                    if(this.space.down.tile.ground && this.space.down.tile.name == "Water"){
                                        if(this.space.down.entities.size() <= 0){
                                            World.placeEntity(this.space.down.tagX, this.space.down.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.down;
                                        }       
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
                        for(int i = 0; i < speed ; i++){
                            // if the space above them exists
                            if(this.space.up != null){
                                // if a tile exists on it
                                if(this.space.up.tile != null){
                                    // if the tile is a ground type
                                    if(this.space.up.tile.ground){
                                        // if it has no entities on it
                                        if(this.space.up.entities.size() <= 0){
                                            World.placeEntity(this.space.up.tagX, this.space.up.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.up;
                                        }       
                                    }
                                }
                            }
                        }   
        
                        break;    
                    }
        
                    // left
                    case 1:{
                        for(int i = 0; i < speed; i++){
                            if(this.space.left != null){
                                if(this.space.left.tile != null){
                                    if(this.space.left.tile.ground){
                                        if(this.space.left.entities.size() <= 0){
                                            World.placeEntity(this.space.left.tagX, this.space.left.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.left;
                                        }      
                                    }
                                }
                            }
                        }   
        
                        break;
                    }
                    
                    // right
                    case 2:{
                        for(int i = 0; i < speed; i++){
                            if(this.space.right != null){
                                if(this.space.right.tile != null){
                                    if(this.space.right.tile.ground){
                                        if(this.space.right.entities.size() <= 0){
                                            World.placeEntity(this.space.right.tagX, this.space.right.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.right;
                                        }      
                                    }
                                }
                            }
                        }   
        
                        break;
                    }
        
                    // down
                    case 3:{
        
                        for(int i = 0; i < speed; i++){
                            if(this.space.down != null){
                                if(this.space.down.tile != null){
                                    if(this.space.down.tile.ground){
                                        if(this.space.down.entities.size() <= 0){
                                            World.placeEntity(this.space.down.tagX, this.space.down.tagY, this);
                                            World.clearEntities(this.space.tagX, this.space.tagY);
                                            this.space = this.space.down;
                                        }       
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
        if(this.space.tile.name == "Water"){
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

        pickupAll();

        runPickups();

    }
    
}

    @Override
    public void movementModifier() {
        // MOVEMENT MODIFIERS
    // if the space they're on has a tile
        if(space.tile != null){
            // if they are a fish and it's water
            if(space.tile.name != "Water"){
                speed = 1;
            }
            else {
                speed = baseSpeed;
            }
        }
    }
}
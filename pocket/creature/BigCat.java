package pocket.creature;

import pocket.system.Speedcheck;
import pocket.world.*;

public class BigCat extends LandAnimal {

    public BigCat(){
    }

    @Override
    public void move(){

        movementModifier();
    
    if(Speedcheck.halfspeed(counter.step)){

        // MOVEMENT -- fish can move from land to water, but won't move from water to land, they're also slower out of water
        if(space.tile != null && space.tile.name != "Water"){
                //  1/10 chance to move at all
            if(random.nextInt(10) == 0){
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
                                    if(this.space.up.tile.ground && this.space.up.tile.name != "Water"){
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
                                    if(this.space.left.tile.ground && this.space.left.tile.name != "Water"){
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
                                    if(this.space.right.tile.ground && this.space.right.tile.name != "Water"){
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
                                    if(this.space.down.tile.ground && this.space.down.tile.name != "Water"){
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
            if(random.nextInt(10) == 0){
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
    }
}

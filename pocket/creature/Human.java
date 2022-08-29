package pocket.creature;

import java.util.Random;

import pocket.world.World;

public class Human extends Entity {

    Random random = new Random();

    public Human(){
        super();
    }

    @Override
    public void behavior(){

        if(this.space != null){
            if(random.nextInt(10) == 9){
                switch(random.nextInt(4)){
                    case 1:{
        
                        for(int i = 0; i < random.nextInt(8); i++){
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
        
                    case 2:{
        
                        for(int i = 0; i < random.nextInt(8); i++){
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
        
                    case 3:{
        
                        for(int i = 0; i < random.nextInt(8); i++){
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
        
                    default:{
        
                        for(int i = 0; i < random.nextInt(8); i++){
                            if(this.space.up != null){
                                if(this.space.up.tile != null){
                                    if(this.space.up.tile.ground){
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
                }     
            }
    
    
        }

    }
}
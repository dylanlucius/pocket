package pocket.creature;

public class LandAnimal extends Entity {
    
    @Override
    public void behavior(){

        // make them lower on the foodchain if they're in the water
        if(this.space.tile != null){
            if(this.space.tile.name == "Water"){
                foodchain = 25;
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
}

package pocket.creature;

public class LandAnimal extends Creature {


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
        else {
            foodchain = baseFoodchain;
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

    @Override
    public void movementModifier() {
        // MOVEMENT MODIFIERS
    // if the space they're on has a tile
        if(space.tile != null){
            // if they're not a fish and it's water
            if(space.tile.name == "Water"){
                speed = baseSpeed / 2;
            }
            else {
                speed = baseSpeed;
            }
        }
    }
}

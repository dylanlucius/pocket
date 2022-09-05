package pocket.creature;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import pocket.system.*;
import pocket.world.*;

public abstract class Entity {
 ///////////////////////////////////////////////
 //                  FIELDS
 //////////////////////////////////////////////
    
    // BASICS
    public Random random = new Random();
    public Space space;

    public String name;
    public Image avatar;
    public Color color, bgColor;

    public byte team;

    // combat variables
    public int hp, ac = 10, damage, number, targetTemp;

    public boolean attacking;

    public Counter counter = new Counter(8);

    // SPECIFICS
    short age, height, weight;
    String nickname; 

    ///////////////////////////////////////
    //        TAXONOMIC TRAITS
    ///////////////////////////////////////

    // BODY : skeleton, muscles, organs
    // HashMap<String, Boolean[]> skeleton = new HashMap<String, Boolean[]>();
    // ... skull, back bones, etc
    
    // HashMap<String, Boolean[]> muscle   = new HashMap<String, Boolean[]>();
    // ... all muscles
    
    // HashMap<String, Boolean[]> organs   = new HashMap<String, Boolean[]>();
    // ... sense organs: eyes, ears, ... | internal organs

    // SENSES
    boolean sight, hearing, smell, taste, touch;
    short sightRange, hearingRange, smellRange;

    // MENTAL
    boolean focused, awake, alert, hostile, languageCapacity;
    short intelligenceLevel;

    // DIET
    boolean carnivore, vegetarian, omnivore;
    int foodchain, baseFoodchain;

    // SEXUAL
    boolean sexual, asexual, male, female, puberty;

    //byte attractiveness;
    // ... deprecated | replace with mate seleection system:
    // ... things they're attracted to: appearance, personality, ... feather color

    // PHYSICAL
    boolean canWalk, canSwim, canFly, fight, flee;;
    int reach, speed, baseSpeed;
    //short damage; // how much damage if attacks -- dangerousness

    /////////////////////////////////
    //          HEALTH
    ///////////////////////////////////

    // VITALS

    boolean canBreath, hydrated, hungry, sleepy, dizzy, nauseous, sexuallyAroused, hasToUrinate, hasToDeficate, bleeding;
    //byte painLevel, bloodVolume;
    short heartrate, temperature;
    // EMOTIONAL STATE

    boolean interested, confused, proud, ashamed, excited, agitated, overwhelmed, alarmed,
            happy, sad, angry, afraid, disgusted;

    // DISABILITIES
    // sense impairment: blind, deaf...
    // mental impairment: 
    // physical impairment:
    // mental illness
    // ... anxiety disorder
    // ... mood disorder
    // ... personality disorder
    // ... psychotic disorder
    // ... addiction

    // DISEASES
    // infectious
    // ... bacterial, viral
    // physiological
    // ... cancer
    // hereditary
    // ... *-degenerative
    // deficiency
    // ... vitamin*
    
 /////////////////////////////////////////////   
 //                  METHODS
 ///////////////////////////////////////////////////////
 public Entity(){

    if(Main.on){
        World.entityList.add(this);
    }

    // if there's a world cursor, set this entity's space to the world cursor's space
    if(World.cursor != null){
        space = World.cursor.space;
    }

    // smiley face avatar
    avatar = Screen.spritesheet.getSprite(2, 0);

    hp = World.d10.roll(1) + 10; // range 11 to 20

    damage = random.nextInt(3) + 1; // 1 to 3

    attacking = false;

    number = 0;

    setNumber(this);

    speed = baseSpeed;

    team = 0; // neutral

}

public void behavior(){

    lifecheck();

    if(!attacking){
        move();
    }
    
    attack();
    
}

 public void move(){

    // MOVEMENT MODIFIERS
    // if the space they're on has a tile
    if(space.tile != null){
        // if they're not a fish and it's water
        if(space.tile.name == "Water" && name != "Fish"){
            speed = baseSpeed / 2;
        }
        // if they are a fish and it's not water
        else if(space.tile.name != "Water" && name == "Fish"){
            speed = 1;
        }
        // by default
        else {
            speed = baseSpeed;
        }
    }

    // MOVEMENT
    //  1/10 chance to move at all
    if(random.nextInt(10) == 9){
        // choose random of 4 directions
        switch(random.nextInt(4)){

            // up
            default:{
                // execute a random number of times between 1 and 8
                for(int i = 0; i < speed ; i++){
                    // if the space above them exists
                    if(space.up != null){
                        // if a tile exists on it
                        if(space.up.tile != null){
                            // if the tile is a ground type
                            if(space.up.tile.ground){
                                // if it has no entities on it
                                if(space.up.entities.size() <= 0){
                                    World.placeEntity(space.up.tagX, space.up.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.up;
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
                    if(space.left != null){
                        if(space.left.tile != null){
                            if(space.left.tile.ground){
                                if(space.left.entities.size() <= 0){
                                    World.placeEntity(space.left.tagX, space.left.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.left;
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
                    if(space.right != null){
                        if(space.right.tile != null){
                            if(space.right.tile.ground){
                                if(space.right.entities.size() <= 0){
                                    World.placeEntity(space.right.tagX, space.right.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.right;
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
                    if(space.down != null){
                        if(space.down.tile != null){
                            if(space.down.tile.ground){
                                if(space.down.entities.size() <= 0){
                                    World.placeEntity(space.down.tagX, space.down.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.down;
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
 
 public void attack(){
            
    // [UP] if the space above them has an entity on it
    if(space.up != null && space.up.entities.size() > 0){
            // if entity is not on the same team
            if(space.up.entities.get(0).team != team || space.up.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.up.entities.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [LEFT] if the space above them has an entity on it
    if(space.left != null && space.left.entities.size() > 0){
            // if entity is not on the same team
            if(space.left.entities.get(0).team != team || space.left.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.left.entities.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [RIGHT] if the space above them has an entity on it
    if(space.right != null && space.right.entities.size() > 0){
            // if entity is not on the same team
            if(space.right.entities.get(0).team != team || space.right.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.right.entities.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [DOWN] if the space above them has an entity on it
    if(space.down != null && space.down.entities.size() > 0){
            // if entity is not on the same team
            if(space.down.entities.get(0).team != team || space.down.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.down.entities.get(0));
            }
    }
    else {
        attacking = false;
    }
    
          
}

public void roll(Entity target){  
    // if main counter is at Top of Cycle
    if( counter.over() ){
         // if random roll of "d20" is equal to or larger than enemy AC
        if( World.d20.roll(1) > target.ac){
            damage = World.d4.roll(2);
            targetTemp = target.hp;
            target.hp -=  damage;
            Main.log.add(target.name + " #" + target.number + " (HP: " + targetTemp + ") --> (HP: " + target.hp + ") -" + damage);
            Main.log.add("");
        }
    }
}

public void lifecheck(){
    // make sure they're still alive
    if(hp <= 0){
        World.entityList.remove(this);
        World.clearEntities(space.tagX, space.tagY);
        Main.log.add(name + " #" + number + " eliminated");
        Main.log.add("");

    }
}

public void setNumber(Entity particant){
    int number = random.nextInt(3840) + 1;
    boolean duplicate = false;

    // for every space in the world
    for(int i = 0; i < 80; i++){
        for(int j = 0; j < 48; j++){
            
            // if the space is not null
            if(World.space != null && World.space[i][j].entities.size() > 0){
                // if there is an entity on it, and it's a "Participant"
                    Entity target = World.space[i][j].entities.get(0);
                    if(number == target.number){
                        duplicate = true;
                        //System.out.println("duplicate number found");
                    }
            }            
        }   
    }

    if(!duplicate){
        this.number = number;
        //System.out.println("Found a unique ID number");
    }
    else {
        setNumber(this);
        //System.out.println("fetching new number");
    }
}

}
